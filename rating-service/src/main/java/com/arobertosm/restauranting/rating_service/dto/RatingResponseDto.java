package com.arobertosm.restauranting.rating_service.dto;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class RatingResponseDto {
    private Long Id;
    private Long restaurantId;
    private Long userId;
    private int ratingValue;
    private String comment;
    private LocalDateTime creationDate;
}
