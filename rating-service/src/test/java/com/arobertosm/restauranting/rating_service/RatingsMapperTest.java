package com.arobertosm.restauranting.rating_service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.arobertosm.restauranting.rating_service.dto.CreateRatingRequestDto;
import com.arobertosm.restauranting.rating_service.dto.RatingResponseDto;
import com.arobertosm.restauranting.rating_service.mapper.RatingsMapper;
import com.arobertosm.restauranting.rating_service.model.Rating;

public class RatingsMapperTest {
    
    private RatingsMapper mapper = new RatingsMapper();
    private Rating rating;
    private CreateRatingRequestDto ratingRequestDto;

    @BeforeEach
    void setUp(){
        System.out.println("Setting up the variables for the tests...");
        rating = new Rating();
        rating.setId(999L);
        rating.setRestaurantId(999L);
        rating.setUserId(999L);
        rating.setRatingValue(5);
        rating.setComment("This rating will be used only for testing purposes and won't exist after the tests end.");
        rating.setCreationDate(LocalDateTime.now());
        ratingRequestDto = new CreateRatingRequestDto();
        ratingRequestDto.setRestaurantId(998L);
        ratingRequestDto.setUserId(998L);
        ratingRequestDto.setRatingValue(3);
        ratingRequestDto.setComment("This rating will be used only for testing. It will also stop existing after the tests end.");
    }

    @Test
    void testPositiveMapDtoToEntity(){
        Rating ratingAux = mapper.toEntity(ratingRequestDto);

        assertEquals(ratingAux.getRestaurantId(), ratingRequestDto.getRestaurantId());
        assertEquals(ratingAux.getUserId(), ratingRequestDto.getUserId());
        assertEquals(ratingAux.getRatingValue(), ratingRequestDto.getRatingValue());
        assertEquals(ratingAux.getComment(), ratingRequestDto.getComment());
    }

    @Test
    void testPositiveMapEntityToResponseDto(){
        RatingResponseDto responseDto = mapper.toResponseDto(rating);

        assertEquals(responseDto.getId(), rating.getId());
        assertEquals(responseDto.getRestaurantId(), rating.getRestaurantId());
        assertEquals(responseDto.getUserId(), rating.getUserId());
        assertEquals(responseDto.getRatingValue(), rating.getRatingValue());
        assertEquals(responseDto.getComment(), rating.getComment());
    }

    @Test
    void testPositiveMapListEntityToListResponseDto(){
        List<Rating> listRating = new ArrayList<Rating>();
        listRating.add(rating);

        List<RatingResponseDto> listResponseDto = mapper.toResponseDtoList(listRating);
        assertEquals(listResponseDto.size(), 1); //There should only be one rating in the list.
        int counter = 0;
        for (RatingResponseDto r : listResponseDto){
            for (Rating ra : listRating){
                assertEquals(r.getId(), ra.getId());
                assertEquals(r.getRestaurantId(), ra.getRestaurantId());
                assertEquals(r.getUserId(), ra.getUserId());
                assertEquals(r.getRatingValue(), ra.getRatingValue());
                assertEquals(r.getComment(), ra.getComment());
                counter++;
            }
        }
        assertEquals(counter, 1); //The fors section should only be executed once since there is only one rating.
    }
}
