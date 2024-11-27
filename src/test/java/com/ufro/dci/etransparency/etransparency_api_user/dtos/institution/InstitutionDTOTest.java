package com.ufro.dci.etransparency.etransparency_api_user.dtos.institution;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.ufro.dci.etransparency.etransparency_api_user.dtos.auditor.AuditorDTO;
import com.ufro.dci.etransparency.etransparency_api_user.dtos.manager.ManagerDTO;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import jakarta.validation.ConstraintViolation;
import java.util.Date;
import java.util.Set;

class InstitutionDTOTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void testGettersAndSetters() {
        InstitutionDTO institutionDTO = new InstitutionDTO();

        // Configura valores para cada propiedad
        institutionDTO.setId(1L);
        institutionDTO.setActive(true);
        institutionDTO.setAddress("123 Main St");
        institutionDTO.setCity("CityName");
        institutionDTO.setName("Institution Name");
        institutionDTO.setRegion("RegionName");
        institutionDTO.setPhoneNumber("1234567890");
        institutionDTO.setWhatsapp("1234567890");
        institutionDTO.setNEmployees(100L);
        institutionDTO.setColor("Blue");
        institutionDTO.setAcronym("INST");
        institutionDTO.setLevel(1L);
        institutionDTO.setWebsite("https://example.com");
        institutionDTO.setCompanyRut("12345678-9");
        institutionDTO.setCompanyName("Company Name");
        institutionDTO.setNProcesses(10L);
        Date now = new Date();
        institutionDTO.setCreatedAt(now);
        institutionDTO.setUpdatedAt(now);
        ManagerDTO manager = new ManagerDTO();
        AuditorDTO auditor = new AuditorDTO();
        institutionDTO.setManager(manager);
        institutionDTO.setAuditor(auditor);

        // Verifica cada getter
        assertEquals(1L, institutionDTO.getId());
        assertTrue(institutionDTO.isActive());
        assertEquals("123 Main St", institutionDTO.getAddress());
        assertEquals("CityName", institutionDTO.getCity());
        assertEquals("Institution Name", institutionDTO.getName());
        assertEquals("RegionName", institutionDTO.getRegion());
        assertEquals("1234567890", institutionDTO.getPhoneNumber());
        assertEquals("1234567890", institutionDTO.getWhatsapp());
        assertEquals(100L, institutionDTO.getNEmployees());
        assertEquals("Blue", institutionDTO.getColor());
        assertEquals("INST", institutionDTO.getAcronym());
        assertEquals(1L, institutionDTO.getLevel());
        assertEquals("https://example.com", institutionDTO.getWebsite());
        assertEquals("12345678-9", institutionDTO.getCompanyRut());
        assertEquals("Company Name", institutionDTO.getCompanyName());
        assertEquals(10L, institutionDTO.getNProcesses());
        assertEquals(now, institutionDTO.getCreatedAt());
        assertEquals(now, institutionDTO.getUpdatedAt());
        assertEquals(manager, institutionDTO.getManager());
        assertEquals(auditor, institutionDTO.getAuditor());
    }

    @Test
    void testAllArgsConstructor() {
        Date now = new Date();
        ManagerDTO manager = new ManagerDTO();
        AuditorDTO auditor = new AuditorDTO();
        InstitutionDTO institutionDTO = new InstitutionDTO(1L, true, "123 Main St", "CityName", "Institution Name", "RegionName",
                "1234567890", "1234567890", 100L, "Blue", "INST", 1L,
                "https://example.com", "12345678-9", "Company Name",
                10L, now, now, manager, auditor);

        // Verifica que el constructor asigne los valores correctamente
        assertEquals(1L, institutionDTO.getId());
        assertTrue(institutionDTO.isActive());
        assertEquals("123 Main St", institutionDTO.getAddress());
        assertEquals("CityName", institutionDTO.getCity());
        assertEquals("Institution Name", institutionDTO.getName());
        assertEquals("RegionName", institutionDTO.getRegion());
        assertEquals("1234567890", institutionDTO.getPhoneNumber());
        assertEquals("1234567890", institutionDTO.getWhatsapp());
        assertEquals(100L, institutionDTO.getNEmployees());
        assertEquals("Blue", institutionDTO.getColor());
        assertEquals("INST", institutionDTO.getAcronym());
        assertEquals(1L, institutionDTO.getLevel());
        assertEquals("https://example.com", institutionDTO.getWebsite());
        assertEquals("12345678-9", institutionDTO.getCompanyRut());
        assertEquals("Company Name", institutionDTO.getCompanyName());
        assertEquals(10L, institutionDTO.getNProcesses());
        assertEquals(now, institutionDTO.getCreatedAt());
        assertEquals(now, institutionDTO.getUpdatedAt());
        assertEquals(manager, institutionDTO.getManager());
        assertEquals(auditor, institutionDTO.getAuditor());
    }

    @Test
    void testEqualsAndHashCode() {
        Date now = new Date();
        ManagerDTO manager = new ManagerDTO();
        AuditorDTO auditor = new AuditorDTO();
        InstitutionDTO institutionDTO1 = new InstitutionDTO(1L, true, "123 Main St", "CityName", "Institution Name",
                "RegionName", "1234567890", "1234567890", 100L, "Blue",
                "INST", 1L, "https://example.com", "12345678-9", "Company Name",
                10L, now, now, manager, auditor);
        InstitutionDTO institutionDTO2 = new InstitutionDTO(1L, true, "123 Main St", "CityName", "Institution Name",
                "RegionName", "1234567890", "1234567890", 100L, "Blue",
                "INST", 1L, "https://example.com", "12345678-9", "Company Name",
                10L, now, now, manager, auditor);

        // Verifica que equals y hashCode funcionen correctamente
        assertEquals(institutionDTO1, institutionDTO2);
        assertEquals(institutionDTO1.hashCode(), institutionDTO2.hashCode());
    }

    @Test
    void testToString() {
        InstitutionDTO institutionDTO = new InstitutionDTO();
        // Verifica que toString no retorne null
        assertNotNull(institutionDTO.toString());
    }

    @Test
    void testValidation() {
        InstitutionDTO institutionDTO = new InstitutionDTO();
        institutionDTO.setAddress(""); // Invalid, should not be blank
        institutionDTO.setCity("CityName");
        institutionDTO.setName("Institution Name");
        institutionDTO.setRegion("RegionName");
        institutionDTO.setPhoneNumber("1234567890");
        institutionDTO.setWhatsapp("1234567890");
        institutionDTO.setWebsite("https://example.com");
        institutionDTO.setCompanyRut("12345678-9");
        institutionDTO.setCompanyName("Company Name");

        Set<ConstraintViolation<InstitutionDTO>> violations = validator.validate(institutionDTO);

        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals("Address cannot be blank")));
    }
}
