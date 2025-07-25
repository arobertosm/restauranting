package com.arobertosm.restauranting.restaurant_service.repository;

import com.arobertosm.restauranting.restaurant_service.model.Restaurant;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {
    
    @Query("SELECT r FROM Restaurant r WHERE r.phoneNumber = :phoneNumber")
    Optional<Restaurant> findByPhoneNumber(String phoneNumber);
}