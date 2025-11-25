package com.ufro.dci.etransparency.etransparency_api_user.user.application.support.validation;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.ufro.dci.etransparency.etransparency_api_user.user.domain.ports.out.UserRepository;

import jakarta.validation.ConstraintValidatorContext;

class UniqueEmailValidatorTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private ConstraintValidatorContext context;

    private UniqueEmailValidator validator;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        validator = new UniqueEmailValidator(userRepository);
    }

    @Test
    void isValid_ShouldReturnTrue_WhenEmailDoesNotExist() {
        String email = "correo@empresa.cl";
        when(userRepository.existsByEmail(email)).thenReturn(false);
        boolean result = validator.isValid(email, context);
        assertThat(result).isTrue();
        verify(userRepository).existsByEmail(email);
    }

    @Test
    void isValid_ShouldReturnFalse_WhenEmailExists() {
        String email = "correo@empresa.cl";
        when(userRepository.existsByEmail(email)).thenReturn(true);
        boolean result = validator.isValid(email, context);
        assertThat(result).isFalse();
        verify(userRepository).existsByEmail(email);
    }

    @Test
    void isValid_ShouldReturnTrue_WhenEmailIsNull() {
        when(userRepository.existsByEmail(null)).thenReturn(false);
        boolean result = validator.isValid(null, context);
        assertThat(result).isTrue();
        verify(userRepository).existsByEmail(null);
    }

}
