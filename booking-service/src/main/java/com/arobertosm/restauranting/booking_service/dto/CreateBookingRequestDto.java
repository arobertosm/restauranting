package com.arobertosm.restauranting.booking_service.dto;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateBookingRequestDto {
    
    @NotNull
    private Long restaurantId;

    @NotNull
    private Long userId;

    @NotNull
    private int customersNumber;

    @NotNull
    private LocalDateTime startDate;
    private LocalDateTime endDate;
}
