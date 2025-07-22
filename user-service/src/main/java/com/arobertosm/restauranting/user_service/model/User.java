package com.arobertosm.restauranting.user_service.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "app_user")
@Data
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String firstName;

    @NotNull
    private String lastName;

    @NotNull
    private String username;

    @NotNull
    @Email
    private String email;

    @NotNull
    private String password;

    private String profilePictureUrl;
    private Boolean isAdmin;
    private Long restaurantId;
    
    @ElementCollection
    @CollectionTable(name = "user_ratings", joinColumns = @JoinColumn(name = "user_id"))
    private List<Long> ratingsId = new ArrayList<>();

    @ElementCollection
    @CollectionTable(name = "user_bookings", joinColumns = @JoinColumn(name = "user_id"))
    private List<Long> bookingsId = new ArrayList<>();

}
