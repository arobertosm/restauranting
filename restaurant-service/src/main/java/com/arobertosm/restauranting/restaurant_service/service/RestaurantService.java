package com.arobertosm.restauranting.restaurant_service.service;

import com.arobertosm.restauranting.restaurant_service.dto.CreateRestaurantRequestDto;
import com.arobertosm.restauranting.restaurant_service.dto.RestaurantResponseDto;
import java.util.List;
import java.util.Optional;

public interface RestaurantService {
    List<RestaurantResponseDto> getAllRestaurants();
    Optional<RestaurantResponseDto> findById(Long id);
    RestaurantResponseDto createRestaurant(CreateRestaurantRequestDto restaurantRequestDto, List<String> imageUrls);
    Optional<RestaurantResponseDto> update(Long id, CreateRestaurantRequestDto restaurantUpdated, List<String> imageUrls);
    void deleteById(Long id);
    Integer getMaxCustomersFromRestaurantId(Long id);
}
