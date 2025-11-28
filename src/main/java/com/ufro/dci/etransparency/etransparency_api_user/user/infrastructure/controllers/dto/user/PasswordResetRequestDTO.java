package com.ufro.dci.etransparency.etransparency_api_user.user.infrastructure.controllers.dto.user;

import com.ufro.dci.etransparency.etransparency_api_user.user.application.support.validation.annotation.ValidPassword;

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
