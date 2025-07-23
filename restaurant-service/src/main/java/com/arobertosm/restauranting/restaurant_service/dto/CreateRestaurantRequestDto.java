package com.arobertosm.restauranting.restaurant_service.dto;

import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CreateRestaurantRequestDto {
    
    @NotBlank(message = "{error.name.blank}")
    @Size(min = 1, max = 100, message = "{error.name.length}")
    private String name;

    @Size(max = 600, message = "{error.illegal.usage}")
    private String description;

    @NotBlank(message = "{error.address.blank}")
    private String address;

    @NotNull(message = "{error.max.customers.blank}")
    private int maxCustomers;

    private String cuisineType;
    
    @NotNull(message = "{error.phone.blank}")
    private String phoneNumber;

    @NotNull(message = "{error.illegal.usage}")
    private Long ownerId;
    
    private List<String> imageUrls;
    private List<String> customersEating;
    private List<Double> estimatedPrices;
    private List<Long> ratingsId;
    private List<Long> bookingsId;
}
