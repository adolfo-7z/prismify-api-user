package com.ufro.dci.etransparency.etransparency_api_user.auth.infrastructure.security;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

class BcryptPasswordMatcherTest {

    private BcryptPasswordMatcher passwordMatcher;
    private BCryptPasswordEncoder encoder;

    @BeforeEach
    void setUp() {
        passwordMatcher = new BcryptPasswordMatcher();
        encoder = new BCryptPasswordEncoder();
    }

    @Test
    void shouldReturnTrueWhenPasswordMatches() {
        String rawPassword = "Ufro123";
        String hashedPassword = encoder.encode(rawPassword);
        boolean result = passwordMatcher.matches(rawPassword, hashedPassword);
        assertTrue(result, "Expected password matcher to return true for correct password");
    }

    @Test
    void shouldReturnFalseWhenPasswordDoesNotMatch() {
        String rawPassword = "Ufro123";
        String wrongPassword = "Sufro123";
        String hashedPassword = encoder.encode(rawPassword);
        boolean result = passwordMatcher.matches(wrongPassword, hashedPassword);
        assertFalse(result, "Expected password matcher to return false for incorrect password");
    }

    @Test
    void shouldNotMatchWhenHashedPasswordIsInvalid() {
        String rawPassword = "Ufro123";
        String invalidHash = "invalid_hash_string";
        boolean result = passwordMatcher.matches(rawPassword, invalidHash);
        assertFalse(result, "Expected password matcher to return false for invalid hash format");
    }

}
