package com.arobertosm.restauranting.restaurant_service.model;

import jakarta.persistence.*;
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

    private String name;
    private String description;
    private String address;
    private String cuisineType;
    private String phoneNumber;

    @ElementCollection
    @CollectionTable(name = "restaurant_image_urls", joinColumns = @JoinColumn(name = "restaurant_id"))
    private List<String> imageUrls;

    @ElementCollection
    @CollectionTable(name = "restaurant_customers_eating", joinColumns = @JoinColumn(name = "restaurant_id"))
    private List<String> customersEating;

    @ElementCollection
    @CollectionTable(name = "restaurant_estimated_prices", joinColumns = @JoinColumn(name = "restaurant_id"))
    private List<Double> estimatedPrices;

    private Long ownerId;

    @OneToMany(mappedBy = "restaurant", 
            cascade = CascadeType.ALL, 
            orphanRemoval = true
    )
    private List<Rating> ratings = new ArrayList<>();

    private Double averageRating;

    public void recalculateAverageRating(){
        if (ratings == null || ratings.isEmpty()){
            this.averageRating = 0.0;
        } else {
            this.averageRating = ratings.stream().mapToInt(Rating::getRatingValue).average().orElse(0.0);
        }
    }
}