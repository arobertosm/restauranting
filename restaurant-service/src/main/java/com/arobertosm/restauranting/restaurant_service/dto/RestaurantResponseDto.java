package com.arobertosm.restauranting.restaurant_service.dto;

import lombok.Data;
import java.util.List;

@Data
public class RestaurantResponseDto {
    private Long id;
    private String name;
    private String description;
    private String address;
    private String cuisineType;
    private String phoneNumber;
    private int maxCustomers;
    private List<String> imageUrls;
    private List<String> customersEating;
    private List<Double> estimatedPrices;
    private Long ownerId;
    private Double averageRating;
    private List<Long> ratingsId;
    private List<Long> bookingsId;
}