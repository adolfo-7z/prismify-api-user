package com.ufro.dci.etransparency.etransparency_api_user.user.infrastructure.security;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

class BcryptPasswordHasherTest {

    private BcryptPasswordHasher hasher;
    private BCryptPasswordEncoder encoder;

    @BeforeEach
    void setUp() {
        hasher = new BcryptPasswordHasher();
        encoder = new BCryptPasswordEncoder();
    }

    @Test
    void shouldHashPassword() {
        String rawPassword = "Password123!";
        String hashedPassword = hasher.hash(rawPassword);
        assertNotNull(hashedPassword, "Hashed password should not be null");
        assertNotEquals(rawPassword, hashedPassword, "Hashed password should not equal the raw password");
    }

    @Test
    void shouldValidatePasswordWithBCrypt() {
        String rawPassword = "Password456!";
        String hashedPassword = hasher.hash(rawPassword);
        assertTrue(encoder.matches(rawPassword, hashedPassword),
                "BCrypt should validate the hashed password against the raw password");
    }

    @Test
    void hashingSamePasswordProducesDifferentHashes() {
        String rawPassword = "Password789!";
        String hash1 = hasher.hash(rawPassword);
        String hash2 = hasher.hash(rawPassword);
        assertNotEquals(hash1, hash2, "Hashing the same password twice should yield different hashes due to salting");
        assertTrue(encoder.matches(rawPassword, hash1), "First hash should match raw password");
        assertTrue(encoder.matches(rawPassword, hash2), "Second hash should match raw password");
    }

}
