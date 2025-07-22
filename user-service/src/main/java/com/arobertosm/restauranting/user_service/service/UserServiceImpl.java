package com.arobertosm.restauranting.user_service.service;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.arobertosm.restauranting.user_service.dto.CreateUserRequestDto;
import com.arobertosm.restauranting.user_service.dto.UserResponseDto;
import com.arobertosm.restauranting.user_service.mapper.UserMapper;
import com.arobertosm.restauranting.user_service.model.User;
import com.arobertosm.restauranting.user_service.repository.UserRepository;
import jakarta.transaction.Transactional;

@Service
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    public Optional<UserResponseDto> getUserById(Long id){
        return userRepository.findById(id)
                .map(userMapper::toResponseDto);
    }

    @Override
    public Optional<UserResponseDto> getUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .map(userMapper::toResponseDto);
    }

    @Override
    @Transactional
    public UserResponseDto createUser(CreateUserRequestDto userRequestDto, String imageUrl){
        Optional<User> existingUserAux = userRepository.findByUsername(userRequestDto.getUsername());
        if (existingUserAux.isPresent()){
            throw new IllegalArgumentException("The username: " + existingUserAux.get().getUsername() + " already exists.");
        }
        if (userRequestDto.getPassword() != userRequestDto.getPassword2()){
            throw new IllegalArgumentException("The passwords do not match.");
        }
        User userToSave = userMapper.toEntity(userRequestDto);
        userToSave.setProfilePictureUrl(imageUrl);
        User savedUser = userRepository.save(userToSave);
        return userMapper.toResponseDto(savedUser);
    }

    @Override
    public Optional<UserResponseDto> updateUser(Long id, CreateUserRequestDto userRequestUpdate, String imageUrl){
        Optional<User> existingUserAux = userRepository.findByUsername(userRequestUpdate.getUsername());
        if (existingUserAux.isPresent() && !existingUserAux.get().getId().equals(id)){
            throw new IllegalArgumentException("The username: " + existingUserAux.get().getUsername() + " already exists.");
        }
        if (userRequestUpdate.getPassword() != userRequestUpdate.getPassword2()){
            throw new IllegalArgumentException("The passwords do not match.");
        }
        return userRepository.findById(id)
                .map(existingUser -> {
                    existingUser.setFirstName(userRequestUpdate.getFirstName());
                    existingUser.setLastName(userRequestUpdate.getLastName());
                    existingUser.setUsername(userRequestUpdate.getUsername());
                    existingUser.setEmail(userRequestUpdate.getEmail());
                    existingUser.setPassword(userRequestUpdate.getPassword());
                    existingUser.setProfilePictureUrl(imageUrl);
                    existingUser.setRatingsId(userRequestUpdate.getRatingsId());
                    existingUser.setBookingsId(userRequestUpdate.getBookingsId());
                    User updatedUser = userRepository.save(existingUser);
                    return userMapper.toResponseDto(updatedUser);
                });
    }

    @Override
    public void deleteUserById(Long id) {
        if (!userRepository.existsById(id)) {
            throw new IllegalArgumentException("You are trying to delete a user that does not exist with id:" + id);
        }
        userRepository.deleteById(id);
    }
}
