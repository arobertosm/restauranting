package com.arobertosm.restauranting.user_service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.arobertosm.restauranting.user_service.dto.CreateUserRequestDto;
import com.arobertosm.restauranting.user_service.dto.UserResponseDto;
import com.arobertosm.restauranting.user_service.mapper.UserMapper;
import com.arobertosm.restauranting.user_service.model.User;

public class UserMapperTest {
    
    private UserMapper mapper = new UserMapper();
    private User user;
    private CreateUserRequestDto userRequestDto;

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
        userRequestDto = new CreateUserRequestDto();
        userRequestDto.setFirstName("Junit5 Test");
        userRequestDto.setLastName("SpringBoot Java");
        userRequestDto.setUsername("junit5TestSpringBoot");
        userRequestDto.setEmail("5junit@mail.com");
        userRequestDto.setPassword("JU5TSBJ!");
        userRequestDto.setPassword2("JU5TSBJ!");
    }

    @Test
    void testPositiveMapDtoToEntity(){
        User userAux = mapper.toEntity(userRequestDto);

        assertEquals(userAux.getFirstName(), userRequestDto.getFirstName());
        assertEquals(userAux.getLastName(), userRequestDto.getLastName());
        assertEquals(userAux.getUsername(), userRequestDto.getUsername());
        assertEquals(userAux.getEmail(), userRequestDto.getEmail());
        assertEquals(userAux.getPassword(), userRequestDto.getPassword());
        assertEquals(userAux.getPassword(), userRequestDto.getPassword2());
    }

    @Test
    void testPositiveMapEntityToResponseDto(){
        UserResponseDto responseDto = mapper.toResponseDto(user);

        assertEquals(responseDto.getId(), user.getId());
        assertEquals(responseDto.getFirstName(), user.getFirstName());
        assertEquals(responseDto.getLastName(), user.getLastName());
        assertEquals(responseDto.getUsername(), user.getUsername());
        assertEquals(responseDto.getEmail(), user.getEmail());
        assertEquals(responseDto.getPassword(), user.getPassword());
    }

    @Test
    void testPositiveMapListEntityToListResponseDto(){
        List<User> listUser = new ArrayList<User>();
        listUser.add(user);

        List<UserResponseDto> listResponseDto = mapper.toResponseDtoList(listUser);
        assertEquals(listResponseDto.size(), 1); //There should only be one user in the list.
        int counter = 0;
        for (UserResponseDto u : listResponseDto){
            for (User us : listUser){
                assertEquals(u.getId(), us.getId());
                assertEquals(u.getFirstName(), us.getFirstName());
                assertEquals(u.getLastName(), us.getLastName());
                assertEquals(u.getUsername(), us.getUsername());
                assertEquals(u.getEmail(), us.getEmail());
                assertEquals(u.getPassword(), us.getPassword());
                counter++;
            }
        }
        assertEquals(counter, 1); //The fors section should only be executed once since there is only one user.
    }
}
