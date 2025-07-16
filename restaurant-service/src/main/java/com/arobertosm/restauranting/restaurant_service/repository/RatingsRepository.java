package com.arobertosm.restauranting.restaurant_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.arobertosm.restauranting.restaurant_service.model.Rating;

public interface RatingsRepository extends JpaRepository<Rating, Long>{
    
}
