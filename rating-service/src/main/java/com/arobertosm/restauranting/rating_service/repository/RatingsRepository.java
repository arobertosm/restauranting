package com.arobertosm.restauranting.rating_service.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.arobertosm.restauranting.rating_service.model.Rating;

public interface RatingsRepository extends JpaRepository<Rating, Long>{
    
    @Query("SELECT r FROM Rating r WHERE r.userId = :userId")
    List<Rating> getRatingsWithUserId(Long userId);

    @Query("SELECT r FROM Rating r WHERE r.restaurantId = :restaurantId")
    List<Rating> getRatingsWithRestaurantId(Long restaurantId);

    @Query("SELECT r FROM Rating r WHERE r.restaurantId = :restaurantId AND r.userId = :userId")
    Optional<Rating> getRatingWithRestaurantIdAndUserId(Long restaurantId, Long userId);
}
