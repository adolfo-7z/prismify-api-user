package com.ufro.dci.etransparency.etransparency_api_user.dtos;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;


class UserUpdateDTOTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void testGettersAndSetters() {
        UserUpdateDTO dto = new UserUpdateDTO();

        // Configura valores para cada propiedad
        dto.setUsername("newUsername");
        dto.setEmail("newuser@example.com");
        dto.setPassword("newpassword");
        dto.setPhoneNumber("123456789");

        // Verifica cada getter
        assertEquals("newUsername", dto.getUsername());
        assertEquals("newuser@example.com", dto.getEmail());
        assertEquals("newpassword", dto.getPassword());
        assertEquals("123456789", dto.getPhoneNumber());
    }

    @Test
    void testAllArgsConstructor() {
        UserUpdateDTO dto = new UserUpdateDTO("newUsername", "newuser@example.com", "newpassword", "123456789");

        // Verifica que el constructor asigne los valores correctamente
        assertEquals("newUsername", dto.getUsername());
        assertEquals("newuser@example.com", dto.getEmail());
        assertEquals("newpassword", dto.getPassword());
        assertEquals("123456789", dto.getPhoneNumber());
    }

    @Test
    void testEqualsAndHashCode() {
        UserUpdateDTO dto1 = new UserUpdateDTO("newUsername", "newuser@example.com", "newpassword", "123456789");
        UserUpdateDTO dto2 = new UserUpdateDTO("newUsername", "newuser@example.com", "newpassword", "123456789");
        UserUpdateDTO dto3 = new UserUpdateDTO("anotherUsername", "anotheruser@example.com", "anotherpassword", "987654321");

        // Verifica que equals y hashCode funcionen correctamente para objetos iguales y diferentes
        assertEquals(dto1, dto2);
        assertNotEquals(dto1, dto3);
        assertEquals(dto1.hashCode(), dto2.hashCode());
        assertNotEquals(dto1.hashCode(), dto3.hashCode());
    }

    @Test
    void testToString() {
        UserUpdateDTO dto = new UserUpdateDTO("newUsername", "newuser@example.com", "newpassword", "123456789");

        // Verifica que toString no retorne null y contenga valores esperados
        String toString = dto.toString();
        assertNotNull(toString);
        assertTrue(toString.contains("newUsername"));
        assertTrue(toString.contains("newuser@example.com"));
    }

    @Test
    void testEqualsWithSelf() {
        UserUpdateDTO dto = new UserUpdateDTO("newUsername", "newuser@example.com", "newpassword", "123456789");
        assertEquals(dto, dto);
    }

    @Test
    void testEqualsWithDifferentClass() {
        UserUpdateDTO dto = new UserUpdateDTO("newUsername", "newuser@example.com", "newpassword", "123456789");
        String differentClass = "I am a string";
        assertNotEquals(dto, differentClass);
    }

    @Test
    void testSettersWithNullValues() {
        UserUpdateDTO dto = new UserUpdateDTO();
        dto.setUsername(null);
        dto.setEmail(null);
        dto.setPassword(null);
        dto.setPhoneNumber(null);

        assertNull(dto.getUsername());
        assertNull(dto.getEmail());
        assertNull(dto.getPassword());
        assertNull(dto.getPhoneNumber());
    }
}

