package com.ufro.dci.etransparency.etransparency_api_user.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ufro.dci.etransparency.etransparency_api_user.utils.annotations.email.UniqueEmail;
import com.ufro.dci.etransparency.etransparency_api_user.utils.annotations.username.UniqueUsername;

import jakarta.validation.constraints.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserUpdateDTO {

    @UniqueUsername
    private String username;

    @Email(message = "Invalid email format")
    @UniqueEmail
    private String email;

    @Size(min = 8, message = "Password must be at least 8 characters long")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @Size(max = 15, message = "Phone Number cannot exceed 15 characters")
    private String phoneNumber;
}
