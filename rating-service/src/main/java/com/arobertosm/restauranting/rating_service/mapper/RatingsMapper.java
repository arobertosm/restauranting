package com.arobertosm.restauranting.rating_service.mapper;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;
import com.arobertosm.restauranting.rating_service.dto.CreateRatingRequestDto;
import com.arobertosm.restauranting.rating_service.dto.RatingResponseDto;
import com.arobertosm.restauranting.rating_service.model.Rating;

@Component
public class RatingsMapper {
    
    public Rating toEntity(CreateRatingRequestDto dto) {
        if (dto == null) {
            return null;
        }
        Rating rating = new Rating();
        rating.setRestaurantId(dto.getRestaurantId());
        rating.setUserId(dto.getUserId());
        rating.setRatingValue(dto.getRatingValue());
        rating.setComment(dto.getComment());
        return rating;
    }

    public RatingResponseDto toResponseDto(Rating rating) {
        if (rating == null){
            return null;
        }
        RatingResponseDto dto = new RatingResponseDto();
        dto.setId(rating.getId());
        dto.setRestaurantId(rating.getRestaurantId());
        dto.setUserId(rating.getUserId());
        dto.setRatingValue(rating.getRatingValue());
        dto.setComment(rating.getComment());
        dto.setCreationDate(rating.getCreationDate());
        return dto;
    }

    public List<RatingResponseDto> toResponseDtoList(List<Rating> ratings){
        return ratings.stream()
                .map(this::toResponseDto)
                .collect(Collectors.toList());
    }
}
