package com.ufro.dci.etransparency.etransparency_api_user.dtos.institution;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;


class SimpleInstitutionDTOTest {

    @Test
    void testGettersAndSetters() {
        SimpleInstitutionDTO simpleInstitutionDTO = new SimpleInstitutionDTO();

        // Configura valores para cada propiedad
        simpleInstitutionDTO.setId(1L);
        simpleInstitutionDTO.setName("Institution Name");
        simpleInstitutionDTO.setAcronym("INST");
        simpleInstitutionDTO.setColor("Blue");
        simpleInstitutionDTO.setCity("CityName");

        // Verifica cada getter
        assertEquals(1L, simpleInstitutionDTO.getId());
        assertEquals("Institution Name", simpleInstitutionDTO.getName());
        assertEquals("INST", simpleInstitutionDTO.getAcronym());
        assertEquals("Blue", simpleInstitutionDTO.getColor());
        assertEquals("CityName", simpleInstitutionDTO.getCity());
    }

    @Test
    void testAllArgsConstructor() {
        SimpleInstitutionDTO simpleInstitutionDTO = new SimpleInstitutionDTO(1L, "Institution Name", "INST", "Blue", "CityName");

        // Verifica que el constructor asigne los valores correctamente
        assertEquals(1L, simpleInstitutionDTO.getId());
        assertEquals("Institution Name", simpleInstitutionDTO.getName());
        assertEquals("INST", simpleInstitutionDTO.getAcronym());
        assertEquals("Blue", simpleInstitutionDTO.getColor());
        assertEquals("CityName", simpleInstitutionDTO.getCity());
    }

    @Test
    void testEqualsAndHashCode() {
        SimpleInstitutionDTO simpleInstitutionDTO1 = new SimpleInstitutionDTO(1L, "Institution Name", "INST", "Blue", "CityName");
        SimpleInstitutionDTO simpleInstitutionDTO2 = new SimpleInstitutionDTO(1L, "Institution Name", "INST", "Blue", "CityName");

        // Verifica que equals y hashCode funcionen correctamente
        assertEquals(simpleInstitutionDTO1, simpleInstitutionDTO2);
        assertEquals(simpleInstitutionDTO1.hashCode(), simpleInstitutionDTO2.hashCode());
    }

    @Test
    void testToString() {
        SimpleInstitutionDTO simpleInstitutionDTO = new SimpleInstitutionDTO(1L, "Institution Name", "INST", "Blue", "CityName");

        // Verifica que toString no retorne null
        assertNotNull(simpleInstitutionDTO.toString());
    }
}

