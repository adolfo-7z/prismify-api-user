package com.ufro.dci.etransparency.etransparency_api_user.models.result;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.ufro.dci.etransparency.etransparency_api_user.models.process.Process;
import org.junit.jupiter.api.Test;

class ProcessResultTest {

    @Test
    void testProcessResultConstructor() {
        ProcessResult processResult = new ProcessResult();

        assertNull(processResult.getId());
        assertFalse(processResult.isActive());
        assertNull(processResult.getPeopleAnswered());
        assertNull(processResult.getDimensionLevels());
        assertNull(processResult.getInstitutionLevel());
        assertNull(processResult.getDaysPassed());
        assertNull(processResult.getDaysLeft());
        assertNull(processResult.getAnsweredSurveys());
        assertNull(processResult.getSurveyQuorum());
        assertNull(processResult.getTotalSurveys());
        assertNull(processResult.getEstimatedCompletionDate());
        assertNull(processResult.getProcess());
        if (processResult.getClusters() == null) {
           processResult.setClusters(new ArrayList<>());
        }
        assertNotNull(processResult.getClusters());
        assertTrue(processResult.getClusters().isEmpty());
        if (processResult.getResponseAverages() == null) {
           processResult.setResponseAverages(new ArrayList<>());
        }
        assertNotNull(processResult.getResponseAverages());
        assertTrue(processResult.getResponseAverages().isEmpty());
    }

    @Test
    void testAllArgsConstructor() {
        List<Long> dimensionLevels = List.of(1L, 2L, 3L);
        List<ClusterResult> clusters = new ArrayList<>();
        List<ResponseAverage> responseAverages = new ArrayList<>();
        Date estimatedDate = new Date();

        ProcessResult processResult = new ProcessResult();
        processResult.setActive(true);
        processResult.setPeopleAnswered(75.0);
        processResult.setDimensionLevels(dimensionLevels);
        processResult.setInstitutionLevel(3L);
        processResult.setDaysPassed(5L);
        processResult.setDaysLeft(10L);
        processResult.setAnsweredSurveys(50L);
        processResult.setSurveyQuorum(60L);
        processResult.setTotalSurveys(100L);
        processResult.setEstimatedCompletionDate(estimatedDate);
        processResult.setClusters(clusters);
        processResult.setResponseAverages(responseAverages);

        assertTrue(processResult.isActive());
        assertEquals(75.0, processResult.getPeopleAnswered());
        assertEquals(dimensionLevels, processResult.getDimensionLevels());
        assertEquals(3L, processResult.getInstitutionLevel());
        assertEquals(5L, processResult.getDaysPassed());
        assertEquals(10L, processResult.getDaysLeft());
        assertEquals(50L, processResult.getAnsweredSurveys());
        assertEquals(60L, processResult.getSurveyQuorum());
        assertEquals(100L, processResult.getTotalSurveys());
        assertEquals(estimatedDate, processResult.getEstimatedCompletionDate());
        assertEquals(clusters, processResult.getClusters());
        assertEquals(responseAverages, processResult.getResponseAverages());
    }

    @Test
    void testSettersAndGetters() {
        ProcessResult processResult = new ProcessResult();

        processResult.setActive(true);
        processResult.setPeopleAnswered(90.0);
        processResult.setDimensionLevels(List.of(1L, 2L));
        processResult.setInstitutionLevel(4L);
        processResult.setDaysPassed(12L);
        processResult.setDaysLeft(18L);
        processResult.setAnsweredSurveys(70L);
        processResult.setSurveyQuorum(80L);
        processResult.setTotalSurveys(90L);
        Date date = new Date();
        processResult.setEstimatedCompletionDate(date);
        Process process = new Process();
        processResult.setProcess(process);

        assertTrue(processResult.isActive());
        assertEquals(90.0, processResult.getPeopleAnswered());
        assertEquals(2, processResult.getDimensionLevels().size());
        assertEquals(4L, processResult.getInstitutionLevel());
        assertEquals(12L, processResult.getDaysPassed());
        assertEquals(18L, processResult.getDaysLeft());
        assertEquals(70L, processResult.getAnsweredSurveys());
        assertEquals(80L, processResult.getSurveyQuorum());
        assertEquals(90L, processResult.getTotalSurveys());
        assertEquals(date, processResult.getEstimatedCompletionDate());
        assertEquals(process, processResult.getProcess());
    }

    @Test
    void testAddClusterResult() {
        ProcessResult processResult = new ProcessResult();
        ClusterResult clusterResult = new ClusterResult();
        processResult.setClusters(new ArrayList<>());

        processResult.getClusters().add(clusterResult);

        assertEquals(1, processResult.getClusters().size());
        assertEquals(clusterResult, processResult.getClusters().get(0));
    }

    @Test
    void testAddResponseAverage() {
        ProcessResult processResult = new ProcessResult();
        ResponseAverage responseAverage = new ResponseAverage();
        processResult.setResponseAverages(new ArrayList<>());

        processResult.getResponseAverages().add(responseAverage);

        assertEquals(1, processResult.getResponseAverages().size());
        assertEquals(responseAverage, processResult.getResponseAverages().get(0));
    }
}
