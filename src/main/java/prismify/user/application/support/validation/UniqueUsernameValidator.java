package prismify.user.application.support.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import prismify.user.application.support.validation.annotation.UniqueUsername;
import prismify.user.domain.ports.out.UserRepository;

@RequiredArgsConstructor
public class UniqueUsernameValidator implements ConstraintValidator<UniqueUsername, String> {

    private final UserRepository userRepository;

    @Override
    public void initialize(UniqueUsername constraintAnnotation) {
        // Inicializa validador
    }

    @Override
    public boolean isValid(String username, ConstraintValidatorContext context) {
        return !userRepository.existsByUsername(username);
    }
}
