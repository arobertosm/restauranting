package com.arobertosm.restauranting.booking_service.service;

import java.util.List;
import java.util.Optional;
import com.arobertosm.restauranting.booking_service.dto.BookingResponseDto;
import com.arobertosm.restauranting.booking_service.dto.CreateBookingRequestDto;

public interface BookingService {
    Optional<BookingResponseDto> findById(Long bookingId);
    List<BookingResponseDto> getAllBookingsByRestaurantId(Long restaurantId);
    List<BookingResponseDto> getAllBookingsByUserId(Long userId);
    BookingResponseDto bookRestaurant(CreateBookingRequestDto bookingRequestDto);
    Optional<BookingResponseDto> update(Long id, CreateBookingRequestDto bookingUpdated);
    void deleteById(Long id);
    void deleteByRestaurantIdAndUserId(Long restaurantId, Long userId);
}
