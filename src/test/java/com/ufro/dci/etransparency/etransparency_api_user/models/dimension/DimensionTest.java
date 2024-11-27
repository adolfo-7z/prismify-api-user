package com.ufro.dci.etransparency.etransparency_api_user.models.dimension;

import com.ufro.dci.etransparency.etransparency_api_user.models.evidence.Evidence;
import com.ufro.dci.etransparency.etransparency_api_user.models.level.Level;
import com.ufro.dci.etransparency.etransparency_api_user.models.maturity.MaturityModel;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DimensionTest {

    @Test
    void testDimensionConstructor() {
        Dimension dimension = new Dimension();
        assertNull(dimension.getId());
        assertFalse(dimension.isActive());
        assertNull(dimension.getName());
        assertNull(dimension.getDescription());
        assertNull(dimension.getMaturityModel());
        assertNull(dimension.getLevels());
        assertNull(dimension.getEvidence());
        assertNull(dimension.getEvidenceRequirements());
    }

    @Test
    void testSettersAndGetters() {
        Dimension dimension = new Dimension();
        dimension.setId(1L);
        dimension.setActive(true);
        dimension.setName("Sample Dimension");
        dimension.setDescription("Description of Sample Dimension");

        MaturityModel maturityModel = new MaturityModel();
        dimension.setMaturityModel(maturityModel);

        List<Level> levels = new ArrayList<>();
        Level level1 = new Level();
        levels.add(level1);
        dimension.setLevels(levels);

        List<Evidence> evidenceList = new ArrayList<>();
        Evidence evidence = new Evidence();
        evidenceList.add(evidence);
        dimension.setEvidence(evidenceList);

        List<EvidenceRequirement> requirements = List.of(
                new EvidenceRequirement("Requirement 1", "Description 1"),
                new EvidenceRequirement("Requirement 2", "Description 2")
        );
        dimension.setEvidenceRequirements(requirements);

        assertEquals(1L, dimension.getId());
        assertTrue(dimension.isActive());
        assertEquals("Sample Dimension", dimension.getName());
        assertEquals("Description of Sample Dimension", dimension.getDescription());
        assertEquals(maturityModel, dimension.getMaturityModel());
        assertEquals(levels, dimension.getLevels());
        assertEquals(evidenceList, dimension.getEvidence());
        assertEquals(requirements, dimension.getEvidenceRequirements());
    }

    @Test
    void testAddAndRemoveLevel() {
        Dimension dimension = new Dimension();
        Level level = new Level();

        dimension.setLevels(new ArrayList<>());
        dimension.getLevels().add(level);

        assertEquals(1, dimension.getLevels().size());
        assertTrue(dimension.getLevels().contains(level));

        dimension.getLevels().remove(level);
        assertEquals(0, dimension.getLevels().size());
    }

    @Test
    void testAddAndRemoveEvidence() {
        Dimension dimension = new Dimension();
        Evidence evidence = new Evidence();

        dimension.setEvidence(new ArrayList<>());
        dimension.getEvidence().add(evidence);

        assertEquals(1, dimension.getEvidence().size());
        assertTrue(dimension.getEvidence().contains(evidence));

        dimension.getEvidence().remove(evidence);
        assertEquals(0, dimension.getEvidence().size());
    }

    @Test
    void testToString() {
        Dimension dimension = new Dimension();
        dimension.setName("Dimension for ToString");
        dimension.setDescription("Description for ToString");

        String toStringOutput = dimension.toString();
        assertTrue(toStringOutput.contains("Dimension"));
        assertTrue(toStringOutput.contains("Dimension for ToString"));
        assertTrue(toStringOutput.contains("Description for ToString"));
    }

    @Test
    void testSetEvidenceRequirements() {
        Dimension dimension = new Dimension();
        List<EvidenceRequirement> requirements = new ArrayList<>();
        requirements.add(new EvidenceRequirement("Title 1", "Requirement 1"));
        requirements.add(new EvidenceRequirement("Title 2", "Requirement 2"));

        dimension.setEvidenceRequirements(requirements);

        assertNotNull(dimension.getEvidenceRequirements());
        assertEquals(2, dimension.getEvidenceRequirements().size());
        assertEquals("Title 1", dimension.getEvidenceRequirements().get(0).getTitle());
        assertEquals("Requirement 1", dimension.getEvidenceRequirements().get(0).getRequirement());
    }
}
