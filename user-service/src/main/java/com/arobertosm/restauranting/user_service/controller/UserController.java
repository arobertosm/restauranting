package com.arobertosm.restauranting.user_service.controller;

import com.arobertosm.restauranting.user_service.model.User;
import com.arobertosm.restauranting.user_service.repository.UserRepository;
import com.arobertosm.restauranting.common_service.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FileStorageService fileStorageService;

    @GetMapping("/{id}")
    public User getUserById(@PathVariable Long id) {
        return userRepository.findById(id).orElseThrow(() -> new RuntimeException("No se ha encontrado al usuario con id: " + id));
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User savedUser = userRepository.save(user);
        return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
    }

    @PostMapping("/{id}/profile-picture")
    public ResponseEntity<?> uploadProfilePicture(@PathVariable Long id, @RequestParam("file") MultipartFile file){
        try{
            User userFound = getUserById(id);
            String relativePath = fileStorageService.store(file, "profile-pictures", String.valueOf(id));
            String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/uploads/").path(relativePath).toUriString();

            userFound.setProfilePictureUrl(fileDownloadUri);
            userRepository.save(userFound);

            return ResponseEntity.ok("Foto de perfil actualizada correctamente para el usuario con id " + id);
        } catch (Exception e) {
            throw new RuntimeException("No se ha encontrado al usuario con id: " + id);
        }
    }
}