package com.arobertosm.restauranting.restaurant_service.mapper;

import com.arobertosm.restauranting.restaurant_service.dto.CreateRestaurantRequestDto;
import com.arobertosm.restauranting.restaurant_service.dto.RestaurantResponseDto;
import com.arobertosm.restauranting.restaurant_service.model.Restaurant;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class RestaurantMapper {
    
    public Restaurant toEntity(CreateRestaurantRequestDto dto) {
        if (dto == null) {
            return null;
        }
        Restaurant restaurant = new Restaurant();
        restaurant.setName(dto.getName());
        restaurant.setDescription(dto.getDescription());
        restaurant.setAddress(dto.getAddress());
        restaurant.setCuisineType(dto.getCuisineType());
        restaurant.setMaxCustomers(dto.getMaxCustomers());
        restaurant.setPhoneNumber(dto.getPhoneNumber());
        restaurant.setOwnerId(dto.getOwnerId());
        restaurant.setImageUrls(dto.getImageUrls());
        restaurant.setCustomersEating(dto.getCustomersEating());
        restaurant.setEstimatedPrices(dto.getEstimatedPrices());
        restaurant.setRatingsId(dto.getRatingsId());
        restaurant.setBookingsId(dto.getBookingsId());
        return restaurant;
    }

    public RestaurantResponseDto toResponseDto(Restaurant restaurant) {
        if (restaurant == null) {
            return null;
        }
        RestaurantResponseDto dto = new RestaurantResponseDto();
        dto.setId(restaurant.getId());
        dto.setName(restaurant.getName());
        dto.setDescription(restaurant.getDescription());
        dto.setAddress(restaurant.getAddress());
        dto.setMaxCustomers(restaurant.getMaxCustomers());
        dto.setCuisineType(restaurant.getCuisineType());
        dto.setPhoneNumber(restaurant.getPhoneNumber());
        dto.setImageUrls(restaurant.getImageUrls());
        dto.setCustomersEating(restaurant.getCustomersEating());
        dto.setEstimatedPrices(restaurant.getEstimatedPrices());
        dto.setOwnerId(restaurant.getOwnerId());
        dto.setAverageRating(restaurant.getAverageRating());
        dto.setRatingsId(restaurant.getRatingsId());
        dto.setBookingsId(restaurant.getBookingsId());
        return dto;
    }

    public List<RestaurantResponseDto> toResponseDtoList(List<Restaurant> restaurants) {
        return restaurants.stream()
                .map(this::toResponseDto)
                .collect(Collectors.toList());
    }
}
