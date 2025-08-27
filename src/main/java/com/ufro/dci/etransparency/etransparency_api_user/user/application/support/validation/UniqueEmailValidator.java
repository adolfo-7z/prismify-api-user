package com.ufro.dci.etransparency.etransparency_api_user.user.application.support.validation;

import com.ufro.dci.etransparency.etransparency_api_user.user.application.support.validation.annotation.UniqueEmail;
import com.ufro.dci.etransparency.etransparency_api_user.user.domain.ports.out.UserRepository;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class UniqueEmailValidator implements ConstraintValidator<UniqueEmail, String> {

    private final UserRepository userRepository;

    public UniqueEmailValidator(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void initialize(UniqueEmail constraintAnnotation) {
        // Este método se deja intencionalmente vacío porque la lógica de inicialización
        // no es necesaria para este validador.
    }

    @Override
    public boolean isValid(String email, ConstraintValidatorContext context) {
        return !userRepository.existsByEmail(email);
    }

}
