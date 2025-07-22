package com.arobertosm.restauranting.rating_service.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.arobertosm.restauranting.rating_service.dto.CreateRatingRequestDto;
import com.arobertosm.restauranting.rating_service.dto.RatingResponseDto;
import com.arobertosm.restauranting.rating_service.service.RatingService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/ratings")
public class RatingsController {

    @Autowired
    private RatingService ratingService;

    @GetMapping("/restaurant/{id}")
    public List<RatingResponseDto> getAllRatingsByRestaurantId(@PathVariable Long id) {
        return ratingService.getAllRatingsByRestaurantId(id);
    }

    @GetMapping("/user/{id}")
    public List<RatingResponseDto> getAllRatingsByUserId(@PathVariable Long id){
        return ratingService.getAllRatingsByUserId(id);
    }

    @PostMapping
    public ResponseEntity<RatingResponseDto> rateRestaurant(@Valid @RequestBody CreateRatingRequestDto ratingRequestDto) {
        RatingResponseDto savedRatingDto = ratingService.rateRestaurant(ratingRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedRatingDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RatingResponseDto> update(@PathVariable Long id, @Valid @RequestBody CreateRatingRequestDto ratingUpdated){
        return ratingService.update(id, ratingUpdated)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRating(@PathVariable Long id){
        if(ratingService.findById(id).isEmpty()){
            return ResponseEntity.notFound().build();
        }
        ratingService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{restaurantId}/{userId}")
    public ResponseEntity<Void> deleteRatingWithRestaurantIdAndUserId(@PathVariable Long restaurantId, @PathVariable Long userId){
        ratingService.deleteByRestaurantIdAndUserId(restaurantId, userId);
        return ResponseEntity.noContent().build();
    }
}
