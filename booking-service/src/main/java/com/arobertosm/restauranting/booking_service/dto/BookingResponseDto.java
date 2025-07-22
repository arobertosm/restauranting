package com.arobertosm.restauranting.booking_service.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class BookingResponseDto {
    private Long id;
    private Long restaurantId;
    private Long userId;
    private int customersNumber;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
}
