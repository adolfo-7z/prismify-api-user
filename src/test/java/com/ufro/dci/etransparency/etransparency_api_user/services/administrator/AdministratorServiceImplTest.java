package com.ufro.dci.etransparency.etransparency_api_user.services.administrator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.ufro.dci.etransparency.etransparency_api_user.models.administrator.Administrator;
import com.ufro.dci.etransparency.etransparency_api_user.models.institution.Institution;
import com.ufro.dci.etransparency.etransparency_api_user.models.process.Process;
import com.ufro.dci.etransparency.etransparency_api_user.repositories.administrator.AdministratorRepository;
import com.ufro.dci.etransparency.etransparency_api_user.repositories.auditor.AuditorRepository;
import com.ufro.dci.etransparency.etransparency_api_user.repositories.institution.InstitutionRepository;
import com.ufro.dci.etransparency.etransparency_api_user.repositories.process.ProcessRepository;
import com.ufro.dci.etransparency.etransparency_api_user.services.administrator.AdministratorServiceImpl;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AdministratorServiceImplTest {

    @Mock
    private AdministratorRepository administratorRepository;

    @Mock
    private InstitutionRepository institutionRepository;

    @Mock
    private AuditorRepository auditorRepository;

    @Mock
    private ProcessRepository processRepository;

    @InjectMocks
    private AdministratorServiceImpl administratorService;

    private Administrator administrator;
    private Institution institution;
    private Process process;

    @BeforeEach
    void setUp() {
        administrator = new Administrator();
        administrator.setActive(true);

        institution = new Institution();
        institution.setNEmployees(100L);

        process = new Process();
    }

    @Test
    void getAllAdministrators_ShouldReturnActiveAdministrators() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Administrator> adminPage = new PageImpl<>(List.of(administrator));
        when(administratorRepository.findAll(pageable)).thenReturn(adminPage);
        Map<String, Object> response = (Map<String, Object>) administratorService.getAllAdministrators(0, 10);

        assertNotNull(response);
        assertEquals(1, ((List<?>) response.get("administrators")).size());
        assertEquals(1, response.get("totalPages"));
        verify(administratorRepository, times(1)).findAll(pageable);
    }

    @Test
    void getAdminDashboard_ShouldReturnDashboardData() {
        when(institutionRepository.findAll()).thenReturn(Collections.singletonList(institution));
        when(auditorRepository.findAll()).thenReturn(Collections.emptyList());
        when(processRepository.countByStatus(Process.ProcessStatus.IN_PROGRESS)).thenReturn(5L);
        when(processRepository.countByStatus(Process.ProcessStatus.FINISHED)).thenReturn(10L);

        Map<String, Object> response = (Map<String, Object>) administratorService.getAdminDashboard();

        assertNotNull(response);
        assertEquals(1L, response.get("numberOfInstitutions"));
        assertEquals(0L, response.get("numberOfAuditors"));
        assertEquals(5L, response.get("processesInProgress"));
        assertEquals(10L, response.get("totalCompletedAudits"));
        assertEquals(100L, response.get("peopleSurveyed"));
        verify(institutionRepository, times(2)).findAll();
        verify(auditorRepository, times(1)).findAll();
        verify(processRepository, times(1)).countByStatus(Process.ProcessStatus.IN_PROGRESS);
        verify(processRepository, times(1)).countByStatus(Process.ProcessStatus.FINISHED);
    }
}
