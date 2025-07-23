package com.arobertosm.restauranting.restaurant_service.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class Restaurant {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String name;

    private String description;

    @NotNull
    private String address;

    private String cuisineType;

    @NotNull
    private String phoneNumber;

    @NotNull
    private int maxCustomers;

    @ElementCollection
    @CollectionTable(name = "restaurant_image_urls", joinColumns = @JoinColumn(name = "restaurant_id"))
    private List<String> imageUrls;

    @ElementCollection
    @CollectionTable(name = "restaurant_customers_eating", joinColumns = @JoinColumn(name = "restaurant_id"))
    private List<String> customersEating;

    @ElementCollection
    @CollectionTable(name = "restaurant_estimated_prices", joinColumns = @JoinColumn(name = "restaurant_id"))
    private List<Double> estimatedPrices;

    @NotNull
    private Long ownerId;

    @ElementCollection
    @CollectionTable(name = "restaurant_ratings", joinColumns = @JoinColumn(name = "restaurant_id"))
    private List<Long> ratingsId = new ArrayList<>();

    private Double averageRating;

    @ElementCollection
    @CollectionTable(name = "restaurant_bookings", joinColumns = @JoinColumn(name = "restaurant_id"))
    private List<Long> bookingsId = new ArrayList<>();
}