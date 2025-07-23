package com.arobertosm.restauranting.user_service.dto;

import java.util.List;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CreateUserRequestDto {
    
    @NotBlank(message = "{error.first.name.blank}")
    @Size(min = 3, max = 45, message = "{error.first.name.length}")
    private String firstName;

    @NotBlank(message = "{error.last.name.blank}")
    @Size(min = 3, max = 45, message = "{error.last.name.length}")
    private String lastName;

    @NotBlank(message = "{error.username.blank}")
    @Size(min = 5, max = 15, message = "{error.username.length}")
    private String username;

    @NotBlank(message = "{error.mail.blank}")
    @Email(message = "{error.mail.invalid}")
    private String email;

    @NotBlank(message = "{error.password.blank}")
    @Size(min = 8, message = "{error.password.length}")
    private String password;

    @NotBlank(message = "{error.password2.blank}")
    private String password2;

    private String profilePictureUrl;
    private Boolean isAdmin;
    private Long restaurantId;
    private List<Long> ratingsId;
    private List<Long> bookingsId;
}

