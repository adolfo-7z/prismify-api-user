package com.ufro.dci.etransparency.etransparency_api_user.dtos;

import com.fasterxml.jackson.annotation.*;
import com.ufro.dci.etransparency.etransparency_api_user.models.UserEntity.UserRole;
import com.ufro.dci.etransparency.etransparency_api_user.utils.annotations.email.UniqueEmail;
import com.ufro.dci.etransparency.etransparency_api_user.utils.annotations.password.ValidPassword;
import com.ufro.dci.etransparency.etransparency_api_user.utils.annotations.username.UniqueUsername;

import jakarta.persistence.MappedSuperclass;
import jakarta.validation.constraints.*;
import lombok.*;

@MappedSuperclass
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    private Long id;

    @JsonIgnore
    private boolean isActive = true;

    @NotNull(message = "Username cannot be null")
    @NotBlank(message = "Username cannot be blank")
    @UniqueUsername
    private String username;

    @NotNull(message = "Email cannot be null")
    @NotBlank(message = "Email cannot be blank")
    @Email(message = "Invalid email format")
    @UniqueEmail
    private String email;

    @NotNull(message = "Password cannot be null")
    @NotBlank(message = "Password cannot be blank")
    @Size(min = 8, message = "Password must be at least 8 characters long")
    @ValidPassword
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @NotNull(message = "Phone Number cannot be null")
    @NotBlank(message = "Phone Number cannot be blank")
    @Size(max = 15, message = "Phone Number cannot exceed 15 characters")
    private String phoneNumber;

    @JsonIgnore
    private UserRole role;
}
