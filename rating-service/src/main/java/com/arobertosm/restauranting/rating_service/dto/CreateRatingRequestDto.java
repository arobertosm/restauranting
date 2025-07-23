package com.arobertosm.restauranting.rating_service.dto;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CreateRatingRequestDto {
    
    @NotNull
    private Long restaurantId;

    @NotNull
    private Long userId;

    @NotNull
    private int ratingValue;

    @Size(max = 1000, message = "{error.comment.length}")
    private String comment;

    @NotNull
    private LocalDateTime creationDate;
}
