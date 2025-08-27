package com.ufro.dci.etransparency.etransparency_api_user.user.infrastructure.controllers.dto;

import com.ufro.dci.etransparency.etransparency_api_user.user.application.support.validation.annotation.ValidRut;
import com.ufro.dci.etransparency.etransparency_api_user.user.domain.models.Role;
import com.ufro.dci.etransparency.etransparency_api_user.user.domain.models.User;

import jakarta.validation.constraints.*;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CreateUserRequestDTO {

    @NotNull(message = "Username cannot be null")
    @NotBlank(message = "Username cannot be blank")
    private String username;

    @NotNull(message = "Email cannot be null")
    @NotBlank(message = "Email cannot be blank")
    @Email(message = "Invalid email format")
    private String email;

    @NotNull(message = "Password cannot be null")
    @NotBlank(message = "Password cannot be blank")
    @Size(min = 8, message = "Password must be at least 8 characters long")
    private String password;

    @NotNull(message = "Phone Number cannot be null")
    @NotBlank(message = "Phone Number cannot be blank")
    @Size(max = 15, message = "Phone Number cannot exceed 15 characters")
    private String phoneNumber;

    @Size(max = 100, message = "Position cannot exceed 100 characters")
    private String position;

    @Size(max = 12, message = "RUT cannot exceed 12 characters")
    @ValidRut
    private String rut;

    @Size(max = 30, message = "City cannot exceed 30 characters")
    private String city;

    @Size(max = 20, message = "Color cannot exceed 20 characters")
    private String color;

    @Size(max = 3, message = "Acronym cannot exceed 3 characters")
    private String acronym;

    private Role role;

    public User toDomain() {
        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(password);
        user.setPhoneNumber(phoneNumber);
        user.setPosition(position);
        user.setRole(role);
        user.setRut(rut);
        user.setCity(city);
        user.setColor(color);
        user.setAcronym(acronym);
        return user;
    }

}
