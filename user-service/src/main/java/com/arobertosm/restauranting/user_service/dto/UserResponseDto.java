package com.arobertosm.restauranting.user_service.dto;

import java.util.List;

import lombok.Data;

@Data
public class UserResponseDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String username;
    private String password;
    private String profilePictureUrl;
    private Boolean isAdmin;
    private Long restaurantId;
    private List<Long> ratingsId;
    private List<Long> bookingsId;
}
