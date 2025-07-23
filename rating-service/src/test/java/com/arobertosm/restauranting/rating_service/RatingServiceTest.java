package com.arobertosm.restauranting.rating_service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import com.arobertosm.restauranting.rating_service.dto.CreateRatingRequestDto;
import com.arobertosm.restauranting.rating_service.dto.RatingResponseDto;
import com.arobertosm.restauranting.rating_service.mapper.RatingsMapper;
import com.arobertosm.restauranting.rating_service.model.Rating;
import com.arobertosm.restauranting.rating_service.repository.RatingsRepository;
import com.arobertosm.restauranting.rating_service.service.RatingServiceImpl;

@ExtendWith(MockitoExtension.class)
public class RatingServiceTest {
    
    @Mock
    private RatingsRepository ratingsRepository;

    @InjectMocks
    private RatingServiceImpl ratingService;

    @Mock
    private RatingsMapper mapper = new RatingsMapper();

    private Rating rating;
    private RatingResponseDto responseDto;
    private CreateRatingRequestDto requestDto;
    private Rating ratingUpdated;
    private RatingResponseDto responseUpdated;
    private CreateRatingRequestDto requestUpdated;

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

        responseDto = new RatingResponseDto();
        responseDto.setId(999L);
        responseDto.setRestaurantId(999L);
        responseDto.setUserId(999L);
        responseDto.setRatingValue(5);
        responseDto.setComment("This rating will be used only for testing purposes and won't exist after the tests end.");
        responseDto.setCreationDate(rating.getCreationDate());

        requestDto = new CreateRatingRequestDto();
        requestDto.setRestaurantId(999L);
        requestDto.setUserId(999L);
        requestDto.setRatingValue(5);
        requestDto.setComment("This rating will be used only for testing purposes and won't exist after the tests end.");
        requestDto.setCreationDate(rating.getCreationDate());

        ratingUpdated = new Rating();
        ratingUpdated.setId(999L);
        ratingUpdated.setRestaurantId(999L);
        ratingUpdated.setUserId(999L);
        ratingUpdated.setRatingValue(4);
        ratingUpdated.setComment("This rating has been changed but will be used only for testing purposes and won't exist after the tests end.");
        ratingUpdated.setCreationDate(rating.getCreationDate().plusHours(2L));


        responseUpdated = new RatingResponseDto();
        responseUpdated.setId(999L);
        responseUpdated.setRestaurantId(999L);
        responseUpdated.setUserId(999L);
        responseUpdated.setRatingValue(4);
        responseUpdated.setComment("This rating has been changed but will be used only for testing purposes and won't exist after the tests end.");
        responseUpdated.setCreationDate(rating.getCreationDate().plusHours(2L));

        requestUpdated = new CreateRatingRequestDto();
        requestUpdated.setRestaurantId(999L);
        requestUpdated.setUserId(999L);
        requestUpdated.setRatingValue(4);
        requestUpdated.setComment("This rating has been changed but will be used only for testing purposes and won't exist after the tests end.");
        requestUpdated.setCreationDate(rating.getCreationDate().plusHours(2L));
    }

    @Test
    void testPositiveFindById(){

        when(ratingsRepository.findById(rating.getId())).thenReturn(Optional.of(rating));

        when(mapper.toResponseDto(rating)).thenReturn(responseDto);

        Optional<RatingResponseDto> ratingFound = ratingService.findById(rating.getId());

        assertNotNull(ratingFound);
        assertTrue(ratingFound.isPresent());
        assertEquals(ratingFound.get().getUserId(), rating.getUserId());
        assertEquals(ratingFound.get().getRestaurantId(), rating.getRestaurantId());
        assertEquals(ratingFound.get().getComment(), rating.getComment());
        assertEquals(ratingFound.get().getCreationDate(), rating.getCreationDate());

        verify(ratingsRepository, times(1)).findById(rating.getId());
        verify(mapper, times(1)).toResponseDto(rating);
    }

    @Test
    void testPositiveGetAllRatingsByUserId(){
        List<Rating> ratingsList = List.of(rating);
        List<RatingResponseDto> responsesList = List.of(responseDto);

        when(ratingsRepository.getRatingsWithUserId(rating.getUserId())).thenReturn(ratingsList);

        when(mapper.toResponseDtoList(ratingsList)).thenReturn(responsesList);

        List<RatingResponseDto> ratingsFound = ratingService.getAllRatingsByUserId(rating.getUserId());

        assertNotNull(ratingsFound);
        assertEquals(ratingsFound.size(), 1); //It should have only found the rating from the set up.
        assertEquals(ratingsFound.get(0).getUserId(), rating.getUserId());
        assertEquals(ratingsFound.get(0).getRestaurantId(), rating.getRestaurantId());

        verify(ratingsRepository, times(1)).getRatingsWithUserId(rating.getUserId());
        verify(mapper, times(1)).toResponseDtoList(ratingsList);
    }

    @Test
    void testPositiveGetAllRatingsByRestaurantId(){
        List<Rating> ratingsList = List.of(rating);
        List<RatingResponseDto> responsesList = List.of(responseDto);

        when(ratingsRepository.getRatingsWithRestaurantId(rating.getRestaurantId())).thenReturn(ratingsList);

        when(mapper.toResponseDtoList(ratingsList)).thenReturn(responsesList);

        List<RatingResponseDto> ratingsFound = ratingService.getAllRatingsByRestaurantId(rating.getRestaurantId());

        assertNotNull(ratingsFound);
        assertEquals(ratingsFound.size(), 1); //It should have only found the rating from the set up.
        assertEquals(ratingsFound.get(0).getUserId(), rating.getUserId());
        assertEquals(ratingsFound.get(0).getRestaurantId(), rating.getRestaurantId());

        verify(ratingsRepository, times(1)).getRatingsWithRestaurantId(rating.getRestaurantId());
        verify(mapper, times(1)).toResponseDtoList(ratingsList);
    }

    @Test
    void testPositiveRateRestaurant(){
        
        when(ratingsRepository.getRatingWithRestaurantIdAndUserId(requestDto.getRestaurantId(), requestDto.getUserId())).thenReturn(null);

        when(mapper.toEntity(requestDto)).thenReturn(rating);

        when(ratingsRepository.save(rating)).thenReturn(rating);

        when(mapper.toResponseDto(rating)).thenReturn(responseDto);

        RatingResponseDto response = ratingService.rateRestaurant(requestDto);

        assertNotNull(response);
        assertEquals(response.getId(), rating.getId());

        verify(ratingsRepository, times(1)).getRatingWithRestaurantIdAndUserId(requestDto.getRestaurantId(), requestDto.getUserId());
        verify(mapper, times(1)).toEntity(requestDto);
        verify(ratingsRepository, times(1)).save(rating);
        verify(mapper, times(1)).toResponseDto(rating);
    }

    @Test
    void testPositiveUpdateRating(){

        when(ratingsRepository.getRatingWithRestaurantIdAndUserId(requestUpdated.getRestaurantId(), requestUpdated.getUserId())).thenReturn(null);

        when(ratingsRepository.findById(rating.getId())).thenReturn(Optional.of(rating));

        when(ratingsRepository.save(ratingUpdated)).thenReturn(ratingUpdated);

        when(mapper.toResponseDto(ratingUpdated)).thenReturn(responseUpdated);

        Optional<RatingResponseDto> response = ratingService.update(rating.getId(), requestUpdated);

        assertNotNull(response);
        assertTrue(response.isPresent());
        assertEquals(response.get().getUserId(), ratingUpdated.getUserId());
        assertEquals(response.get().getRestaurantId(), ratingUpdated.getRestaurantId());

        verify(ratingsRepository, times(1)).getRatingWithRestaurantIdAndUserId(requestUpdated.getRestaurantId(), requestUpdated.getUserId());
        verify(ratingsRepository, times(1)).findById(rating.getId());
        verify(ratingsRepository, times(1)).save(ratingUpdated);
        verify(mapper, times(1)).toResponseDto(ratingUpdated);
    }

    @Test
    void testPositiveDeleteById(){
        
        when(ratingsRepository.existsById(rating.getId())).thenReturn(true);

        ratingService.deleteById(rating.getId());

        verify(ratingsRepository, times(1)).existsById(rating.getId());
        verify(ratingsRepository, times(1)).deleteById(rating.getId());
    }

    @Test
    void testPositiveDeleteRestaurantIdAndUserId(){

        when(ratingsRepository.getRatingWithRestaurantIdAndUserId(rating.getRestaurantId(), rating.getUserId())).thenReturn(Optional.of(rating));

        ratingService.deleteByRestaurantIdAndUserId(rating.getRestaurantId(), rating.getUserId());

        verify(ratingsRepository, times(2)).getRatingWithRestaurantIdAndUserId(rating.getRestaurantId(), rating.getUserId());
        verify(ratingsRepository, times(1)).deleteById(rating.getId());
    }
}
