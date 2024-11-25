package com.ufro.dci.etransparency.etransparency_api_user.models.recommendation;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.ufro.dci.etransparency.etransparency_api_user.models.process.Process;

class RecommendationTest {

    private Recommendation recommendation;
    private List<Process> processes;

    @BeforeEach
    void setUp() {
        processes = new ArrayList<>();
        recommendation = new Recommendation();
    }

    @Test
    void testRecommendationConstructor() {
        Date createdAt = new Date();
        Date updatedAt = new Date();
        Recommendation rec = new Recommendation(1L, "Dimension A", 1L, 2L, "Improvement needed", createdAt, updatedAt, processes);

        // Verificar los valores
        assertEquals(1L, rec.getId());
        assertEquals("Dimension A", rec.getDimension());
        assertEquals(1L, rec.getCurrentLevel());
        assertEquals(2L, rec.getTargetLevel());
        assertEquals("Improvement needed", rec.getDescription());
        assertEquals(createdAt, rec.getCreatedAt());
        assertEquals(updatedAt, rec.getUpdatedAt());
        assertEquals(processes, rec.getProcesses());
    }

    @Test
    void testSettersAndGetters() {
        recommendation.setId(1L);
        recommendation.setDimension("Dimension B");
        recommendation.setCurrentLevel(2L);
        recommendation.setTargetLevel(3L);
        recommendation.setDescription("Enhanced recommendation");
        recommendation.setCreatedAt(new Date());
        recommendation.setUpdatedAt(new Date());
        recommendation.setProcesses(processes);

        assertEquals(1L, recommendation.getId());
        assertEquals("Dimension B", recommendation.getDimension());
        assertEquals(2L, recommendation.getCurrentLevel());
        assertEquals(3L, recommendation.getTargetLevel());
        assertEquals("Enhanced recommendation", recommendation.getDescription());
        assertNotNull(recommendation.getCreatedAt());
        assertNotNull(recommendation.getUpdatedAt());
        assertEquals(processes, recommendation.getProcesses());
    }

    @Test
    void testAddProcess() {
        Process process = new Process();
        recommendation.setProcesses(new ArrayList<>());

        recommendation.getProcesses().add(process);

        assertEquals(1, recommendation.getProcesses().size());
        assertTrue(recommendation.getProcesses().contains(process));
    }

    @Test
    void testRemoveProcess() {
        Process process1 = new Process();
        Process process2 = new Process();
        List<Process> processList = new ArrayList<>(List.of(process1, process2));

        recommendation.setProcesses(processList);

        recommendation.getProcesses().remove(process1);

        assertEquals(1, recommendation.getProcesses().size());
        assertFalse(recommendation.getProcesses().contains(process1));
    }

    @Test
    void testHandleEmptyProcessesList() {
        recommendation.setProcesses(new ArrayList<>());

        assertNotNull(recommendation.getProcesses());
        assertTrue(recommendation.getProcesses().isEmpty());
    }

    @Test
    void testSetDates() {
        Date createdAt = new Date();
        Date updatedAt = new Date();

        recommendation.setCreatedAt(createdAt);
        recommendation.setUpdatedAt(updatedAt);

        assertEquals(createdAt, recommendation.getCreatedAt());
        assertEquals(updatedAt, recommendation.getUpdatedAt());
    }
}
