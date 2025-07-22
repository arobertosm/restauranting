package com.arobertosm.restauranting.booking_service.controller;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.arobertosm.restauranting.booking_service.dto.BookingResponseDto;
import com.arobertosm.restauranting.booking_service.dto.CreateBookingRequestDto;
import com.arobertosm.restauranting.booking_service.service.BookingService; 
import jakarta.validation.Valid;

@RestController
@RequestMapping("/bookings")
public class BookingController {
    
    @Autowired
    private BookingService bookingService;

    @GetMapping("/restaurant/{id}")
    public List<BookingResponseDto> getAllBookingByRestaurantId(@PathVariable Long id){
        return bookingService.getAllBookingsByRestaurantId(id);
    }

    @GetMapping("/user/{id}")
    public List<BookingResponseDto> getAllBookingByUserId(@PathVariable Long id){
        return bookingService.getAllBookingsByUserId(id);
    }

    @PostMapping
    public ResponseEntity<BookingResponseDto> bookRestaurant(@Valid @RequestBody CreateBookingRequestDto bookingRequestDto){
        BookingResponseDto savedBookingDto = bookingService.bookRestaurant(bookingRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedBookingDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BookingResponseDto> update(@PathVariable Long id, @RequestBody CreateBookingRequestDto bookingUpdated){
        return bookingService.update(id, bookingUpdated)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBooking(@PathVariable Long id){
        if(bookingService.findById(id).isEmpty()){
            return ResponseEntity.notFound().build();
        }
        bookingService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{restaurantId}/{userId}")
    public ResponseEntity<Void> deleteBookingWithRestaurantAndUserId(@PathVariable Long restaurantId, @PathVariable Long userId){
        bookingService.deleteByRestaurantIdAndUserId(restaurantId, userId);
        return ResponseEntity.noContent().build();
    }
}
