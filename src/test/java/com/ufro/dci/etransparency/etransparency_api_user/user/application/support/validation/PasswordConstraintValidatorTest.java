package com.ufro.dci.etransparency.etransparency_api_user.user.application.support.validation;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import jakarta.validation.ConstraintValidatorContext;

class PasswordConstraintValidatorTest {

    private PasswordConstraintValidator validator;

    private ConstraintValidatorContext context;

    @BeforeEach
    void setUp() {
        validator = new PasswordConstraintValidator();
        context = Mockito.mock(ConstraintValidatorContext.class);
    }

    @Test
    void givenNullPassword_whenValidate_thenInvalid() {
        assertFalse(validator.isValid(null, context));
    }

    @Test
    void givenPasswordWithoutUppercase_whenValidate_thenInvalid() {
        assertFalse(validator.isValid("abcd123!", context));
    }

    @Test
    void givenPasswordWithoutLowercase_whenValidate_thenInvalid() {
        assertFalse(validator.isValid("ABCD123!", context));
    }

    @Test
    void givenPasswordWithoutDigit_whenValidate_thenInvalid() {
        assertFalse(validator.isValid("Abcdefg!", context));
    }

    @Test
    void givenPasswordWithoutSpecialChar_whenValidate_thenInvalid() {
        assertFalse(validator.isValid("Abcd1234", context));
    }

    @Test
    void givenPasswordWithWhitespace_whenValidate_thenInvalid() {
        assertFalse(validator.isValid("Abcd 123!", context));
    }

    @Test
    void givenPasswordTooShort_whenValidate_thenInvalid() {
        assertFalse(validator.isValid("Ab1!", context));
    }

    @Test
    void givenValidPassword_whenValidate_thenValid() {
        assertTrue(validator.isValid("Aa1!abcd", context));
    }

    @Test
    void givenValidPasswordWithMultipleSpecialChars_whenValidate_thenValid() {
        assertTrue(validator.isValid("Abcdef1!!", context));
    }

}
