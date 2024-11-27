package com.ufro.dci.etransparency.etransparency_api_user.dtos.auditor;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

import java.util.Date;

class AuditorDTOTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void testGettersAndSetters() {
        AuditorDTO auditor = new AuditorDTO();

        // Configura valores para cada propiedad heredada de UserDTO y nuevas propiedades de AuditorDTO
        auditor.setId(1L);
        auditor.setUsername("auditorUsername");
        auditor.setEmail("auditor@example.com");
        auditor.setPassword("securePassword");
        auditor.setPhoneNumber("123456789");
        auditor.setCity("Sample City");
        auditor.setNAssignedInstitutions(5L);
        auditor.setNAuditsPerformed(10L);
        Date now = new Date();
        auditor.setCreatedAt(now);
        auditor.setUpdatedAt(now);
        auditor.setColor("Blue");
        auditor.setAcronym("AUD");

        // Verifica cada getter
        assertEquals(1L, auditor.getId());
        assertEquals("auditorUsername", auditor.getUsername());
        assertEquals("auditor@example.com", auditor.getEmail());
        assertEquals("securePassword", auditor.getPassword());
        assertEquals("123456789", auditor.getPhoneNumber());
        assertEquals("Sample City", auditor.getCity());
        assertEquals(5L, auditor.getNAssignedInstitutions());
        assertEquals(10L, auditor.getNAuditsPerformed());
        assertEquals(now, auditor.getCreatedAt());
        assertEquals(now, auditor.getUpdatedAt());
        assertEquals("Blue", auditor.getColor());
        assertEquals("AUD", auditor.getAcronym());
    }

    @Test
    void testEqualsAndHashCode() {
        Date now = new Date();
        AuditorDTO auditor1 = new AuditorDTO();
        auditor1.setCity("Sample City");
        auditor1.setNAssignedInstitutions(5L);
        auditor1.setNAuditsPerformed(10L);
        auditor1.setCreatedAt(now);
        auditor1.setUpdatedAt(now);
        auditor1.setColor("Blue");
        auditor1.setAcronym("AUD");

        AuditorDTO auditor2 = new AuditorDTO();
        auditor2.setCity("Sample City");
        auditor2.setNAssignedInstitutions(5L);
        auditor2.setNAuditsPerformed(10L);
        auditor2.setCreatedAt(now);
        auditor2.setUpdatedAt(now);
        auditor2.setColor("Blue");
        auditor2.setAcronym("AUD");

        AuditorDTO auditor3 = new AuditorDTO();
        auditor3.setCity("Another City");
        auditor3.setColor("Red");

        // Verifica que equals y hashCode funcionen correctamente para objetos iguales y diferentes
        assertEquals(auditor1, auditor2);
        assertNotEquals(auditor1, auditor3);
        assertEquals(auditor1.hashCode(), auditor2.hashCode());
        assertNotEquals(auditor1.hashCode(), auditor3.hashCode());
    }

    @Test
    void testToString() {
        Date now = new Date();
        AuditorDTO auditor = new AuditorDTO();
        auditor.setCity("Sample City");
        auditor.setNAssignedInstitutions(5L);
        auditor.setNAuditsPerformed(10L);
        auditor.setCreatedAt(now);
        auditor.setUpdatedAt(now);
        auditor.setColor("Blue");
        auditor.setAcronym("AUD");

        // Verifica que toString no retorne null y contenga valores esperados
        String toString = auditor.toString();
        assertNotNull(toString);
        assertTrue(toString.contains("Sample City"));
        assertTrue(toString.contains("Blue"));
    }

    @Test
    void testEqualsWithSelf() {
        AuditorDTO auditor = new AuditorDTO();
        auditor.setUsername("auditorUsername");
        auditor.setEmail("auditor@example.com");
        assertEquals(auditor, auditor);
    }

    @Test
    void testEqualsWithDifferentClass() {
        AuditorDTO auditor = new AuditorDTO();
        auditor.setUsername("auditorUsername");
        auditor.setEmail("auditor@example.com");
        String differentClass = "I am a string";
        assertNotEquals(auditor, differentClass);
    }

    @Test
    void testSettersWithNullValues() {
        AuditorDTO auditor = new AuditorDTO();
        auditor.setCity(null);
        auditor.setColor(null);
        auditor.setAcronym(null);
        auditor.setCreatedAt(null);
        auditor.setUpdatedAt(null);

        assertNull(auditor.getCity());
        assertNull(auditor.getColor());
        assertNull(auditor.getAcronym());
        assertNull(auditor.getCreatedAt());
        assertNull(auditor.getUpdatedAt());
    }
}

