package com.arobertosm.restauranting.booking_service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.arobertosm.restauranting.booking_service.dto.BookingResponseDto;
import com.arobertosm.restauranting.booking_service.dto.CreateBookingRequestDto;
import com.arobertosm.restauranting.booking_service.mapper.BookingMapper;
import com.arobertosm.restauranting.booking_service.model.Booking;

public class BookingMapperTest {
    
    private BookingMapper mapper = new BookingMapper();
    private Booking booking;
    private CreateBookingRequestDto bookingRequestDto;

    @BeforeEach
    void setUp(){
        System.out.println("Setting up the variables for the tests...");
        booking = new Booking();
        booking.setId(999L);
        booking.setRestaurantId(999L);
        booking.setUserId(999L);
        booking.setCustomersNumber(5);
        booking.setStartDate(LocalDateTime.now());
        booking.setEndDate(LocalDateTime.now().plusHours(3L));
        bookingRequestDto = new CreateBookingRequestDto();
        bookingRequestDto.setRestaurantId(998L);
        bookingRequestDto.setUserId(998L);
        bookingRequestDto.setCustomersNumber(2);
        bookingRequestDto.setStartDate(LocalDateTime.now().plusHours(3L));
        bookingRequestDto.setEndDate(LocalDateTime.now().plusHours(6L));
    }

    @Test
    void testPositiveMapDtoToEntity(){
        Booking bookingAux = mapper.toEntity(bookingRequestDto);

        assertEquals(bookingAux.getRestaurantId(), bookingRequestDto.getRestaurantId());
        assertEquals(bookingAux.getUserId(), bookingRequestDto.getUserId());
        assertEquals(bookingAux.getCustomersNumber(), bookingRequestDto.getCustomersNumber());
        assertEquals(bookingAux.getStartDate(), bookingRequestDto.getStartDate());
        assertEquals(bookingAux.getEndDate(), bookingRequestDto.getEndDate());
    }

    @Test
    void testPositiveMapEntityToResponseDto(){
        BookingResponseDto responseDto = mapper.toResponseDto(booking);

        assertEquals(responseDto.getId(), booking.getId());
        assertEquals(responseDto.getRestaurantId(), booking.getRestaurantId());
        assertEquals(responseDto.getUserId(), booking.getUserId());
        assertEquals(responseDto.getCustomersNumber(), booking.getCustomersNumber());
        assertEquals(responseDto.getStartDate(), booking.getStartDate());
        assertEquals(responseDto.getEndDate(), booking.getEndDate());
    }

    @Test
    void testPositiveMapListEntityToListResponseDto(){
        List<Booking> listBooking = new ArrayList<Booking>();
        listBooking.add(booking);

        List<BookingResponseDto> listResponseDto = mapper.toResponseDtoList(listBooking);
        assertEquals(listResponseDto.size(), 1); //There should only be one booking in the list.
        int counter = 0;
        for (BookingResponseDto b : listResponseDto){
            for(Booking book : listBooking){
                assertEquals(b.getId(), book.getId());
                assertEquals(b.getRestaurantId(), book.getRestaurantId());
                assertEquals(b.getUserId(), book.getUserId());
                assertEquals(b.getCustomersNumber(), book.getCustomersNumber());
                assertEquals(b.getStartDate(), book.getStartDate());
                assertEquals(b.getEndDate(), book.getEndDate());
                counter++;
            }
        }
        assertEquals(counter, 1); //The fors section should only be executed once since there is only one booking.
    }
}
