package com.ufro.dci.etransparency.etransparency_api_user.models.institution;

import com.ufro.dci.etransparency.etransparency_api_user.models.auditor.Auditor;
import com.ufro.dci.etransparency.etransparency_api_user.models.manager.Manager;
import com.ufro.dci.etransparency.etransparency_api_user.models.process.Process;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class InstitutionTest {

    @Test
    void testInstitutionConstructor() {
        Institution institution = new Institution();
        assertNull(institution.getId());
        assertFalse(institution.isActive());
        assertNull(institution.getAddress());
        assertNull(institution.getCity());
        assertNull(institution.getName());
        assertNull(institution.getRegion());
        assertNull(institution.getPhoneNumber());
        assertNull(institution.getWhatsapp());
        assertNull(institution.getNEmployees());
        assertNull(institution.getColor());
        assertNull(institution.getAcronym());
        assertNull(institution.getLevel());
        assertNull(institution.getWebsite());
        assertNull(institution.getCompanyRut());
        assertNull(institution.getCompanyName());
        assertNull(institution.getNProcesses());
        assertNull(institution.getCreatedAt());
        assertNull(institution.getUpdatedAt());
        assertNull(institution.getManager());
        assertNull(institution.getAuditor());

        if (institution.getProcesses() == null) {
            institution.setProcesses(new ArrayList<>());
        }
        assertNotNull(institution.getProcesses());
        assertTrue(institution.getProcesses().isEmpty());
    }

    @Test
    void testSettersAndGetters() {
        Institution institution = new Institution();

        institution.setActive(true);
        institution.setAddress("123 Main St");
        institution.setCity("CityTest");
        institution.setName("InstitutionTest");
        institution.setRegion("RegionTest");
        institution.setPhoneNumber("123456789");
        institution.setWhatsapp("987654321");
        institution.setNEmployees(100L);
        institution.setColor("blue");
        institution.setAcronym("INST");
        institution.setLevel(3L);
        institution.setWebsite("www.institutiontest.com");
        institution.setCompanyRut("123456789-0");
        institution.setCompanyName("CompanyTest");
        institution.setNProcesses(5L);
        institution.setCreatedAt(new Date());
        institution.setUpdatedAt(new Date());

        assertTrue(institution.isActive());
        assertEquals("123 Main St", institution.getAddress());
        assertEquals("CityTest", institution.getCity());
        assertEquals("InstitutionTest", institution.getName());
        assertEquals("RegionTest", institution.getRegion());
        assertEquals("123456789", institution.getPhoneNumber());
        assertEquals("987654321", institution.getWhatsapp());
        assertEquals(100L, institution.getNEmployees());
        assertEquals("blue", institution.getColor());
        assertEquals("INST", institution.getAcronym());
        assertEquals(3L, institution.getLevel());
        assertEquals("www.institutiontest.com", institution.getWebsite());
        assertEquals("123456789-0", institution.getCompanyRut());
        assertEquals("CompanyTest", institution.getCompanyName());
        assertEquals(5L, institution.getNProcesses());
        assertNotNull(institution.getCreatedAt());
        assertNotNull(institution.getUpdatedAt());
    }

    @Test
    void testManagerRelation() {
        Institution institution = new Institution();
        Manager manager = new Manager();
        institution.setManager(manager);

        assertNotNull(institution.getManager());
        assertEquals(manager, institution.getManager());
    }

    @Test
    void testAuditorRelation() {
        Institution institution = new Institution();
        Auditor auditor = new Auditor();
        institution.setAuditor(auditor);

        assertNotNull(institution.getAuditor());
        assertEquals(auditor, institution.getAuditor());
    }

    @Test
    void testProcessesRelation() {
        Institution institution = new Institution();
        List<Process> processes = new ArrayList<>();
        Process process1 = new Process();
        Process process2 = new Process();
        processes.add(process1);
        processes.add(process2);
        institution.setProcesses(processes);

        assertNotNull(institution.getProcesses());
        assertEquals(2, institution.getProcesses().size());
        assertTrue(institution.getProcesses().contains(process1));
        assertTrue(institution.getProcesses().contains(process2));
    }

    @Test
    void testToString() {
        Institution institution = new Institution();
        institution.setName("InstitutionTest");
        institution.setCity("CityTest");
        institution.setRegion("RegionTest");
        institution.setCompanyRut("123456789-0");

        String toStringOutput = institution.toString();
        assertTrue(toStringOutput.contains("InstitutionTest"));
        assertTrue(toStringOutput.contains("CityTest"));
        assertTrue(toStringOutput.contains("RegionTest"));
        assertTrue(toStringOutput.contains("123456789-0"));
    }

    @Test
    void testSetAndRemoveProcesses() {
        Institution institution = new Institution();
        Process process1 = new Process();
        Process process2 = new Process();

        List<Process> processes = new ArrayList<>(List.of(process1, process2));
        institution.setProcesses(processes);

        assertEquals(2, institution.getProcesses().size());

        institution.getProcesses().remove(process1);
        assertEquals(1, institution.getProcesses().size());
        assertFalse(institution.getProcesses().contains(process1));
    }
}
