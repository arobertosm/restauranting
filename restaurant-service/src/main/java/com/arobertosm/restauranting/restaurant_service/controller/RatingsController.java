package com.arobertosm.restauranting.restaurant_service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.arobertosm.restauranting.restaurant_service.model.Rating;
import com.arobertosm.restauranting.restaurant_service.model.Restaurant;
import com.arobertosm.restauranting.restaurant_service.repository.RatingsRepository;
import com.arobertosm.restauranting.restaurant_service.repository.RestaurantRepository;

@RestController
@RequestMapping("/ratings")
public class RatingsController {
    
    @Autowired
    private RatingsRepository ratingsRepository;

    @Autowired
    private RestaurantRepository restaurantRepository;

    @PostMapping("/restaurant/{restaurantId}")
    public ResponseEntity<?> rateRestaurant(@PathVariable Long restaurantId, @RequestBody Rating ratingRequest){
        Restaurant restaurant = restaurantRepository.findById(restaurantId).orElseThrow(() -> new RuntimeException("No se ha encontrado el restaurante con id: " + restaurantId));

        Rating newRating = new Rating();
        newRating.setRestaurant(restaurant);
        newRating.setUserId(ratingRequest.getUserId());
        newRating.setRatingValue(ratingRequest.getRatingValue());
        newRating.setComment(ratingRequest.getComment());

        try{
            ratingsRepository.save(newRating);

            restaurant.recalculateAverageRating();
            restaurantRepository.save(restaurant);

            return new ResponseEntity<>(newRating, HttpStatus.CREATED);

        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("El usuario ya ha valorado el restaurante.");
        }
    }
}
