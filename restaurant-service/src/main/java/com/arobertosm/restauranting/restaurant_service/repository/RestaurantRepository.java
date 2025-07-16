package com.arobertosm.restauranting.restaurant_service.repository;

import com.arobertosm.restauranting.restaurant_service.model.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {
    
}