package com.arobertosm.restauranting.booking_service.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.arobertosm.restauranting.booking_service.dto.BookingResponseDto;
import com.arobertosm.restauranting.booking_service.dto.CreateBookingRequestDto;
import com.arobertosm.restauranting.booking_service.model.Booking;

@Component
public class BookingMapper {
    
    public Booking toEntity(CreateBookingRequestDto dto) {
        if (dto == null){
            return null;
        }
        Booking booking = new Booking();
        booking.setRestaurantId(dto.getRestaurantId());
        booking.setUserId(dto.getUserId());
        booking.setCustomersNumber(dto.getCustomersNumber());
        booking.setStartDate(dto.getStartDate());
        booking.setEndDate(dto.getEndDate());
        return booking;
    }

    public BookingResponseDto toResponseDto(Booking booking) {
        if (booking == null){
            return null;
        }
        BookingResponseDto dto = new BookingResponseDto();
        dto.setId(booking.getId());
        dto.setRestaurantId(booking.getRestaurantId());
        dto.setUserId(booking.getUserId());
        dto.setCustomersNumber(booking.getCustomersNumber());
        dto.setStartDate(booking.getStartDate());
        dto.setEndDate(booking.getEndDate());
        return dto;
    }

    public List<BookingResponseDto> toResponseDtoList(List<Booking> bookings) {
        return bookings.stream()
                .map(this::toResponseDto)
                .collect(Collectors.toList());
    }
}
