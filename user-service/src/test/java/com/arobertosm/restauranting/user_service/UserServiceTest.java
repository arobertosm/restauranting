package com.arobertosm.restauranting.user_service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.ArrayList;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import com.arobertosm.restauranting.user_service.dto.CreateUserRequestDto;
import com.arobertosm.restauranting.user_service.dto.UserResponseDto;
import com.arobertosm.restauranting.user_service.mapper.UserMapper;
import com.arobertosm.restauranting.user_service.model.User;
import com.arobertosm.restauranting.user_service.repository.UserRepository;
import com.arobertosm.restauranting.user_service.service.UserServiceImpl;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserMapper mapper = new UserMapper();

    private String imageEmpty = "";
    private User user;
    private UserResponseDto responseDto;
    private CreateUserRequestDto requestDto;
    private User userUpdated;
    private UserResponseDto responseUpdated;
    private CreateUserRequestDto requestUpdated;

    @BeforeEach
    void setUp(){
        System.out.println("Setting up the variables for the tests...");

        user = new User();
        user.setId(999L);
        user.setFirstName("Junit5");
        user.setLastName("Java SpringBoot");
        user.setUsername("junit5SpringBootTest");
        user.setEmail("junit5@mail.com");
        user.setPassword("Junit5TestSpringBoot!");

        responseDto = new UserResponseDto();
        responseDto.setId(999L);
        responseDto.setFirstName("Junit5");
        responseDto.setLastName("Java SpringBoot");
        responseDto.setUsername("junit5SpringBootTest");
        responseDto.setEmail("junit5@mail.com");
        responseDto.setPassword("Junit5TestSpringBoot!");

        requestDto = new CreateUserRequestDto();
        requestDto.setFirstName("Junit5");
        requestDto.setLastName("Java SpringBoot");
        requestDto.setUsername("junit5SpringBootTest");
        requestDto.setEmail("junit5@mail.com");
        requestDto.setPassword("Junit5TestSpringBoot!");
        requestDto.setPassword2("Junit5TestSpringBoot!");

        userUpdated = new User();
        userUpdated.setId(999L);
        userUpdated.setFirstName("JUNIT5");
        userUpdated.setLastName("Java Springboot");
        userUpdated.setUsername("Junit5Springbootest");
        userUpdated.setEmail("junit5changed@mail.com");
        userUpdated.setPassword("Junit5TestSpringBoot!CHANGED");
        
        responseUpdated = new UserResponseDto();
        responseUpdated.setFirstName("JUNIT5");
        responseUpdated.setLastName("Java Springboot");
        responseUpdated.setUsername("Junit5Springbootest");
        responseUpdated.setEmail("junit5changed@mail.com");
        responseUpdated.setPassword("Junit5TestSpringBoot!CHANGED");
        
        requestUpdated = new CreateUserRequestDto();
        requestUpdated.setFirstName("JUNIT5");
        requestUpdated.setLastName("Java Springboot");
        requestUpdated.setUsername("Junit5Springbootest");
        requestUpdated.setEmail("junit5changed@mail.com");
        requestUpdated.setPassword("Junit5TestSpringBoot!CHANGED");
        requestUpdated.setPassword2("Junit5TestSpringBoot!CHANGED");
        requestUpdated.setBookingsId(new ArrayList<>());
        requestUpdated.setRatingsId(new ArrayList<>());
    }
    
    @Test
    void testPositiveGetUserById(){

        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        
        when(mapper.toResponseDto(user)).thenReturn(responseDto);

        Optional<UserResponseDto> userFound = userService.getUserById(user.getId());

        assertNotNull(userFound);
        assertTrue(userFound.isPresent());
        assertEquals(userFound.get().getUsername(), user.getUsername());

        verify(userRepository, times(1)).findById(user.getId());
        verify(mapper, times(1)).toResponseDto(user);
    }

    @Test
    void testPositiveGetUserByUsername(){
        
        when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.of(user));

        when(mapper.toResponseDto(user)).thenReturn(responseDto);

        Optional<UserResponseDto> userFound = userService.getUserByUsername(user.getUsername());

        assertNotNull(userFound);
        assertTrue(userFound.isPresent());
        assertEquals(userFound.get().getId(), user.getId());

        verify(userRepository, times(1)).findByUsername(user.getUsername());
        verify(mapper, times(1)).toResponseDto(user);
    }

    @Test
    void testPositiveCreateUser(){

        when(userRepository.findByUsername(requestDto.getUsername())).thenReturn(null);

        when(mapper.toEntity(requestDto)).thenReturn(user);

        when(userRepository.save(user)).thenReturn(user);

        when(mapper.toResponseDto(user)).thenReturn(responseDto);

        UserResponseDto userCreated = userService.createUser(requestDto, imageEmpty);

        assertNotNull(userCreated);
        assertEquals(user.getEmail(), userCreated.getEmail());

        verify(userRepository, times(1)).findByUsername(requestDto.getUsername());
        verify(mapper, times(1)).toEntity(requestDto);
        verify(userRepository, times(1)).save(user);
        verify(mapper, times(1)).toResponseDto(user);
    }

    @Test
    void testNegativeCreateUserPasswordsDontMatch(){

        when(userRepository.findByUsername(requestDto.getUsername())).thenReturn(null);

        requestDto.setPassword2("error");

        Exception e = assertThrows(IllegalArgumentException.class, () -> userService.createUser(requestDto, imageEmpty));
        assertEquals(e.getMessage(), "The passwords do not match."); //Expected message error from UserServiceImpl.

        verify(userRepository, times(1)).findByUsername(requestDto.getUsername());
    }

    @Test
    void testNegativeCreateUserExists(){

        when(userRepository.findByUsername(requestDto.getUsername())).thenReturn(Optional.of(user));

        Exception e = assertThrows(IllegalArgumentException.class, () -> userService.createUser(requestDto, imageEmpty));
        assertEquals(e.getMessage(), "The username: " + user.getUsername() + " already exists."); //Expected message error from UserServiceImpl.

        verify(userRepository, times(1)).findByUsername(requestDto.getUsername());
    }

    @Test
    void testPositiveUpdateUser(){

        when(userRepository.findByUsername(requestUpdated.getUsername())).thenReturn(null);

        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));

        when(userRepository.save(userUpdated)).thenReturn(userUpdated);

        when(mapper.toResponseDto(userUpdated)).thenReturn(responseUpdated);

        Optional<UserResponseDto> response = userService.updateUser(user.getId(), requestUpdated, null);

        assertNotNull(response);
        assertTrue(response.isPresent());
        assertEquals(response.get().getUsername(), userUpdated.getUsername());

        verify(userRepository, times(1)).findByUsername(requestUpdated.getUsername());
        verify(userRepository, times(1)).findById(user.getId());
        verify(userRepository, times(1)).save(userUpdated);
        verify(mapper, times(1)).toResponseDto(userUpdated);
    }

    @Test
    void testPositiveDeleteUserById(){

        when(userRepository.existsById(user.getId())).thenReturn(true);

        userService.deleteUserById(user.getId());

        verify(userRepository, times(1)).existsById(user.getId());
        verify(userRepository, times(1)).deleteById(user.getId());
    }
}
