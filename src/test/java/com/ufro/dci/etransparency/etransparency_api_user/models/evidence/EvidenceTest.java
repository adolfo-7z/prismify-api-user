package com.ufro.dci.etransparency.etransparency_api_user.models.evidence;

import com.ufro.dci.etransparency.etransparency_api_user.models.dimension.Dimension;
import com.ufro.dci.etransparency.etransparency_api_user.models.process.Process;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EvidenceTest {

    @Test
    void testEvidenceConstructor() {
        Evidence evidence = new Evidence();
        assertNull(evidence.getId());
        assertNull(evidence.getFileData());
        assertNull(evidence.getFileName());
        assertNull(evidence.getFileType());
        assertNull(evidence.getConditions());
        assertNull(evidence.getComment());
        assertNull(evidence.getEvidenceStatus());
        assertNull(evidence.getDimension());
        assertNull(evidence.getProcess());
    }

    @Test
    void testSettersAndGetters() {
        Evidence evidence = new Evidence();

        byte[] fileData = new byte[]{1, 2, 3};
        evidence.setFileData(fileData);
        evidence.setFileName("test_file.pdf");
        evidence.setFileType("application/pdf");
        evidence.setConditions("All conditions met");
        evidence.setComment("Reviewed and approved");
        evidence.setEvidenceStatus(Evidence.EvidenceStatus.ACCEPTED);

        Dimension dimension = new Dimension();
        Process process = new Process();
        evidence.setDimension(dimension);
        evidence.setProcess(process);

        assertArrayEquals(fileData, evidence.getFileData());
        assertEquals("test_file.pdf", evidence.getFileName());
        assertEquals("application/pdf", evidence.getFileType());
        assertEquals("All conditions met", evidence.getConditions());
        assertEquals("Reviewed and approved", evidence.getComment());
        assertEquals(Evidence.EvidenceStatus.ACCEPTED, evidence.getEvidenceStatus());
        assertEquals(dimension, evidence.getDimension());
        assertEquals(process, evidence.getProcess());
    }

    @Test
    void testEvidenceStatusEnum() {
        Evidence evidence = new Evidence();

        evidence.setEvidenceStatus(Evidence.EvidenceStatus.NOT_EVALUATED);
        assertEquals(Evidence.EvidenceStatus.NOT_EVALUATED, evidence.getEvidenceStatus());

        evidence.setEvidenceStatus(Evidence.EvidenceStatus.ACCEPTED);
        assertEquals(Evidence.EvidenceStatus.ACCEPTED, evidence.getEvidenceStatus());

        evidence.setEvidenceStatus(Evidence.EvidenceStatus.REJECTED);
        assertEquals(Evidence.EvidenceStatus.REJECTED, evidence.getEvidenceStatus());

        evidence.setEvidenceStatus(Evidence.EvidenceStatus.APPEAL);
        assertEquals(Evidence.EvidenceStatus.APPEAL, evidence.getEvidenceStatus());
    }

    @Test
    void testToString() {
        Evidence evidence = new Evidence();
        evidence.setFileName("example_file.pdf");
        evidence.setFileType("application/pdf");
        evidence.setConditions("Sample conditions");
        evidence.setComment("Sample comment");

        String toStringOutput = evidence.toString();
        assertTrue(toStringOutput.contains("example_file.pdf"));
        assertTrue(toStringOutput.contains("application/pdf"));
        assertTrue(toStringOutput.contains("Sample conditions"));
        assertTrue(toStringOutput.contains("Sample comment"));
    }

    @Test
    void testSettingDimensionAndProcess() {
        Evidence evidence = new Evidence();
        Dimension dimension = new Dimension();
        Process process = new Process();

        evidence.setDimension(dimension);
        evidence.setProcess(process);

        assertNotNull(evidence.getDimension());
        assertNotNull(evidence.getProcess());
        assertEquals(dimension, evidence.getDimension());
        assertEquals(process, evidence.getProcess());
    }
}
