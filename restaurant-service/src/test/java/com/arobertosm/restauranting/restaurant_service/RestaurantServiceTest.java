package com.arobertosm.restauranting.restaurant_service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import com.arobertosm.restauranting.restaurant_service.dto.CreateRestaurantRequestDto;
import com.arobertosm.restauranting.restaurant_service.dto.RestaurantResponseDto;
import com.arobertosm.restauranting.restaurant_service.mapper.RestaurantMapper;
import com.arobertosm.restauranting.restaurant_service.model.Restaurant;
import com.arobertosm.restauranting.restaurant_service.repository.RestaurantRepository;
import com.arobertosm.restauranting.restaurant_service.service.RestaurantServiceImpl;

@ExtendWith(MockitoExtension.class)
public class RestaurantServiceTest {
    
    @Mock
    private RestaurantRepository restaurantRepository;

    @InjectMocks
    private RestaurantServiceImpl restaurantService;

    private List<String> imagesEmpty = new ArrayList<String>();
    private Restaurant restaurant;
    private RestaurantResponseDto responseDto;
    private CreateRestaurantRequestDto requestDto;
    private Restaurant restaurantUpdated;
    private RestaurantResponseDto responseUpdated;
    private CreateRestaurantRequestDto requestUpdated;
    
    @Mock
    private RestaurantMapper mapper = new RestaurantMapper();

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
        restaurant.setOwnerId(999L);

        responseDto = new RestaurantResponseDto();
        responseDto.setId(999L);
        responseDto.setName("Restaurant for unit testing");
        responseDto.setAddress("Junit 5, Spring Boot, Java");
        responseDto.setDescription("This restaurant will be used for unit testing purposes. It will also be existing only during the tests.");
        responseDto.setPhoneNumber("999999999");
        responseDto.setMaxCustomers(25);
        responseDto.setOwnerId(999L);

        requestDto = new CreateRestaurantRequestDto();
        requestDto.setName("Restaurant for unit testing");
        requestDto.setAddress("Junit 5, Spring Boot, Java");
        requestDto.setDescription("This restaurant will be used for unit testing purposes. It will also be existing only during the tests.");
        requestDto.setPhoneNumber("999999999");
        requestDto.setMaxCustomers(25);
        requestDto.setOwnerId(999L);

        restaurantUpdated = new Restaurant();
        restaurantUpdated.setId(999L);
        restaurantUpdated.setName("Restaurant for unit testing changed");
        restaurantUpdated.setAddress("Junit 5, Spring Boot, Jav");
        restaurantUpdated.setDescription("This restaurant will be used for unit testing changed purposes. It will also be existing only during the tests.");
        restaurantUpdated.setPhoneNumber("111111111");
        restaurantUpdated.setMaxCustomers(20);
        restaurantUpdated.setOwnerId(998L);

        responseUpdated = new RestaurantResponseDto();
        responseUpdated.setName("Restaurant for unit testing changed");
        responseUpdated.setAddress("Junit 5, Spring Boot, Jav");
        responseUpdated.setDescription("This restaurant will be used for unit testing changed purposes. It will also be existing only during the tests.");
        responseUpdated.setPhoneNumber("111111111");
        responseUpdated.setMaxCustomers(20);
        responseUpdated.setOwnerId(998L);

        requestUpdated = new CreateRestaurantRequestDto();
        requestUpdated.setName("Restaurant for unit testing changed");
        requestUpdated.setAddress("Junit 5, Spring Boot, Jav");
        requestUpdated.setDescription("This restaurant will be used for unit testing changed purposes. It will also be existing only during the tests.");
        requestUpdated.setPhoneNumber("111111111");
        requestUpdated.setMaxCustomers(20);
        requestUpdated.setOwnerId(998L);
        requestUpdated.setRatingsId(new ArrayList<>());
        requestUpdated.setBookingsId(new ArrayList<>());
    }

    @Test
    void testPositiveGetAllRestaurants(){
        List<Restaurant> restaurantList = List.of(restaurant);
        List<RestaurantResponseDto> dtoList = List.of(responseDto);

        when(restaurantRepository.findAll()).thenReturn(restaurantList);

        when(mapper.toResponseDtoList(restaurantList)).thenReturn(dtoList);

        List<RestaurantResponseDto> restaurantsFound = restaurantService.getAllRestaurants();

        assertNotNull(restaurantsFound);
        assertEquals(1, restaurantsFound.size()); //It should only find the restaurant from the set up.
        assertEquals(restaurantsFound.get(0).getName(), restaurant.getName());

        verify(restaurantRepository, times(1)).findAll();
        verify(mapper, times(1)).toResponseDtoList(restaurantList);
    }

    @Test
    void testPositiveFindById(){

        when(restaurantRepository.findById(restaurant.getId())).thenReturn(Optional.of(restaurant));

        when(mapper.toResponseDto(restaurant)).thenReturn(responseDto);

        Optional<RestaurantResponseDto> responseFound = restaurantService.findById(restaurant.getId());

        assertNotNull(responseFound);
        assertEquals(responseFound.get().getName(), restaurant.getName()); //It should have found the restaurant from the set up.

        verify(restaurantRepository, times(1)).findById(999L);
        verify(mapper, times(1)).toResponseDto(restaurant);
    }

    @Test
    void testPositiveCreateRestaurant(){

        when(mapper.toEntity(requestDto)).thenReturn(restaurant);

        when(restaurantRepository.save(restaurant)).thenReturn(restaurant);

        when(mapper.toResponseDto(restaurant)).thenReturn(responseDto);

        RestaurantResponseDto responseFound = restaurantService.createRestaurant(requestDto, imagesEmpty);

        assertNotNull(responseFound);
        assertEquals(responseFound.getName(), restaurant.getName()); //It should have created the restaurant from the set up.

        verify(restaurantRepository, times(1)).save(restaurant);
        verify(mapper, times(1)).toEntity(requestDto);
        verify(mapper, times(1)).toResponseDto(restaurant);
    }

    @Test
    void testPositiveUpdateRestaurant(){

        when(restaurantRepository.findByPhoneNumber("111111111")).thenReturn(null);

        when(restaurantRepository.findById(restaurant.getId())).thenReturn(Optional.of(restaurant));

        when(restaurantRepository.save(restaurantUpdated)).thenReturn(restaurantUpdated);

        when(mapper.toResponseDto(restaurantUpdated)).thenReturn(responseUpdated);

        Optional<RestaurantResponseDto> restaurantUpdatedAux = restaurantService.update(restaurant.getId(), requestUpdated, imagesEmpty);

        assertTrue(restaurantUpdatedAux.isPresent());
        assertEquals(restaurantUpdated.getName(), restaurantUpdatedAux.get().getName());

        verify(restaurantRepository, times(1)).findByPhoneNumber("111111111");
        verify(restaurantRepository, times(1)).findById(restaurant.getId());
        verify(restaurantRepository, times(1)).save(restaurantUpdated);
        verify(mapper, times(1)).toResponseDto(restaurantUpdated);
    }

    @Test
    void testPositiveDeleteById(){
        when(restaurantRepository.existsById(restaurant.getId())).thenReturn(true);

        restaurantService.deleteById(restaurant.getId());

        verify(restaurantRepository, times(1)).existsById(restaurant.getId());
        verify(restaurantRepository, times(1)).deleteById(restaurant.getId());
    }

    @Test
    void testPositiveGetMaxCustomersFromRestaurantId(){
        when(restaurantRepository.existsById(restaurant.getId())).thenReturn(true);

        when(restaurantRepository.findById(restaurant.getId())).thenReturn(Optional.of(restaurant));

        Integer maxCustomersFound = restaurantService.getMaxCustomersFromRestaurantId(restaurant.getId());

        assertEquals(maxCustomersFound, restaurant.getMaxCustomers());

        verify(restaurantRepository, times(1)).existsById(restaurant.getId());
        verify(restaurantRepository, times(1)).findById(restaurant.getId());
    }
}