package com.arobertosm.restauranting.user_service.controller;

import com.arobertosm.restauranting.user_service.dto.CreateUserRequestDto;
import com.arobertosm.restauranting.user_service.dto.UserResponseDto;
import com.arobertosm.restauranting.user_service.service.UserService;
import jakarta.validation.Valid;
import jakarta.ws.rs.core.MediaType;
import com.arobertosm.restauranting.common_service.IFileStorageService;
import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/users")
public class UserController {

    private UserService userService;
    private IFileStorageService fileStorageService;

    public UserController(UserService userService, IFileStorageService fileStorageService) {
        this.userService = userService;
        this.fileStorageService = fileStorageService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> getUserById(@PathVariable Long id) {
        return userService.getUserById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA})
    public ResponseEntity<UserResponseDto> createUser(@Valid @RequestPart("userData") CreateUserRequestDto userRequestDto, @RequestPart("profilePicture") MultipartFile profilePictureFile) {
        String imageUrl = fileStorageService.store(profilePictureFile, "profile-pictures", "user-" + UUID.randomUUID().toString());
        UserResponseDto savedUserDto = userService.createUser(userRequestDto, imageUrl);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedUserDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDto> updateUser(@PathVariable Long id, @Valid @RequestPart("userData") CreateUserRequestDto userUpdated, @RequestPart("profilePicture") MultipartFile profilePictureFile) {
        String imageUrl = fileStorageService.store(profilePictureFile, "profile-pictures", "user-" +  UUID.randomUUID().toString());
        return userService.updateUser(id, userUpdated, imageUrl)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUserById(@PathVariable Long id) {
        if (userService.getUserById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        userService.deleteUserById(id);
        return ResponseEntity.noContent().build();
    }
}