package com.ufro.dci.etransparency.etransparency_api_user.dtos.institution;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import jakarta.validation.ConstraintViolation;

import java.util.Set;

class InstitutionUpdateDTOTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void testGettersAndSetters() {
        InstitutionUpdateDTO institutionUpdateDTO = new InstitutionUpdateDTO();

        // Configura valores para cada propiedad
        institutionUpdateDTO.setId(1L);
        institutionUpdateDTO.setActive(true);
        institutionUpdateDTO.setAddress("123 Main St");
        institutionUpdateDTO.setCity("CityName");
        institutionUpdateDTO.setName("Institution Name");
        institutionUpdateDTO.setRegion("RegionName");
        institutionUpdateDTO.setPhoneNumber("1234567890");
        institutionUpdateDTO.setWhatsapp("1234567890");
        institutionUpdateDTO.setNEmployees(100L);
        institutionUpdateDTO.setColor("Blue");
        institutionUpdateDTO.setAcronym("INST");
        institutionUpdateDTO.setLevel(1L);
        institutionUpdateDTO.setWebsite("https://example.com");
        institutionUpdateDTO.setCompanyRut("12345678-9");
        institutionUpdateDTO.setCompanyName("Company Name");

        // Verifica cada getter
        assertEquals(1L, institutionUpdateDTO.getId());
        assertTrue(institutionUpdateDTO.isActive());
        assertEquals("123 Main St", institutionUpdateDTO.getAddress());
        assertEquals("CityName", institutionUpdateDTO.getCity());
        assertEquals("Institution Name", institutionUpdateDTO.getName());
        assertEquals("RegionName", institutionUpdateDTO.getRegion());
        assertEquals("1234567890", institutionUpdateDTO.getPhoneNumber());
        assertEquals("1234567890", institutionUpdateDTO.getWhatsapp());
        assertEquals(100L, institutionUpdateDTO.getNEmployees());
        assertEquals("Blue", institutionUpdateDTO.getColor());
        assertEquals("INST", institutionUpdateDTO.getAcronym());
        assertEquals(1L, institutionUpdateDTO.getLevel());
        assertEquals("https://example.com", institutionUpdateDTO.getWebsite());
        assertEquals("12345678-9", institutionUpdateDTO.getCompanyRut());
        assertEquals("Company Name", institutionUpdateDTO.getCompanyName());
    }

    @Test
    void testAllArgsConstructor() {
        InstitutionUpdateDTO institutionUpdateDTO = new InstitutionUpdateDTO(1L, true, "123 Main St", "CityName",
                "Institution Name", "RegionName", "1234567890", "1234567890", 100L, "Blue", "INST", 1L,
                "https://example.com", "12345678-9", "Company Name");

        // Verifica que el constructor asigne los valores correctamente
        assertEquals(1L, institutionUpdateDTO.getId());
        assertTrue(institutionUpdateDTO.isActive());
        assertEquals("123 Main St", institutionUpdateDTO.getAddress());
        assertEquals("CityName", institutionUpdateDTO.getCity());
        assertEquals("Institution Name", institutionUpdateDTO.getName());
        assertEquals("RegionName", institutionUpdateDTO.getRegion());
        assertEquals("1234567890", institutionUpdateDTO.getPhoneNumber());
        assertEquals("1234567890", institutionUpdateDTO.getWhatsapp());
        assertEquals(100L, institutionUpdateDTO.getNEmployees());
        assertEquals("Blue", institutionUpdateDTO.getColor());
        assertEquals("INST", institutionUpdateDTO.getAcronym());
        assertEquals(1L, institutionUpdateDTO.getLevel());
        assertEquals("https://example.com", institutionUpdateDTO.getWebsite());
        assertEquals("12345678-9", institutionUpdateDTO.getCompanyRut());
        assertEquals("Company Name", institutionUpdateDTO.getCompanyName());
    }

    @Test
    void testEqualsAndHashCode() {
        InstitutionUpdateDTO institutionUpdateDTO1 = new InstitutionUpdateDTO(1L, true, "123 Main St", "CityName",
                "Institution Name", "RegionName", "1234567890", "1234567890", 100L, "Blue", "INST", 1L,
                "https://example.com", "12345678-9", "Company Name");
        InstitutionUpdateDTO institutionUpdateDTO2 = new InstitutionUpdateDTO(1L, true, "123 Main St", "CityName",
                "Institution Name", "RegionName", "1234567890", "1234567890", 100L, "Blue", "INST", 1L,
                "https://example.com", "12345678-9", "Company Name");

        // Verifica que equals y hashCode funcionen correctamente
        assertEquals(institutionUpdateDTO1, institutionUpdateDTO2);
        assertEquals(institutionUpdateDTO1.hashCode(), institutionUpdateDTO2.hashCode());
    }

    @Test
    void testToString() {
        InstitutionUpdateDTO institutionUpdateDTO = new InstitutionUpdateDTO();

        // Verifica que toString no retorne null
        assertNotNull(institutionUpdateDTO.toString());
    }

    @Test
    void testValidation() {
        InstitutionUpdateDTO institutionUpdateDTO = new InstitutionUpdateDTO();
        institutionUpdateDTO.setAddress("123456789012345678901234567890123456789012345678901"); // Invalid, exceeds 50 characters
        institutionUpdateDTO.setCity("Valid City");
        institutionUpdateDTO.setName("Valid Institution Name");
        institutionUpdateDTO.setRegion("Valid Region");
        institutionUpdateDTO.setPhoneNumber("1234567890123456"); // Invalid, exceeds 15 characters
        institutionUpdateDTO.setWhatsapp("Valid Whatsapp");

        Set<ConstraintViolation<InstitutionUpdateDTO>> violations = validator.validate(institutionUpdateDTO);

        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals("Address cannot exceed 50 characters")));
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals("Phone Number cannot exceed 15 characters")));
    }
}
