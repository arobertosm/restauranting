package com.arobertosm.restauranting.user_service.service;

import java.util.Optional;

import com.arobertosm.restauranting.user_service.dto.CreateUserRequestDto;
import com.arobertosm.restauranting.user_service.dto.UserResponseDto;

public interface UserService {
    Optional<UserResponseDto> getUserById(Long id);
    Optional<UserResponseDto> getUserByUsername(String username);
    UserResponseDto createUser(CreateUserRequestDto userDto, String imageUrl);
    Optional<UserResponseDto> updateUser(Long id, CreateUserRequestDto userUpdated, String imageUrl);
    void deleteUserById(Long id);
}
