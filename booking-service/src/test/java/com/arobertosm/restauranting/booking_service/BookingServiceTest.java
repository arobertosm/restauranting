package com.arobertosm.restauranting.booking_service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.reactive.function.client.WebClient;

import com.arobertosm.restauranting.booking_service.dto.BookingResponseDto;
import com.arobertosm.restauranting.booking_service.dto.CreateBookingRequestDto;
import com.arobertosm.restauranting.booking_service.mapper.BookingMapper;
import com.arobertosm.restauranting.booking_service.model.Booking;
import com.arobertosm.restauranting.booking_service.repository.BookingRepository;
import com.arobertosm.restauranting.booking_service.service.BookingServiceImpl;

@ExtendWith(MockitoExtension.class)
public class BookingServiceTest {
    
    @Mock
    private BookingRepository bookingRepository;

    @Mock
    private BookingMapper mapper = new BookingMapper();

    @Mock
    private WebClient.Builder webClientBuilder;

    @Mock
    private WebClient webClient;

    private BookingServiceImpl bookingService;

    private Booking booking;
    private BookingResponseDto responseDto;
    private CreateBookingRequestDto requestDto;
    private Booking bookingUpdated;
    private BookingResponseDto responseUpdated;
    private CreateBookingRequestDto requestUpdated;

    @BeforeEach
    void setUp(){
        System.out.println("Setting up the variables for the tests...");

        when(webClientBuilder.baseUrl(anyString())).thenReturn(webClientBuilder);
        when(webClientBuilder.build()).thenReturn(webClient);

        bookingService = new BookingServiceImpl(bookingRepository, mapper, webClientBuilder);

        booking = new Booking();
        booking.setId(999L);
        booking.setRestaurantId(999L);
        booking.setUserId(999L);
        booking.setCustomersNumber(3);
        booking.setStartDate(LocalDateTime.now());

        responseDto = new BookingResponseDto();
        responseDto.setId(999L);
        responseDto.setRestaurantId(999L);
        responseDto.setUserId(999L);
        responseDto.setCustomersNumber(3);
        responseDto.setStartDate(booking.getStartDate());
        
        requestDto = new CreateBookingRequestDto();
        requestDto.setRestaurantId(999L);
        requestDto.setUserId(999L);
        requestDto.setCustomersNumber(3);
        requestDto.setStartDate(booking.getStartDate());
        
        bookingUpdated = new Booking();
        bookingUpdated.setId(999L);
        bookingUpdated.setRestaurantId(998L);
        bookingUpdated.setUserId(998L);
        bookingUpdated.setCustomersNumber(5);
        bookingUpdated.setStartDate(booking.getStartDate().plusHours(3L));
        
        responseUpdated = new BookingResponseDto();
        responseUpdated.setId(999L);
        responseUpdated.setRestaurantId(998L);
        responseUpdated.setUserId(998L);
        responseUpdated.setCustomersNumber(5);
        responseUpdated.setStartDate(booking.getStartDate().plusHours(3L));

        requestUpdated = new CreateBookingRequestDto();
        requestUpdated.setRestaurantId(998L);
        requestUpdated.setUserId(998L);
        requestUpdated.setCustomersNumber(5);
        requestUpdated.setStartDate(booking.getStartDate().plusHours(3L));
    }

    @Test
    void testPositiveFindById(){

        when(bookingRepository.findById(booking.getId())).thenReturn(Optional.of(booking));

        when(mapper.toResponseDto(booking)).thenReturn(responseDto);

        Optional<BookingResponseDto> response = bookingService.findById(booking.getId());

        assertNotNull(response);
        assertTrue(response.isPresent());
        assertEquals(response.get().getId(), booking.getId());

        verify(bookingRepository, times(1)).findById(booking.getId());
        verify(mapper, times(1)).toResponseDto(booking);
    }

    @Test
    void testPositiveGetAllBookingsByRestaurantId(){
        List<Booking> bookingsList = List.of(booking);
        List<BookingResponseDto> responseList = List.of(responseDto);

        when(bookingRepository.getBookingsFromRestaurantId(booking.getRestaurantId())).thenReturn(bookingsList);

        when(mapper.toResponseDtoList(bookingsList)).thenReturn(responseList);

        List<BookingResponseDto> response = bookingService.getAllBookingsByRestaurantId(booking.getRestaurantId());

        assertNotNull(response);
        assertEquals(1, response.size()); //It should have only found the booking created in the set up.
        assertEquals(response.get(0).getId(), booking.getId());

        verify(bookingRepository, times(1)).getBookingsFromRestaurantId(booking.getRestaurantId());
        verify(mapper, times(1)).toResponseDtoList(bookingsList);
    }

    @Test
    void testPositiveGetAllBookingsByUserId(){
        List<Booking> bookingsList = List.of(booking);
        List<BookingResponseDto> responseList = List.of(responseDto);

        when(bookingRepository.getBookingsFromUserId(booking.getUserId())).thenReturn(bookingsList);

        when(mapper.toResponseDtoList(bookingsList)).thenReturn(responseList);

        List<BookingResponseDto> response = bookingService.getAllBookingsByUserId(booking.getUserId());

        assertNotNull(response);
        assertEquals(1, response.size()); //It should have only found the booking created in the set up.
        assertEquals(response.get(0).getId(), booking.getId());

        verify(bookingRepository, times(1)).getBookingsFromUserId(booking.getUserId());
        verify(mapper, times(1)).toResponseDtoList(bookingsList);
    }
    @Test
    void testPositiveDeleteById(){

        when(bookingRepository.existsById(booking.getId())).thenReturn(true);

        bookingService.deleteById(booking.getId());
        
        verify(bookingRepository, times(1)).existsById(booking.getId());
        verify(bookingRepository, times(1)).deleteById(booking.getId());
    }

    @Test
    void testPositiveDeleteByRestaurantIdAndUserId(){
        
        when(bookingRepository.getBookingWithRestaurantAndUserId(booking.getRestaurantId(), booking.getUserId())).thenReturn(Optional.of(booking));

        bookingService.deleteByRestaurantIdAndUserId(booking.getRestaurantId(), booking.getUserId());

        verify(bookingRepository, times(2)).getBookingWithRestaurantAndUserId(booking.getRestaurantId(), booking.getUserId());
        verify(bookingRepository, times(1)).deleteById(booking.getId());
    }
}
