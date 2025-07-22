package com.arobertosm.restauranting.booking_service.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.arobertosm.restauranting.booking_service.model.Booking;

public interface BookingRepository extends JpaRepository<Booking, Long> {

    @Query("SELECT b FROM Booking b WHERE b.restaurantId = :restaurantId")
    List<Booking> getBookingsFromRestaurantId(Long restaurantId);

    @Query("SELECT b FROM Booking b WHERE b.userId = :userId")
    List<Booking> getBookingsFromUserId(Long userId);

    @Query("SELECT b FROM Booking b WHERE b.restaurantId = :restaurantId AND b.userId = :userId AND b.startDate = :startDate")
    Optional<Booking> getBookingWithRestaurantAndUserIdsAndStartDate(Long restaurantId, Long userId, LocalDateTime startDate);

    @Query("SELECT b FROM Booking b WHERE b.userId = :userId AND b.startDate >= :startDate")
    List<Booking> getRelevantBookingsFromUserId(Long userId, LocalDateTime startDate);

    @Query("SELECT b FROM Booking b WHERE b.restaurantId = :restaurantId AND b.userId = :userId")
    Optional<Booking> getBookingWithRestaurantAndUserId(Long restaurantId, Long userId);

    @Query("SELECT b FROM Booking b WHERE b.restaurantId = :restaurantId AND b.startDate >= :startDate AND b.endDate <= :endDate")
    List<Booking> getDailyBookingsFromRestaurantId(Long restaurantId, LocalDateTime startDate, LocalDateTime endDate);
}
