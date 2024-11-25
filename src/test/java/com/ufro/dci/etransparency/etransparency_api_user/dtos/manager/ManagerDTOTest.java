package com.ufro.dci.etransparency.etransparency_api_user.dtos.manager;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.ufro.dci.etransparency.etransparency_api_user.dtos.institution.InstitutionDTO;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;


class ManagerDTOTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void testGettersAndSetters() {
        ManagerDTO manager = new ManagerDTO();

        // Configura valores para cada propiedad heredada de UserDTO y nuevas propiedades de ManagerDTO
        manager.setId(1L);
        manager.setUsername("managerUsername");
        manager.setEmail("manager@example.com");
        manager.setPassword("securePassword");
        manager.setPhoneNumber("123456789");
        manager.setPosition("General Manager");
        manager.setRut("12345678-9");
        InstitutionDTO institution = new InstitutionDTO();
        manager.setInstitution(institution);

        // Verifica cada getter
        assertEquals(1L, manager.getId());
        assertEquals("managerUsername", manager.getUsername());
        assertEquals("manager@example.com", manager.getEmail());
        assertEquals("securePassword", manager.getPassword());
        assertEquals("123456789", manager.getPhoneNumber());
        assertEquals("General Manager", manager.getPosition());
        assertEquals("12345678-9", manager.getRut());
        assertEquals(institution, manager.getInstitution());
    }

    @Test
    void testAllArgsConstructor() {
        InstitutionDTO institution = new InstitutionDTO();
        ManagerDTO manager = new ManagerDTO();
        manager.setPosition("General Manager");
        manager.setRut("12345678-9");
        manager.setInstitution(institution);

        // Verifica que el constructor asigne los valores correctamente
        assertEquals("General Manager", manager.getPosition());
        assertEquals("12345678-9", manager.getRut());
        assertEquals(institution, manager.getInstitution());
    }

    @Test
    void testEqualsAndHashCode() {
        InstitutionDTO institution = new InstitutionDTO();

        ManagerDTO manager1 = new ManagerDTO();
        ManagerDTO manager2 = new ManagerDTO();
        ManagerDTO manager3 = new ManagerDTO();
        manager1.setPosition("General Manager");
        manager1.setRut("12345678-9");
        manager1.setInstitution(institution);
        manager2.setPosition("General Manager");
        manager2.setRut("12345678-9");
        manager2.setInstitution(institution);
        manager3.setPosition("Finance Manager");
        manager3.setRut("98765432-1");
        manager3.setInstitution(new InstitutionDTO());


        // Verifica que equals y hashCode funcionen correctamente para objetos iguales y diferentes
        assertEquals(manager1, manager2);
        assertNotEquals(manager1, manager3);
        assertEquals(manager1.hashCode(), manager2.hashCode());
        assertNotEquals(manager1.hashCode(), manager3.hashCode());
    }

    @Test
    void testToString() {
        InstitutionDTO institution = new InstitutionDTO();
        ManagerDTO manager = new ManagerDTO();
        manager.setPosition("General Manager");
        manager.setRut("12345678-9");
        manager.setInstitution(institution);

        // Verifica que toString no retorne null y contenga valores esperados
        String toString = manager.toString();
        assertNotNull(toString);
        assertTrue(toString.contains("General Manager"));
        assertTrue(toString.contains("12345678-9"));
    }

    @Test
    void testEqualsWithSelf() {
        ManagerDTO manager = new ManagerDTO();
        manager.setPosition("General Manager");
        manager.setRut("12345678-9");
        manager.setInstitution(new InstitutionDTO());
        assertEquals(manager, manager);
    }

    @Test
    void testEqualsWithDifferentClass() {
        ManagerDTO manager = new ManagerDTO();
        manager.setPosition("General Manager");
        manager.setRut("12345678-9");
        manager.setInstitution(new InstitutionDTO());
        String differentClass = "I am a string";
        assertNotEquals(manager, differentClass);
    }

    @Test
    void testSettersWithNullValues() {
        ManagerDTO manager = new ManagerDTO();
        manager.setPosition(null);
        manager.setRut(null);
        manager.setInstitution(null);

        assertNull(manager.getPosition());
        assertNull(manager.getRut());
        assertNull(manager.getInstitution());
    }
}
