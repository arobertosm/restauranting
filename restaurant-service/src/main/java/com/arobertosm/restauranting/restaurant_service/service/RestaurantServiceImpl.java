package com.arobertosm.restauranting.restaurant_service.service;

import com.arobertosm.restauranting.restaurant_service.dto.CreateRestaurantRequestDto;
import com.arobertosm.restauranting.restaurant_service.dto.RestaurantResponseDto;
import com.arobertosm.restauranting.restaurant_service.mapper.RestaurantMapper;
import com.arobertosm.restauranting.restaurant_service.model.Restaurant;
import com.arobertosm.restauranting.restaurant_service.repository.RestaurantRepository;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RestaurantServiceImpl implements RestaurantService {
    
    private final RestaurantRepository restaurantRepository;
    private final RestaurantMapper restaurantMapper;

    @Autowired
    public RestaurantServiceImpl(RestaurantRepository restaurantRepository, RestaurantMapper restaurantMapper) {
        this.restaurantRepository = restaurantRepository;
        this.restaurantMapper = restaurantMapper;
    }

    @Override
    public List<RestaurantResponseDto> getAllRestaurants() {
        List<Restaurant> restaurants = restaurantRepository.findAll();
        return restaurantMapper.toResponseDtoList(restaurants);
    }

    @Override
    public Optional<RestaurantResponseDto> findById(Long id) {
        return restaurantRepository.findById(id)
                .map(restaurantMapper::toResponseDto);
    }

    @Override
    @Transactional
    public RestaurantResponseDto createRestaurant(CreateRestaurantRequestDto restaurantRequestDto, List<String> imageUrls) {
        Optional<Restaurant> existingRestaurantAux = restaurantRepository.findByPhoneNumber(restaurantRequestDto.getPhoneNumber());
        if (existingRestaurantAux.isPresent()) {
            throw new IllegalArgumentException("A restaurant with the phone number " + restaurantRequestDto.getPhoneNumber() + " already exists.");
        }
        Restaurant restaurantToSave = restaurantMapper.toEntity(restaurantRequestDto);
        restaurantToSave.setImageUrls(imageUrls);
        Restaurant savedRestaurant = restaurantRepository.save(restaurantToSave);
        return restaurantMapper.toResponseDto(savedRestaurant);
    }

    @Override
    public Optional<RestaurantResponseDto> update(Long id, CreateRestaurantRequestDto restaurantRequestUpdate, List<String> imageUrls){
        Optional<Restaurant> existingRestaurantAux = restaurantRepository.findByPhoneNumber(restaurantRequestUpdate.getPhoneNumber());
        if (existingRestaurantAux.isPresent()) {
            throw new IllegalArgumentException("A restaurant with the phone number " + restaurantRequestUpdate.getPhoneNumber() + " already exists.");
        }        
        List<String> newUrlList = restaurantRequestUpdate.getImageUrls();
        for (String imageUrl : restaurantRequestUpdate.getImageUrls()) {
            if (!imageUrls.contains(imageUrl)){
                newUrlList.add(imageUrl);
            }
        }
        return restaurantRepository.findById(id)
                .map(existingRestaurant -> {
                    existingRestaurant.setName(restaurantRequestUpdate.getName());
                    existingRestaurant.setDescription(restaurantRequestUpdate.getDescription());
                    existingRestaurant.setAddress(restaurantRequestUpdate.getAddress());
                    existingRestaurant.setCuisineType(restaurantRequestUpdate.getCuisineType());
                    existingRestaurant.setPhoneNumber(restaurantRequestUpdate.getPhoneNumber());
                    existingRestaurant.setImageUrls(newUrlList);
                    existingRestaurant.setCustomersEating(restaurantRequestUpdate.getCustomersEating());
                    existingRestaurant.setEstimatedPrices(restaurantRequestUpdate.getEstimatedPrices());
                    existingRestaurant.setOwnerId(restaurantRequestUpdate.getOwnerId());
                    existingRestaurant.setRatingsId(restaurantRequestUpdate.getRatingsId());
                    existingRestaurant.setBookingsId(restaurantRequestUpdate.getBookingsId());
                    Restaurant updatedRestaurant = restaurantRepository.save(existingRestaurant);
                    return restaurantMapper.toResponseDto(updatedRestaurant);
                });
    }

    @Override
    public void deleteById(Long id){
        if (!restaurantRepository.existsById(id)){
            throw new IllegalArgumentException("You are trying to delete a restaurant that doesn't exist.");
        }
        restaurantRepository.deleteById(id);
    }

    @Override
    public Integer getMaxCustomersFromRestaurantId(Long id){
        if (!restaurantRepository.existsById(id)){
            throw new IllegalArgumentException("You are trying to reach a restaurant that doesn't exist.");
        }
        return restaurantRepository.findById(id).get().getMaxCustomers();
    }
}
