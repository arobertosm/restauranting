package com.arobertosm.restauranting.user_service.mapper;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;
import com.arobertosm.restauranting.user_service.dto.CreateUserRequestDto;
import com.arobertosm.restauranting.user_service.dto.UserResponseDto;
import com.arobertosm.restauranting.user_service.model.User;

@Component
public class UserMapper {

    public User toEntity(CreateUserRequestDto dto) {
        if (dto == null) {
            return null;
        }
        User user = new User();
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setUsername(dto.getUsername());
        user.setEmail(dto.getEmail());
        user.setPassword(dto.getPassword());
        user.setProfilePictureUrl(dto.getProfilePictureUrl());
        user.setIsAdmin(dto.getIsAdmin());
        user.setRestaurantId(dto.getRestaurantId());
        user.setRatingsId(dto.getRatingsId());
        user.setBookingsId(dto.getBookingsId());
        return user;
    }

    public UserResponseDto toResponseDto(User user) {
        if (user == null) {
            return null;
        }
        UserResponseDto dto = new UserResponseDto();
        dto.setId(user.getId());
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        dto.setUsername(user.getUsername());
        dto.setEmail(user.getEmail());
        dto.setPassword(user.getPassword());
        dto.setProfilePictureUrl(user.getProfilePictureUrl());
        dto.setIsAdmin(user.getIsAdmin());
        dto.setRestaurantId(user.getRestaurantId());
        dto.setRatingsId(user.getRatingsId());
        dto.setBookingsId(user.getBookingsId());
        return dto;
    }

    public List<UserResponseDto> toResponseDtoList(List<User> users) {
        return users.stream()
                .map(this::toResponseDto)
                .collect(Collectors.toList());
    }

}