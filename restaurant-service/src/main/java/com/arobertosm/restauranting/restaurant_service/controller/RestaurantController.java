package com.arobertosm.restauranting.restaurant_service.controller;

import com.arobertosm.restauranting.common_service.IFileStorageService;
import com.arobertosm.restauranting.restaurant_service.dto.CreateRestaurantRequestDto;
import com.arobertosm.restauranting.restaurant_service.dto.RestaurantResponseDto;
import com.arobertosm.restauranting.restaurant_service.service.RestaurantService;
import jakarta.validation.Valid;
import jakarta.ws.rs.core.MediaType;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/restaurants")
public class RestaurantController {

    private final RestaurantService restaurantService;
    private final IFileStorageService fileStorageService;

    public RestaurantController(RestaurantService restaurantService, IFileStorageService fileStorageService) {
        this.restaurantService = restaurantService;
        this.fileStorageService = fileStorageService;
    }

    @GetMapping
    public List<RestaurantResponseDto> getAllRestaurants() {
        return restaurantService.getAllRestaurants();
    }

    @GetMapping("/{id}")
    public ResponseEntity<RestaurantResponseDto> getRestaurantById(@PathVariable Long id) {
        return restaurantService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA})
    public ResponseEntity<RestaurantResponseDto> createRestaurant(@Valid @RequestPart("restaurantData") CreateRestaurantRequestDto restaurantRequestDto, @RequestPart("images") List<MultipartFile> imageFiles) {
        List<String> imageUrls = imageFiles.stream()
                .map(file -> {
                    String baseFileName = "restaurant-" + UUID.randomUUID().toString();
                    return fileStorageService.store(file, "restaurants", baseFileName);
                })
                .collect(Collectors.toList());
        RestaurantResponseDto savedRestaurantDto = restaurantService.createRestaurant(restaurantRequestDto, imageUrls);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedRestaurantDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RestaurantResponseDto> updateRestaurant(@PathVariable Long id, @Valid @RequestPart("restaurantData") CreateRestaurantRequestDto restaurantUpdated, @RequestPart("images") List<MultipartFile> imageFiles) {
    List<String> imageUrls = imageFiles.stream()
                .map(file -> {
                    String baseFileName = "restaurant-" + UUID.randomUUID().toString();
                    return fileStorageService.store(file, "restaurants", baseFileName);
                })
                .collect(Collectors.toList());
        return restaurantService.update(id, restaurantUpdated, imageUrls)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRestaurant(@PathVariable Long id) {
        if(restaurantService.findById(id).isEmpty()){
            return ResponseEntity.notFound().build();
        }
        restaurantService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/max-customers")
    public ResponseEntity<Integer> getMaxCustomersWithId(@PathVariable Long id){
        if (restaurantService.getMaxCustomersFromRestaurantId(id) == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(restaurantService.getMaxCustomersFromRestaurantId(id));
    }
}