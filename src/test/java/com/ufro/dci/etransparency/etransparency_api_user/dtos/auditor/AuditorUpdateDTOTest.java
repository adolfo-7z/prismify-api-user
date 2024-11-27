package com.ufro.dci.etransparency.etransparency_api_user.dtos.auditor;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

class AuditorUpdateDTOTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void testGettersAndSetters() {
        AuditorUpdateDTO auditorUpdateDTO = new AuditorUpdateDTO();

        // Configura valores para cada propiedad heredada de UserUpdateDTO y nuevas propiedades de AuditorUpdateDTO
        auditorUpdateDTO.setUsername("auditorUsername");
        auditorUpdateDTO.setEmail("auditor@example.com");
        auditorUpdateDTO.setPassword("securePassword");
        auditorUpdateDTO.setPhoneNumber("123456789");
        auditorUpdateDTO.setCity("Sample City");
        auditorUpdateDTO.setNAssignedInstitutions(5L);
        auditorUpdateDTO.setColor("Blue");
        auditorUpdateDTO.setAcronym("AUD");

        // Verifica cada getter
        assertEquals("auditorUsername", auditorUpdateDTO.getUsername());
        assertEquals("auditor@example.com", auditorUpdateDTO.getEmail());
        assertEquals("securePassword", auditorUpdateDTO.getPassword());
        assertEquals("123456789", auditorUpdateDTO.getPhoneNumber());
        assertEquals("Sample City", auditorUpdateDTO.getCity());
        assertEquals(5L, auditorUpdateDTO.getNAssignedInstitutions());
        assertEquals("Blue", auditorUpdateDTO.getColor());
        assertEquals("AUD", auditorUpdateDTO.getAcronym());
    }

    @Test
    void testAllArgsConstructor() {
        AuditorUpdateDTO auditorUpdateDTO = new AuditorUpdateDTO("Sample City", 5L, "Blue", "AUD");

        // Verifica que el constructor asigne los valores correctamente
        assertEquals("Sample City", auditorUpdateDTO.getCity());
        assertEquals(5L, auditorUpdateDTO.getNAssignedInstitutions());
        assertEquals("Blue", auditorUpdateDTO.getColor());
        assertEquals("AUD", auditorUpdateDTO.getAcronym());
    }

    @Test
    void testEqualsAndHashCode() {
        AuditorUpdateDTO auditor1 = new AuditorUpdateDTO("Sample City", 5L, "Blue", "AUD");

        AuditorUpdateDTO auditor2 = new AuditorUpdateDTO("Sample City", 5L, "Blue", "AUD");

        AuditorUpdateDTO auditor3 = new AuditorUpdateDTO("Another City", 3L, "Red", "ADM");

        // Verifica que equals y hashCode funcionen correctamente para objetos iguales y diferentes
        assertEquals(auditor1, auditor2);
        assertNotEquals(auditor1, auditor3);
        assertEquals(auditor1.hashCode(), auditor2.hashCode());
        assertNotEquals(auditor1.hashCode(), auditor3.hashCode());
    }

    @Test
    void testToString() {
        AuditorUpdateDTO auditorUpdateDTO = new AuditorUpdateDTO("Sample City", 5L, "Blue", "AUD");

        // Verifica que toString no retorne null y contenga valores esperados
        String toString = auditorUpdateDTO.toString();
        assertNotNull(toString);
        assertTrue(toString.contains("Sample City"));
        assertTrue(toString.contains("Blue"));
    }

    @Test
    void testEqualsWithSelf() {
        AuditorUpdateDTO auditorUpdateDTO = new AuditorUpdateDTO("Sample City", 5L, "Blue", "AUD");
        assertEquals(auditorUpdateDTO, auditorUpdateDTO);
    }

    @Test
    void testEqualsWithDifferentClass() {
        AuditorUpdateDTO auditorUpdateDTO = new AuditorUpdateDTO("Sample City", 5L, "Blue", "AUD");
        String differentClass = "I am a string";
        assertNotEquals(auditorUpdateDTO, differentClass);
    }

    @Test
    void testSettersWithNullValues() {
        AuditorUpdateDTO auditorUpdateDTO = new AuditorUpdateDTO();
        auditorUpdateDTO.setCity(null);
        auditorUpdateDTO.setColor(null);
        auditorUpdateDTO.setAcronym(null);

        assertNull(auditorUpdateDTO.getCity());
        assertNull(auditorUpdateDTO.getColor());
        assertNull(auditorUpdateDTO.getAcronym());
    }
}

