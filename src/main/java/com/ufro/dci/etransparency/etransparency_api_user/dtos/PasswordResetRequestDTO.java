package com.ufro.dci.etransparency.etransparency_api_user.dtos;

import com.ufro.dci.etransparency.etransparency_api_user.utils.annotations.password.ValidPassword;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PasswordResetRequestDTO {

    private String email;

    @NotBlank(message = "Password is required")
    @ValidPassword
    private String password;

    @NotBlank(message = "Validation password is required")
    private String validationPassword;

}
