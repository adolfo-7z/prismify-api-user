package com.ufro.dci.etransparency.etransparency_api_user.dtos;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.ufro.dci.etransparency.etransparency_api_user.models.UserEntity.UserRole;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

import java.util.Arrays;
import java.util.List;

class UserDTOTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void testGettersAndSetters() {
        UserDTO dto = new UserDTO();

        // Configura valores para cada propiedad
        dto.setId(1L);
        dto.setActive(true);
        dto.setUsername("username123");
        dto.setEmail("user@example.com");
        dto.setPassword("password123");
        dto.setPhoneNumber("123456789");
        dto.setRole(UserRole.MANAGER);
        List<String> notifications = Arrays.asList("Notification1", "Notification2");
        dto.setNotifications(notifications);

        // Verifica cada getter
        assertEquals(1L, dto.getId());
        assertTrue(dto.isActive());
        assertEquals("username123", dto.getUsername());
        assertEquals("user@example.com", dto.getEmail());
        assertEquals("password123", dto.getPassword());
        assertEquals("123456789", dto.getPhoneNumber());
        assertEquals(UserRole.MANAGER, dto.getRole());
        assertEquals(notifications, dto.getNotifications());
    }

    @Test
    void testAllArgsConstructor() {
        List<String> notifications = Arrays.asList("Notification1", "Notification2");
        UserDTO dto = new UserDTO(1L, true, "username123", "user@example.com", "password123",
                "123456789", UserRole.MANAGER, notifications);

        // Verifica que el constructor asigne los valores correctamente
        assertEquals(1L, dto.getId());
        assertTrue(dto.isActive());
        assertEquals("username123", dto.getUsername());
        assertEquals("user@example.com", dto.getEmail());
        assertEquals("password123", dto.getPassword());
        assertEquals("123456789", dto.getPhoneNumber());
        assertEquals(UserRole.MANAGER, dto.getRole());
        assertEquals(notifications, dto.getNotifications());
    }

    @Test
    void testEqualsAndHashCode() {
        List<String> notifications = Arrays.asList("Notification1", "Notification2");

        UserDTO dto1 = new UserDTO(1L, true, "username123", "user@example.com", "password123",
                "123456789", UserRole.MANAGER, notifications);

        UserDTO dto2 = new UserDTO(1L, true, "username123", "user@example.com", "password123",
                "123456789", UserRole.MANAGER, notifications);

        UserDTO dto3 = new UserDTO(2L, false, "anotheruser", "another@example.com", "anotherpass",
                "987654321", UserRole.ADMIN, Arrays.asList("Notification3"));

        // Verifica que equals y hashCode funcionen correctamente para objetos iguales y diferentes
        assertEquals(dto1, dto2);
        assertNotEquals(dto1, dto3);
        assertEquals(dto1.hashCode(), dto2.hashCode());
        assertNotEquals(dto1.hashCode(), dto3.hashCode());
    }

    @Test
    void testToString() {
        List<String> notifications = Arrays.asList("Notification1", "Notification2");
        UserDTO dto = new UserDTO(1L, true, "username123", "user@example.com", "password123",
                "123456789", UserRole.MANAGER, notifications);

        // Verifica que toString no retorne null y contenga valores esperados
        String toString = dto.toString();
        assertNotNull(toString);
        assertTrue(toString.contains("username123"));
        assertTrue(toString.contains("user@example.com"));
    }


    @Test
    void testEqualsWithSelf() {
        UserDTO dto = new UserDTO(1L, true, "username123", "user@example.com", "password123",
                "123456789", UserRole.MANAGER, Arrays.asList("Notification1"));
        assertEquals(dto, dto);
    }

    @Test
    void testEqualsWithDifferentClass() {
        UserDTO dto = new UserDTO(1L, true, "username123", "user@example.com", "password123",
                "123456789", UserRole.MANAGER, Arrays.asList("Notification1"));
        String differentClass = "I am a string";
        assertNotEquals(dto, differentClass);
    }

    @Test
    void testSettersWithNullValues() {
        UserDTO dto = new UserDTO();
        dto.setUsername(null);
        dto.setEmail(null);
        dto.setPassword(null);
        dto.setPhoneNumber(null);
        dto.setNotifications(null);

        assertNull(dto.getUsername());
        assertNull(dto.getEmail());
        assertNull(dto.getPassword());
        assertNull(dto.getPhoneNumber());
        assertNull(dto.getNotifications());
    }
}
