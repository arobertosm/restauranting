package com.arobertosm.restauranting.restaurant_service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.ArrayList;
import java.util.List;
import com.arobertosm.restauranting.restaurant_service.dto.CreateRestaurantRequestDto;
import com.arobertosm.restauranting.restaurant_service.dto.RestaurantResponseDto;
import com.arobertosm.restauranting.restaurant_service.mapper.RestaurantMapper;
import com.arobertosm.restauranting.restaurant_service.model.Restaurant;

public class RestaurantMapperTest {

    private RestaurantMapper mapper = new RestaurantMapper();
    private Restaurant restaurant;
    private CreateRestaurantRequestDto restaurantRequestDto;

    @BeforeEach
    void setUp(){
        System.out.println("Setting up the variables for the tests...");
        restaurant = new Restaurant();
        restaurant.setId(999L);
        restaurant.setName("Restaurant for unit testing");
        restaurant.setAddress("Junit 5, Spring Boot, Java");
        restaurant.setDescription("This restaurant will be used for unit testing purposes. It will also be existing only during the tests.");
        restaurant.setPhoneNumber("999999999");
        restaurant.setMaxCustomers(25);
        restaurant.setOwnerId(998L);
        restaurantRequestDto = new CreateRestaurantRequestDto();
        restaurantRequestDto.setName("Restaurant dto request for unit testing");
        restaurantRequestDto.setDescription("This restaurant dto request will be used for unit testing purposes and will also be existing only during the tests.");
        restaurantRequestDto.setAddress("Junit 5, Spring Boot, Java");
        restaurantRequestDto.setMaxCustomers(15);
        restaurantRequestDto.setPhoneNumber("111111111");
        restaurantRequestDto.setOwnerId(999L);
    }

    @Test
    void testPositiveMapDtoToEntity(){
        Restaurant restaurantAux = mapper.toEntity(restaurantRequestDto);

        assertEquals(restaurantRequestDto.getName(), restaurantAux.getName());
        assertEquals(restaurantRequestDto.getDescription(), restaurantAux.getDescription());
        assertEquals(restaurantRequestDto.getAddress(), restaurantAux.getAddress());
        assertEquals(restaurantRequestDto.getMaxCustomers(), restaurantAux.getMaxCustomers());
        assertEquals(restaurantRequestDto.getPhoneNumber(), restaurantAux.getPhoneNumber());
        assertEquals(restaurantRequestDto.getOwnerId(), restaurantAux.getOwnerId());
    }

    @Test 
    void testPositiveMapEntityToResponseDto(){
        RestaurantResponseDto responseDto = mapper.toResponseDto(restaurant);

        assertEquals(restaurant.getId(), responseDto.getId());
        assertEquals(restaurant.getName(), responseDto.getName());
        assertEquals(restaurant.getAddress(), responseDto.getAddress());
        assertEquals(restaurant.getDescription(), responseDto.getDescription());
        assertEquals(restaurant.getPhoneNumber(), responseDto.getPhoneNumber());
        assertEquals(restaurant.getMaxCustomers(), responseDto.getMaxCustomers());
        assertEquals(restaurant.getOwnerId(), responseDto.getOwnerId());
    }

    @Test
    void testPositiveMapListEntityToListResponseDto(){
        List<Restaurant> listRestaurant = new ArrayList<Restaurant>();
        listRestaurant.add(restaurant);

        List<RestaurantResponseDto> listResponseDto = mapper.toResponseDtoList(listRestaurant);
        assertEquals(listResponseDto.size(), 1); //There should only be one restaurant in the list.
        int counter = 0;
        for (RestaurantResponseDto r : listResponseDto){
            for (Restaurant res : listRestaurant){
                assertEquals(r.getId(), res.getId());
                assertEquals(r.getName(), res.getName());
                assertEquals(r.getAddress(), res.getAddress());
                assertEquals(r.getDescription(), res.getDescription());
                assertEquals(r.getPhoneNumber(), res.getPhoneNumber());
                assertEquals(r.getMaxCustomers(), res.getMaxCustomers());
                assertEquals(r.getOwnerId(), res.getOwnerId());
                counter++;
            }
        }
        assertEquals(counter, 1); //The fors section should only be executed once since there is only one restaurant.
    }
    
}
