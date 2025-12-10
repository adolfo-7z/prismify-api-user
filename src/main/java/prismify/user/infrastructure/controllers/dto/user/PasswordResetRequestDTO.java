package prismify.user.infrastructure.controllers.dto.user;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import prismify.user.application.support.validation.annotation.ValidPassword;

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
