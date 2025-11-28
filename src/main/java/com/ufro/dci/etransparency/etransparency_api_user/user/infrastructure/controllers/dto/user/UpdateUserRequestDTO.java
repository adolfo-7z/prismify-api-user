package com.ufro.dci.etransparency.etransparency_api_user.user.infrastructure.controllers.dto.user;

import jakarta.validation.constraints.*;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UpdateUserRequestDTO {

    @Size(max = 50, message = "Username cannot exceed 50 characters")
    private String username;

    @Email(message = "Invalid email format")
    private String email;

    @Size(min = 8, message = "Password must be at least 8 characters long")
    private String password;

    @Size(max = 15, message = "Phone Number cannot exceed 15 characters")
    private String phoneNumber;

    @Size(max = 100, message = "Position cannot exceed 100 characters")
    private String position;

    @Size(max = 12, message = "Rut cannot exceed 12 characters")
    private String rut;

    @Size(max = 30, message = "City cannot exceed 30 characters")
    private String city;

    @Size(max = 20, message = "Color cannot exceed 20 characters")
    private String color;

    @Size(max = 10, message = "Acronym cannot exceed 10 characters")
    private String acronym;

    private Long auditsPerformed;
    
}
