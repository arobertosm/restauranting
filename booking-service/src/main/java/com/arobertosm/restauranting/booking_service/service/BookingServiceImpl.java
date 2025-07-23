package com.arobertosm.restauranting.booking_service.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import com.arobertosm.restauranting.booking_service.dto.BookingResponseDto;
import com.arobertosm.restauranting.booking_service.dto.CreateBookingRequestDto;
import com.arobertosm.restauranting.booking_service.mapper.BookingMapper;
import com.arobertosm.restauranting.booking_service.model.Booking;
import com.arobertosm.restauranting.booking_service.repository.BookingRepository;
import jakarta.transaction.Transactional;

@Service
public class BookingServiceImpl implements BookingService {
    
    private final BookingRepository bookingRepository;
    private final BookingMapper bookingMapper;
    private final WebClient webClient;

    @Autowired
    public BookingServiceImpl(BookingRepository bookingRepository, BookingMapper bookingMapper, WebClient.Builder webClientBuilder){
        this.bookingRepository = bookingRepository;
        this.bookingMapper = bookingMapper;
        this.webClient = webClientBuilder.baseUrl("http://restaurant-service").build();
    }

    @Override
    public Optional<BookingResponseDto> findById(Long bookingId){
        return bookingRepository.findById(bookingId)
                .map(bookingMapper::toResponseDto);
    }

    @Override
    public List<BookingResponseDto> getAllBookingsByRestaurantId(Long restaurantId){
        List<Booking> bookingsFound = bookingRepository.getBookingsFromRestaurantId(restaurantId);
        if (bookingsFound == null || bookingsFound.isEmpty()){
            return new ArrayList<BookingResponseDto>();
        }
        return bookingMapper.toResponseDtoList(bookingsFound);
    }

    @Override
    public List<BookingResponseDto> getAllBookingsByUserId(Long userId){
        List<Booking> bookingsFound = bookingRepository.getBookingsFromUserId(userId);
        if (bookingsFound == null || bookingsFound.isEmpty()){
            return new ArrayList<BookingResponseDto>();
        }
        return bookingMapper.toResponseDtoList(bookingsFound);
    }

    @Override
    @Transactional
    public BookingResponseDto bookRestaurant(CreateBookingRequestDto bookingRequestDto){
        Optional<Booking> existingBookingAux = bookingRepository.getBookingWithRestaurantAndUserIdsAndStartDate(bookingRequestDto.getRestaurantId(), bookingRequestDto.getUserId(), bookingRequestDto.getStartDate());
        Double maxHoursUntilNextBooking = 4.0;
        if (existingBookingAux != null && existingBookingAux.isPresent()){
            throw new IllegalArgumentException("You can't book more than once in the same period of time at the same restaurant.");
        }
        List<Booking> existingBookingsUser = bookingRepository.getRelevantBookingsFromUserId(bookingRequestDto.getUserId(), bookingRequestDto.getStartDate());
        if(existingBookingsUser != null && !existingBookingsUser.isEmpty()){
            for (Booking bookingUser : existingBookingsUser) {
                LocalDateTime startingDate = bookingUser.getStartDate();
                LocalDateTime finishingDate = null;
                if (bookingUser.getEndDate() != null){
                    finishingDate = bookingUser.getEndDate();
                }

                if (finishingDate == null){
                    long hoursDifference = ChronoUnit.HOURS.between(startingDate, bookingRequestDto.getStartDate());
                    if (hoursDifference < maxHoursUntilNextBooking){
                        throw new RuntimeException("You can't book before 4 hours from your last booking start date.");
                    }
                }

                if (finishingDate != null){
                    if (bookingRequestDto.getStartDate() == startingDate && bookingRequestDto.getEndDate() == finishingDate){
                        throw new RuntimeException("You're trying to book in a date which would overlap another of your bookings.");
                    }
                    if (bookingRequestDto.getStartDate().isBefore(finishingDate) && bookingRequestDto.getEndDate().isAfter(startingDate)){
                        throw new RuntimeException("You're trying to book in a date which would overlap another of your bookings.");
                    }
                }
            }
        }
        LocalDateTime finishingDayDate = bookingRequestDto.getStartDate().plusDays(1).toLocalDate().atStartOfDay();
        List<Booking> existingBookingsRestaurant = bookingRepository.getDailyBookingsFromRestaurantId(bookingRequestDto.getRestaurantId(), bookingRequestDto.getStartDate(), finishingDayDate);
        Integer customersBooked = 0;
        if(existingBookingsRestaurant != null && !existingBookingsRestaurant.isEmpty()){
            for (Booking bookingRestaurant : existingBookingsRestaurant) {
                customersBooked = customersBooked += bookingRestaurant.getCustomersNumber();
            }
        }
        Integer maxCustomers = this.webClient.get()
                                .uri("/api/restaurants/{id}/max-customers", bookingRequestDto.getRestaurantId())
                                .retrieve()
                                .bodyToMono(Integer.class)
                                .block();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        if (customersBooked >= maxCustomers){
            throw new RuntimeException("The restaurant you are trying to book is full for the date: " + bookingRequestDto.getStartDate().format(formatter).toString());
        }
        Booking bookingToSave = bookingMapper.toEntity(bookingRequestDto);
        Booking bookingSaved = bookingRepository.save(bookingToSave);
        return bookingMapper.toResponseDto(bookingSaved);
    }

    @Override
    public Optional<BookingResponseDto> update(Long id, CreateBookingRequestDto bookingUpdated){
        Optional<Booking> existingBookingAux = bookingRepository.getBookingWithRestaurantAndUserIdsAndStartDate(bookingUpdated.getRestaurantId(), bookingUpdated.getUserId(), bookingUpdated.getStartDate());
        Double maxHoursUntilNextBooking = 4.0;
        if (existingBookingAux != null && existingBookingAux.isPresent()){
            throw new IllegalArgumentException("You can't book more than once in the same period of time at the same restaurant.");
        }
        List<Booking> existingBookingsUser = bookingRepository.getRelevantBookingsFromUserId(bookingUpdated.getUserId(), bookingUpdated.getStartDate());
        if(existingBookingsUser != null && !existingBookingsUser.isEmpty()){
            for (Booking bookingUser : existingBookingsUser) {
                LocalDateTime startingDate = bookingUser.getStartDate();
                LocalDateTime finishingDate = null;
                if (bookingUser.getEndDate() != null){
                    finishingDate = bookingUser.getEndDate();
                }

                if (finishingDate == null){
                    long hoursDifference = ChronoUnit.HOURS.between(startingDate, bookingUpdated.getStartDate());
                    if (hoursDifference < maxHoursUntilNextBooking){
                        throw new RuntimeException("You can't book before 4 hours from your last booking start date.");
                    }
                }

                if (finishingDate != null){
                    if (bookingUpdated.getStartDate() == startingDate && bookingUpdated.getEndDate() == finishingDate){
                        throw new RuntimeException("You're trying to book in a date which would overlap another of your bookings.");
                    }
                    if (bookingUpdated.getStartDate().isBefore(finishingDate) && bookingUpdated.getEndDate().isAfter(startingDate)){
                        throw new RuntimeException("You're trying to book in a date which would overlap another of your bookings.");
                    }
                }
            }
        }
        LocalDateTime finishingDayDate = bookingUpdated.getStartDate().plusDays(1).toLocalDate().atStartOfDay();
        List<Booking> existingBookingsRestaurant = bookingRepository.getDailyBookingsFromRestaurantId(bookingUpdated.getRestaurantId(), bookingUpdated.getStartDate(), finishingDayDate);
        Integer customersBooked = 0;
        if(existingBookingsRestaurant != null && !existingBookingsRestaurant.isEmpty()){
            for (Booking bookingRestaurant : existingBookingsRestaurant) {
                customersBooked = customersBooked += bookingRestaurant.getCustomersNumber();
            }
        }
        Integer maxCustomers = this.webClient.get()
                                .uri("/api/restaurants/{id}/max-customers", bookingUpdated.getRestaurantId())
                                .retrieve()
                                .bodyToMono(Integer.class)
                                .block();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        if (customersBooked >= maxCustomers){
            throw new RuntimeException("The restaurant you are trying to book is full for the date: " + bookingUpdated.getStartDate().format(formatter).toString());
        }
        return bookingRepository.findById(id)
                .map(existingBooking ->{
                    existingBooking.setRestaurantId(bookingUpdated.getRestaurantId());
                    existingBooking.setUserId(bookingUpdated.getUserId());
                    existingBooking.setStartDate(bookingUpdated.getStartDate());
                    existingBooking.setEndDate(bookingUpdated.getEndDate());
                    existingBooking.setCustomersNumber(bookingUpdated.getCustomersNumber());
                    Booking updatedBooking = bookingRepository.save(existingBooking);
                    return bookingMapper.toResponseDto(updatedBooking);
                });
    }

    @Override
    public void deleteById(Long id){
        if (!bookingRepository.existsById(id)){
            throw new IllegalArgumentException("You are trying to delete a booking that doesn't exist.");
        }
        bookingRepository.deleteById(id);
    }

    @Override
    public void deleteByRestaurantIdAndUserId(Long restaurantId, Long userId){
        if (!bookingRepository.getBookingWithRestaurantAndUserId(restaurantId, userId).isPresent()){
            throw new IllegalArgumentException("You are trying to delete a booking that doesn't exist.");
        }
        Booking booking = bookingRepository.getBookingWithRestaurantAndUserId(restaurantId, userId).get();
        bookingRepository.deleteById(booking.getId());
    }
}
