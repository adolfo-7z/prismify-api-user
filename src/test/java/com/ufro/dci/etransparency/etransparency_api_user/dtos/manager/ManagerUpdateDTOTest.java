package com.ufro.dci.etransparency.etransparency_api_user.dtos.manager;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.ufro.dci.etransparency.etransparency_api_user.dtos.institution.InstitutionDTO;

class ManagerUpdateDTOTest {

    private ManagerUpdateDTO managerUpdateDTO;

    @BeforeEach
    void setUp() {
        managerUpdateDTO = new ManagerUpdateDTO();
    }

    @Test
    void testGettersAndSetters() {
        // Configura valores para cada propiedad heredada de UserUpdateDTO y nuevas propiedades de ManagerUpdateDTO
        managerUpdateDTO.setUsername("managerUsername");
        managerUpdateDTO.setEmail("manager@example.com");
        managerUpdateDTO.setPassword("securePassword");
        managerUpdateDTO.setPhoneNumber("123456789");
        managerUpdateDTO.setPosition("General Manager");
        managerUpdateDTO.setRut("12345678-9");
        InstitutionDTO institution = new InstitutionDTO();
        managerUpdateDTO.setInstitution(institution);

        // Verifica cada getter
        assertEquals("managerUsername", managerUpdateDTO.getUsername());
        assertEquals("manager@example.com", managerUpdateDTO.getEmail());
        assertEquals("securePassword", managerUpdateDTO.getPassword());
        assertEquals("123456789", managerUpdateDTO.getPhoneNumber());
        assertEquals("General Manager", managerUpdateDTO.getPosition());
        assertEquals("12345678-9", managerUpdateDTO.getRut());
        assertEquals(institution, managerUpdateDTO.getInstitution());
    }

    @Test
    void testAllArgsConstructor() {
        InstitutionDTO institution = new InstitutionDTO();
        ManagerUpdateDTO managerUpdateDTO = new ManagerUpdateDTO("General Manager", "12345678-9", institution);

        // Verifica que el constructor asigne los valores correctamente
        assertEquals("General Manager", managerUpdateDTO.getPosition());
        assertEquals("12345678-9", managerUpdateDTO.getRut());
        assertEquals(institution, managerUpdateDTO.getInstitution());
    }

    @Test
    void testEqualsAndHashCode() {
        InstitutionDTO institution = new InstitutionDTO();

        ManagerUpdateDTO manager1 = new ManagerUpdateDTO("General Manager", "12345678-9", institution);
        ManagerUpdateDTO manager2 = new ManagerUpdateDTO("General Manager", "12345678-9", institution);
        ManagerUpdateDTO manager3 = new ManagerUpdateDTO("Finance Manager", "98765432-1", new InstitutionDTO());

        // Verifica que equals y hashCode funcionen correctamente para objetos iguales y diferentes
        assertEquals(manager1, manager2);
        assertNotEquals(manager1, manager3);
        assertEquals(manager1.hashCode(), manager2.hashCode());
        assertNotEquals(manager1.hashCode(), manager3.hashCode());
    }

    @Test
    void testToString() {
        InstitutionDTO institution = new InstitutionDTO();
        ManagerUpdateDTO managerUpdateDTO = new ManagerUpdateDTO("General Manager", "12345678-9", institution);

        // Verifica que toString no retorne null y contenga valores esperados
        String toString = managerUpdateDTO.toString();
        assertNotNull(toString);
        assertTrue(toString.contains("General Manager"));
        assertTrue(toString.contains("12345678-9"));
    }

    @Test
    void testEqualsWithSelf() {
        ManagerUpdateDTO managerUpdateDTO = new ManagerUpdateDTO("General Manager", "12345678-9", new InstitutionDTO());
        assertEquals(managerUpdateDTO, managerUpdateDTO);
    }

    @Test
    void testEqualsWithDifferentClass() {
        ManagerUpdateDTO managerUpdateDTO = new ManagerUpdateDTO("General Manager", "12345678-9", new InstitutionDTO());
        String differentClass = "I am a string";
        assertNotEquals(managerUpdateDTO, differentClass);
    }

    @Test
    void testSettersWithNullValues() {
        managerUpdateDTO.setPosition(null);
        managerUpdateDTO.setRut(null);
        managerUpdateDTO.setInstitution(null);

        assertNull(managerUpdateDTO.getPosition());
        assertNull(managerUpdateDTO.getRut());
        assertNull(managerUpdateDTO.getInstitution());
    }
}
