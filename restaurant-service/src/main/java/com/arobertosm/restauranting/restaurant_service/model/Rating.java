package com.arobertosm.restauranting.restaurant_service.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@Table(name="ratings", uniqueConstraints = @UniqueConstraint(columnNames = {"restaurant_id", "user_id"}))
public class Rating {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id", nullable = false)
    private Restaurant restaurant;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private int ratingValue;

    @Column(length = 1000)
    private String comment;

    @Column(nullable = false)
    private LocalDateTime creationDate;

    @PrePersist
    protected void onCreate(){
        creationDate = LocalDateTime.now();
    }
}
