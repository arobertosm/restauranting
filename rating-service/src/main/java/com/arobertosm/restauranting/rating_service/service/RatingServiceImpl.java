package com.arobertosm.restauranting.rating_service.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.arobertosm.restauranting.rating_service.dto.CreateRatingRequestDto;
import com.arobertosm.restauranting.rating_service.dto.RatingResponseDto;
import com.arobertosm.restauranting.rating_service.mapper.RatingsMapper;
import com.arobertosm.restauranting.rating_service.model.Rating;
import com.arobertosm.restauranting.rating_service.repository.RatingsRepository;
import jakarta.transaction.Transactional;

@Service
public class RatingServiceImpl implements RatingService {
    
    private final RatingsRepository ratingsRepository;
    private final RatingsMapper ratingsMapper;

    @Autowired
    public RatingServiceImpl(RatingsRepository ratingsRepository, RatingsMapper ratingsMapper) {
        this.ratingsRepository = ratingsRepository;
        this.ratingsMapper = ratingsMapper;
    }

    @Override
    public Optional<RatingResponseDto> findById(Long ratingId){
        return ratingsRepository.findById(ratingId)
                .map(ratingsMapper::toResponseDto);
    }

    @Override
    public List<RatingResponseDto> getAllRatingsByUserId(Long userId) {
        List<Rating> ratingsFound = ratingsRepository.getRatingsWithUserId(userId);
        if (ratingsFound == null || ratingsFound.isEmpty()){
            return new ArrayList<RatingResponseDto>();
        }
        return ratingsMapper.toResponseDtoList(ratingsFound);
    }

    @Override
    public List<RatingResponseDto> getAllRatingsByRestaurantId(Long restaurantId) {
        List<Rating> ratingsFound = ratingsRepository.getRatingsWithRestaurantId(restaurantId);
        if (ratingsFound == null || ratingsFound.isEmpty()){
            return new ArrayList<RatingResponseDto>();
        }
        return ratingsMapper.toResponseDtoList(ratingsFound);
    }

    @Override
    @Transactional
    public RatingResponseDto rateRestaurant(CreateRatingRequestDto ratingRequestDto) {
        Optional<Rating> existingRatingAux = ratingsRepository.getRatingWithRestaurantIdAndUserId(ratingRequestDto.getRestaurantId(), ratingRequestDto.getUserId());
        if (existingRatingAux.isPresent()){
            throw new IllegalArgumentException("A user can only rate a restaurant once.");
        }
        Rating ratingToSave = ratingsMapper.toEntity(ratingRequestDto);
        Rating ratingSaved = ratingsRepository.save(ratingToSave);
        return ratingsMapper.toResponseDto(ratingSaved);
    }

    @Override
    public Optional<RatingResponseDto> update(Long id, CreateRatingRequestDto ratingUpdated) {
        Optional<Rating> existingRatingAux = ratingsRepository.getRatingWithRestaurantIdAndUserId(ratingUpdated.getRestaurantId(), ratingUpdated.getUserId());
        if (existingRatingAux.isPresent()){
            throw new IllegalArgumentException("A user can only rate a restaurant once.");
        }
        return ratingsRepository.findById(id)
                .map(existingRating -> {
                    existingRating.setRestaurantId(ratingUpdated.getRestaurantId());
                    existingRating.setUserId(ratingUpdated.getUserId());
                    existingRating.setRatingValue(ratingUpdated.getRatingValue());
                    existingRating.setComment(ratingUpdated.getComment());
                    existingRating.setCreationDate(LocalDateTime.now());
                    Rating updatedRating = ratingsRepository.save(existingRating);
                    return ratingsMapper.toResponseDto(updatedRating);
                });
    }

    @Override
    public void deleteById(Long id) {
        if (!ratingsRepository.existsById(id)){
            throw new IllegalArgumentException("You are trying to delete a rating that doesn't exist.");
        }
        ratingsRepository.deleteById(id);
    }

    @Override
    public void deleteByRestaurantIdAndUserId(Long restaurantId, Long userId){
        if (!ratingsRepository.getRatingWithRestaurantIdAndUserId(restaurantId, userId).isPresent()){
            throw new IllegalArgumentException("You are trying to delete a rating that doesn't exist.");
        }
        Rating rating = ratingsRepository.getRatingWithRestaurantIdAndUserId(restaurantId, userId).get();
        ratingsRepository.deleteById(rating.getId());
    }
}
