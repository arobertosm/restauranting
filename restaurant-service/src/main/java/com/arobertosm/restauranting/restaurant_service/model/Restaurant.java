package com.arobertosm.restauranting.restaurant_service.model;

import jakarta.persistence.Entity;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class Restaurant {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String address;
    private String cuisineType;
    private String phoneNumber;
    
    @ElementCollection
    @CollectionTable(name = "restaurant_customers_eating", joinColumns = @JoinColumn(name = "restaurant_id"))
    private List<String> customersEating;

    @ElementCollection
    @CollectionTable(name = "restaurant_estimated_prices", joinColumns = @JoinColumn(name = "restaurant_id"))
    private List<Double> estimatedPrices;

}