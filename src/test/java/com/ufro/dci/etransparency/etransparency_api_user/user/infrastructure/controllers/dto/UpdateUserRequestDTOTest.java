package com.ufro.dci.etransparency.etransparency_api_user.user.infrastructure.controllers.dto;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Set;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import jakarta.validation.*;

class UpdateUserRequestDTOTest {

    private static Validator validator;

    @BeforeAll
    static void setupValidator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void testValidDTO_NoViolations() {
        UpdateUserRequestDTO dto = new UpdateUserRequestDTO();
        dto.setUsername("juancito");
        dto.setEmail("juan@correo.cl");
        dto.setPassword("verysecurepassword");
        dto.setPhoneNumber("123456789012345");
        dto.setPosition("Director");
        dto.setRut("88888888-8");
        dto.setCity("Temuco");
        dto.setColor("Red");
        dto.setAcronym("TP");
        dto.setAuditsPerformed(5L);

        Set<ConstraintViolation<UpdateUserRequestDTO>> violations = validator.validate(dto);
        assertTrue(violations.isEmpty(), "Expected no validation violations");
    }

    @Test
    void testUsernameExceedsMaxSize_Violation() {
        UpdateUserRequestDTO dto = new UpdateUserRequestDTO();
        dto.setUsername("thisusernameisdefinitelywaytoolongbeyond50characters");
        Set<ConstraintViolation<UpdateUserRequestDTO>> violations = validator.validate(dto);
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("username")));
    }

    @Test
    void testInvalidEmail_Violation() {
        UpdateUserRequestDTO dto = new UpdateUserRequestDTO();
        dto.setEmail("not-an-email");

        Set<ConstraintViolation<UpdateUserRequestDTO>> violations = validator.validate(dto);
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("email")));
    }

    @Test
    void testPasswordTooShort_Violation() {
        UpdateUserRequestDTO dto = new UpdateUserRequestDTO();
        dto.setPassword("short");
        Set<ConstraintViolation<UpdateUserRequestDTO>> violations = validator.validate(dto);
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("password")));
    }

    @Test
    void testPhoneNumberExceedsMaxSize_Violation() {
        UpdateUserRequestDTO dto = new UpdateUserRequestDTO();
        dto.setPhoneNumber("1234567890123456");
        Set<ConstraintViolation<UpdateUserRequestDTO>> violations = validator.validate(dto);
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("phoneNumber")));
    }

    @Test
    void testPositionExceedsMaxSize_Violation() {
        UpdateUserRequestDTO dto = new UpdateUserRequestDTO();
        dto.setPosition(
                "This position name is intentionally crafted to be exceedingly long, extending well beyond one hundred characters in total length, invoking the Omnissiah’s will to reject it.");
        Set<ConstraintViolation<UpdateUserRequestDTO>> violations = validator.validate(dto);
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("position")));
    }

    @Test
    void testRutExceedsMaxSize_Violation() {
        UpdateUserRequestDTO dto = new UpdateUserRequestDTO();
        dto.setRut("1234567890123456");

        Set<ConstraintViolation<UpdateUserRequestDTO>> violations = validator.validate(dto);
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("rut")));
    }

    @Test
    void testCityExceedsMaxSize_Violation() {
        UpdateUserRequestDTO dto = new UpdateUserRequestDTO();
        dto.setCity("This city name is way too long beyond thirty characters");

        Set<ConstraintViolation<UpdateUserRequestDTO>> violations = validator.validate(dto);
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("city")));
    }

    @Test
    void testColorExceedsMaxSize_Violation() {
        UpdateUserRequestDTO dto = new UpdateUserRequestDTO();
        dto.setColor("ThisColorIsWayTooLongBeyondTwentyChars");

        Set<ConstraintViolation<UpdateUserRequestDTO>> violations = validator.validate(dto);
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("color")));
    }

    @Test
    void testAcronymExceedsMaxSize_Violation() {
        UpdateUserRequestDTO dto = new UpdateUserRequestDTO();
        dto.setAcronym("ThisAcronymTooLong");

        Set<ConstraintViolation<UpdateUserRequestDTO>> violations = validator.validate(dto);
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("acronym")));
    }

}
