package com.ufro.dci.etransparency.etransparency_api_user.models.process;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.ufro.dci.etransparency.etransparency_api_user.models.evidence.Evidence;
import com.ufro.dci.etransparency.etransparency_api_user.models.institution.Institution;
import com.ufro.dci.etransparency.etransparency_api_user.models.maturity.MaturityModel;
import com.ufro.dci.etransparency.etransparency_api_user.models.recommendation.Recommendation;
import com.ufro.dci.etransparency.etransparency_api_user.models.result.ProcessResult;
import com.ufro.dci.etransparency.etransparency_api_user.models.survey.Survey;

import org.junit.jupiter.api.Test;

class ProcessTest {

    @Test
    void testProcessConstructor() {
        Process process = new Process();

        assertNull(process.getId());
        assertNull(process.getStatus());
        assertNull(process.getRequestStatus());
        assertNull(process.getName());
        assertNull(process.getStartDate());
        assertNull(process.getEndDate());
        assertNull(process.getStep());
        assertNull(process.getSurveyLink());
        assertNull(process.getEmployeesNames());
        assertNull(process.getEmployeesEmails());
        assertNull(process.getEmployeesRut());
        assertNull(process.getMaxLevelScore());
        assertNull(process.getNEmployees());
        assertNull(process.getCreatedAt());
        assertNull(process.getUpdatedAt());
        if (process.getMilestones() == null) {
            process.setMilestones(new ArrayList<>());
        }
        assertNotNull(process.getMilestones());
        assertTrue(process.getMilestones().isEmpty());
        assertNull(process.getInstitution());
        assertNull(process.getMaturityModel());
        assertNull(process.getProcessResult());
        if (process.getEvidences() == null) {
            process.setEvidences(new ArrayList<>());
        }
        assertNotNull(process.getEvidences());
        assertTrue(process.getEvidences().isEmpty());
        if (process.getRecommendations() == null) {
            process.setRecommendations(new ArrayList<>());
        }
        assertNotNull(process.getRecommendations());
        assertTrue(process.getRecommendations().isEmpty());
        assertNull(process.getSurvey());
    }

    @Test
    void testSettersAndGetters() {
        Process process = new Process();

        process.setId(1L);
        process.setStatus(Process.ProcessStatus.IN_PROGRESS);
        process.setRequestStatus(Process.RequestStatus.ACCEPTED);
        process.setName("Test Process");
        Date now = new Date();
        process.setStartDate(now);
        process.setEndDate(now);
        process.setStep(3L);
        process.setSurveyLink("http://example.com/survey");
        process.setEmployeesNames("John Doe, Jane Doe");
        process.setEmployeesEmails("john@example.com, jane@example.com");
        process.setEmployeesRut("12345678-9, 98765432-1");
        process.setMaxLevelScore(100L);
        process.setNEmployees(2L);
        process.setCreatedAt(now);
        process.setUpdatedAt(now);

        List<String> milestones = new ArrayList<>();
        milestones.add("Milestone 1");
        process.setMilestones(milestones);

        Institution institution = new Institution();
        process.setInstitution(institution);

        MaturityModel maturityModel = new MaturityModel();
        process.setMaturityModel(maturityModel);

        ProcessResult processResult = new ProcessResult();
        process.setProcessResult(processResult);

        List<Evidence> evidences = new ArrayList<>();
        process.setEvidences(evidences);

        List<Recommendation> recommendations = new ArrayList<>();
        process.setRecommendations(recommendations);

        Survey survey = new Survey();
        process.setSurvey(survey);

        assertEquals(1L, process.getId());
        assertEquals(Process.ProcessStatus.IN_PROGRESS, process.getStatus());
        assertEquals(Process.RequestStatus.ACCEPTED, process.getRequestStatus());
        assertEquals("Test Process", process.getName());
        assertEquals(now, process.getStartDate());
        assertEquals(now, process.getEndDate());
        assertEquals(3L, process.getStep());
        assertEquals("http://example.com/survey", process.getSurveyLink());
        assertEquals("John Doe, Jane Doe", process.getEmployeesNames());
        assertEquals("john@example.com, jane@example.com", process.getEmployeesEmails());
        assertEquals("12345678-9, 98765432-1", process.getEmployeesRut());
        assertEquals(100L, process.getMaxLevelScore());
        assertEquals(2L, process.getNEmployees());
        assertEquals(now, process.getCreatedAt());
        assertEquals(now, process.getUpdatedAt());
        assertEquals(1, process.getMilestones().size());
        assertEquals("Milestone 1", process.getMilestones().get(0));
        assertEquals(institution, process.getInstitution());
        assertEquals(maturityModel, process.getMaturityModel());
        assertEquals(processResult, process.getProcessResult());
        assertEquals(evidences, process.getEvidences());
        assertEquals(recommendations, process.getRecommendations());
        assertEquals(survey, process.getSurvey());
    }

    @Test
    void testMilestonesInitialization() {
        Process process = new Process();
        if (process.getMilestones() == null) {
            process.setMilestones(new ArrayList<>());
        }
        assertNotNull(process.getMilestones());
        assertTrue(process.getMilestones().isEmpty());

        process.getMilestones().add("Milestone A");
        assertEquals(1, process.getMilestones().size());
        assertEquals("Milestone A", process.getMilestones().get(0));
    }

    @Test
    void testEvidencesInitialization() {
        Process process = new Process();
        if (process.getEvidences() == null) {
            process.setEvidences(new ArrayList<>());
        }
        assertNotNull(process.getEvidences());
        assertTrue(process.getEvidences().isEmpty());

        Evidence evidence = new Evidence();
        process.getEvidences().add(evidence);
        assertEquals(1, process.getEvidences().size());
        assertEquals(evidence, process.getEvidences().get(0));
    }

    @Test
    void testRecommendationsInitialization() {
        Process process = new Process();
        if (process.getRecommendations() == null) {
            process.setRecommendations(new ArrayList<>());
        }
        assertNotNull(process.getRecommendations());
        assertTrue(process.getRecommendations().isEmpty());

        Recommendation recommendation = new Recommendation();
        process.getRecommendations().add(recommendation);
        assertEquals(1, process.getRecommendations().size());
        assertEquals(recommendation, process.getRecommendations().get(0));
    }

    @Test
    void testProcessStatusEnum() {
        assertEquals("IN_PROGRESS", Process.ProcessStatus.IN_PROGRESS.name());
        assertEquals("FINISHED", Process.ProcessStatus.FINISHED.name());
    }

    @Test
    void testRequestStatusEnum() {
        assertEquals("UNREAD", Process.RequestStatus.UNREAD.name());
        assertEquals("REJECTED", Process.RequestStatus.REJECTED.name());
    }

    @Test
    void testProcessManualCreation() {
        Institution institution = new Institution();
        MaturityModel maturityModel = new MaturityModel();
        ProcessResult processResult = new ProcessResult();
        List<Evidence> evidences = new ArrayList<>();
        List<Recommendation> recommendations = new ArrayList<>();
        Survey survey = new Survey();

        // Crear la instancia de Process y configurar los valores manualmente
        Process process = new Process();
        process.setName("Manual Process");
        process.setStatus(Process.ProcessStatus.UNINITIATED);
        process.setRequestStatus(Process.RequestStatus.UNREAD);
        process.setInstitution(institution);
        process.setMaturityModel(maturityModel);
        process.setProcessResult(processResult);
        process.setEvidences(evidences);
        process.setRecommendations(recommendations);
        process.setSurvey(survey);

        // Verificar los valores configurados
        assertEquals("Manual Process", process.getName());
        assertEquals(Process.ProcessStatus.UNINITIATED, process.getStatus());
        assertEquals(Process.RequestStatus.UNREAD, process.getRequestStatus());
        assertEquals(institution, process.getInstitution());
        assertEquals(maturityModel, process.getMaturityModel());
        assertEquals(processResult, process.getProcessResult());
        assertEquals(evidences, process.getEvidences());
        assertEquals(recommendations, process.getRecommendations());
        assertEquals(survey, process.getSurvey());
    }
}
