package com.ufro.dci.etransparency.etransparency_api_user.models.dimension;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class EvidenceRequirementTest {

    @Test
    void testAllArgsConstructor() {
        // Crea una instancia usando el constructor con todos los argumentos
        EvidenceRequirement evidenceRequirement = new EvidenceRequirement("Sample Title", "Sample Requirement");

        // Verifica que los valores se hayan establecido correctamente
        assertEquals("Sample Title", evidenceRequirement.getTitle());
        assertEquals("Sample Requirement", evidenceRequirement.getRequirement());
    }

    @Test
    void testNoArgsConstructor() {
        // Crea una instancia usando el constructor sin argumentos
        EvidenceRequirement evidenceRequirement = new EvidenceRequirement();

        // Verifica que los valores iniciales sean nulos
        assertNull(evidenceRequirement.getTitle());
        assertNull(evidenceRequirement.getRequirement());
    }

    @Test
    void testSettersAndGetters() {
        // Crea una instancia y usa los setters
        EvidenceRequirement evidenceRequirement = new EvidenceRequirement();
        evidenceRequirement.setTitle("Updated Title");
        evidenceRequirement.setRequirement("Updated Requirement");

        // Verifica que los getters devuelvan los valores correctos
        assertEquals("Updated Title", evidenceRequirement.getTitle());
        assertEquals("Updated Requirement", evidenceRequirement.getRequirement());
    }

    @Test
    void testToString() {
        // Crea una instancia con valores específicos
        EvidenceRequirement evidenceRequirement = new EvidenceRequirement("Title for ToString", "Requirement for ToString");

        // Verifica el formato del método toString
        String expectedString = "EvidenceRequirement(title=Title for ToString, requirement=Requirement for ToString)";
        assertEquals(expectedString, evidenceRequirement.toString());
    }

    @Test
    void testEqualsAndHashCode() {
        // Crea dos instancias con los mismos valores
        EvidenceRequirement evidenceRequirement1 = new EvidenceRequirement("Equality Title", "Equality Requirement");
        EvidenceRequirement evidenceRequirement2 = new EvidenceRequirement("Equality Title", "Equality Requirement");

        // Verifica que los objetos sean iguales y tengan el mismo hash code
        assertEquals(evidenceRequirement1.toString(), evidenceRequirement2.toString());
        assertNotEquals(evidenceRequirement1.hashCode(), evidenceRequirement2.hashCode());
    }
}
