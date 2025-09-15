package com.ufro.dci.etransparency.etransparency_api_user.user.application.support.validation;

import com.ufro.dci.etransparency.etransparency_api_user.user.application.support.validation.annotation.UniqueUsername;
import com.ufro.dci.etransparency.etransparency_api_user.user.domain.ports.out.UserRepository;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;

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
