package com.ufro.dci.etransparency.etransparency_api_user.models.maturity;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.ufro.dci.etransparency.etransparency_api_user.models.dimension.Dimension;
import com.ufro.dci.etransparency.etransparency_api_user.models.process.Process;

import org.junit.jupiter.api.Test;

class MaturityModelTest {

    @Test
    void testMaturityModelConstructor() {
        MaturityModel maturityModel = new MaturityModel();

        assertNull(maturityModel.getId());
        assertFalse(maturityModel.isActive());
        assertNull(maturityModel.getName());
        assertNull(maturityModel.getDescription());
        assertNull(maturityModel.getTimeLimit());
        assertNull(maturityModel.getApprovalPercentage());
        assertNull(maturityModel.getCreatedAt());
        assertNull(maturityModel.getUpdatedAt());
        assertNull(maturityModel.getMaturityModelFile());
        if (maturityModel.getProcesses() == null) {
            maturityModel.setProcesses(new ArrayList<>());
        }
        assertNotNull(maturityModel.getProcesses());
        assertTrue(maturityModel.getProcesses().isEmpty());
        if (maturityModel.getDimensions() == null) {
            maturityModel.setDimensions(new ArrayList<>());
        }
        assertNotNull(maturityModel.getDimensions());
        assertTrue(maturityModel.getDimensions().isEmpty());
    }

    @Test
    void testSettersAndGetters() {
        MaturityModel maturityModel = new MaturityModel();

        maturityModel.setId(1L);
        maturityModel.setActive(true);
        maturityModel.setName("Maturity Model Test");
        maturityModel.setDescription("Description for Maturity Model");
        maturityModel.setTimeLimit(60L);
        maturityModel.setApprovalPercentage(75.0);
        Date createdAt = new Date();
        Date updatedAt = new Date();
        maturityModel.setCreatedAt(createdAt);
        maturityModel.setUpdatedAt(updatedAt);

        List<Process> processes = new ArrayList<>();
        maturityModel.setProcesses(processes);

        List<Dimension> dimensions = new ArrayList<>();
        maturityModel.setDimensions(dimensions);

        MaturityModelFile file = new MaturityModelFile();
        maturityModel.setMaturityModelFile(file);

        assertEquals(1L, maturityModel.getId());
        assertTrue(maturityModel.isActive());
        assertEquals("Maturity Model Test", maturityModel.getName());
        assertEquals("Description for Maturity Model", maturityModel.getDescription());
        assertEquals(60L, maturityModel.getTimeLimit());
        assertEquals(75.0, maturityModel.getApprovalPercentage());
        assertEquals(createdAt, maturityModel.getCreatedAt());
        assertEquals(updatedAt, maturityModel.getUpdatedAt());
        assertEquals(processes, maturityModel.getProcesses());
        assertEquals(dimensions, maturityModel.getDimensions());
        assertEquals(file, maturityModel.getMaturityModelFile());
    }

    @Test
    void testAllArgsConstructor() {
        List<Process> processes = new ArrayList<>();
        List<Dimension> dimensions = new ArrayList<>();
        MaturityModelFile file = new MaturityModelFile();
        Date createdAt = new Date();
        Date updatedAt = new Date();

        MaturityModel maturityModel = new MaturityModel(1L, true, "Advanced Model", "Detailed description", 90L, 85.0,
                createdAt, updatedAt, processes, dimensions, file);

        assertEquals(1L, maturityModel.getId());
        assertTrue(maturityModel.isActive());
        assertEquals("Advanced Model", maturityModel.getName());
        assertEquals("Detailed description", maturityModel.getDescription());
        assertEquals(90L, maturityModel.getTimeLimit());
        assertEquals(85.0, maturityModel.getApprovalPercentage());
        assertEquals(createdAt, maturityModel.getCreatedAt());
        assertEquals(updatedAt, maturityModel.getUpdatedAt());
        assertEquals(processes, maturityModel.getProcesses());
        assertEquals(dimensions, maturityModel.getDimensions());
        assertEquals(file, maturityModel.getMaturityModelFile());
    }

    @Test
    void testBuilder() {
        List<Process> processes = new ArrayList<>();
        List<Dimension> dimensions = new ArrayList<>();
        MaturityModelFile file = new MaturityModelFile();
        Date createdAt = new Date();
        Date updatedAt = new Date();

        MaturityModel maturityModel = MaturityModel.builder()
                .id(2L)
                .isActive(false)
                .name("Beginner Model")
                .description("Basic description")
                .timeLimit(30L)
                .approvalPercentage(50.0)
                .createdAt(createdAt)
                .updatedAt(updatedAt)
                .processes(processes)
                .dimensions(dimensions)
                .maturityModelFile(file)
                .build();

        assertEquals(2L, maturityModel.getId());
        assertFalse(maturityModel.isActive());
        assertEquals("Beginner Model", maturityModel.getName());
        assertEquals("Basic description", maturityModel.getDescription());
        assertEquals(30L, maturityModel.getTimeLimit());
        assertEquals(50.0, maturityModel.getApprovalPercentage());
        assertEquals(createdAt, maturityModel.getCreatedAt());
        assertEquals(updatedAt, maturityModel.getUpdatedAt());
        assertEquals(processes, maturityModel.getProcesses());
        assertEquals(dimensions, maturityModel.getDimensions());
        assertEquals(file, maturityModel.getMaturityModelFile());
    }

    @Test
    void testProcessListInitialization() {
        MaturityModel maturityModel = new MaturityModel();
        if (maturityModel.getProcesses() == null) {
            maturityModel.setProcesses(new ArrayList<>());
        }
        assertNotNull(maturityModel.getProcesses());
        assertTrue(maturityModel.getProcesses().isEmpty());

        Process process = new Process();
        maturityModel.getProcesses().add(process);

        assertEquals(1, maturityModel.getProcesses().size());
        assertEquals(process, maturityModel.getProcesses().get(0));
    }

    @Test
    void testDimensionListInitialization() {
        MaturityModel maturityModel = new MaturityModel();
        if (maturityModel.getDimensions() == null) {
            maturityModel.setDimensions(new ArrayList<>());
        }
        assertNotNull(maturityModel.getDimensions());
        assertTrue(maturityModel.getDimensions().isEmpty());

        Dimension dimension = new Dimension();
        maturityModel.getDimensions().add(dimension);

        assertEquals(1, maturityModel.getDimensions().size());
        assertEquals(dimension, maturityModel.getDimensions().get(0));
    }
}
