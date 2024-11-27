package com.ufro.dci.etransparency.etransparency_api_user.models;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class UserEntityTest {

    @Test
    void testUserEntityConstructor() {
        UserEntity user = new UserEntity();

        assertFalse(user.isActive());
        assertNull(user.getUsername());
        assertNull(user.getEmail());
        assertNull(user.getPassword());
        assertNull(user.getPhoneNumber());
        assertNull(user.getRole());
    }

    @Test
    void testSettersAndGetters() {
        UserEntity user = new UserEntity();

        user.setActive(true);
        user.setUsername("testuser");
        user.setEmail("testuser@example.com");
        user.setPassword("securepassword123");
        user.setPhoneNumber("1234567890");
        user.setRole(UserEntity.UserRole.MANAGER);

        assertTrue(user.isActive());
        assertEquals("testuser", user.getUsername());
        assertEquals("testuser@example.com", user.getEmail());
        assertEquals("securepassword123", user.getPassword());
        assertEquals("1234567890", user.getPhoneNumber());
        assertEquals(UserEntity.UserRole.MANAGER, user.getRole());
    }

    @Test
    void testUserRoleEnum() {
        assertEquals("ADMIN", UserEntity.UserRole.ADMIN.name());
        assertEquals("MANAGER", UserEntity.UserRole.MANAGER.name());
        assertEquals("AUDITOR", UserEntity.UserRole.AUDITOR.name());
    }

    @Test
    void testToString() {
        UserEntity user = new UserEntity();
        user.setActive(true);
        user.setUsername("testuser");
        user.setEmail("testuser@example.com");
        user.setPassword("securepassword123");
        user.setPhoneNumber("1234567890");
        user.setRole(UserEntity.UserRole.MANAGER);

        String toStringOutput = user.toString();

        assertTrue(toStringOutput.contains("isActive=true"));
        assertTrue(toStringOutput.contains("username=testuser"));
        assertTrue(toStringOutput.contains("email=testuser@example.com"));
        assertTrue(toStringOutput.contains("password=securepassword123"));
        assertTrue(toStringOutput.contains("phoneNumber=1234567890"));
        assertTrue(toStringOutput.contains("role=MANAGER"));
    }
}
