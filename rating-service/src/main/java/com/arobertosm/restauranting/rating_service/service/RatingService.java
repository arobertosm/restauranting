package com.arobertosm.restauranting.rating_service.service;

import java.util.List;
import java.util.Optional;
import com.arobertosm.restauranting.rating_service.dto.CreateRatingRequestDto;
import com.arobertosm.restauranting.rating_service.dto.RatingResponseDto;

public interface RatingService {
    Optional<RatingResponseDto> findById(Long ratingId);
    List<RatingResponseDto> getAllRatingsByUserId(Long userId);
    List<RatingResponseDto> getAllRatingsByRestaurantId(Long restaurantId);
    RatingResponseDto rateRestaurant(CreateRatingRequestDto ratingRequestDto);
    Optional<RatingResponseDto> update(Long id, CreateRatingRequestDto ratingUpdated);
    void deleteById(Long id);
    void deleteByRestaurantIdAndUserId(Long restaurantId, Long userId);
}
