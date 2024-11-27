package com.ufro.dci.etransparency.etransparency_api_user.services.administrator;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.*;

import com.ufro.dci.etransparency.etransparency_api_user.dtos.administrator.AdministratorDTO;
import com.ufro.dci.etransparency.etransparency_api_user.dtos.dimension.DimensionAverageDTO;
import com.ufro.dci.etransparency.etransparency_api_user.exceptions.custom.ResourceNotFoundException;
import com.ufro.dci.etransparency.etransparency_api_user.models.administrator.Administrator;
import com.ufro.dci.etransparency.etransparency_api_user.models.institution.Institution;
import com.ufro.dci.etransparency.etransparency_api_user.models.process.Process;
import com.ufro.dci.etransparency.etransparency_api_user.models.result.SystemResult;
import com.ufro.dci.etransparency.etransparency_api_user.repositories.administrator.AdministratorRepository;
import com.ufro.dci.etransparency.etransparency_api_user.repositories.auditor.AuditorRepository;
import com.ufro.dci.etransparency.etransparency_api_user.repositories.institution.InstitutionRepository;
import com.ufro.dci.etransparency.etransparency_api_user.repositories.process.ProcessRepository;
import com.ufro.dci.etransparency.etransparency_api_user.repositories.result.SystemResultRepository;
import com.ufro.dci.etransparency.etransparency_api_user.utils.ConversionUtils;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

class AdministratorServiceImplTest {

    @Mock
    private AdministratorRepository administratorRepository;

    @Mock
    private InstitutionRepository institutionRepository;

    @Mock
    private AuditorRepository auditorRepository;

    @Mock
    private ProcessRepository processRepository;

    @Mock
    private SystemResultRepository systemResultRepository;

    @InjectMocks
    private AdministratorServiceImpl service;

    private Administrator administrator;

    private Institution institution;

    private SystemResult systemResult;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        administrator = new Administrator();
        administrator.setId(1L);
        administrator.setActive(true);
        administrator.setUsername("adminUser");
        administrator.setEmail("admin@example.com");

        institution = new Institution();
        institution.setId(1L);
        institution.setName("Test Institution");
        institution.setNEmployees(50L);

        systemResult = new SystemResult();
        systemResult.setDimensions(List.of("Dimension 1", "Dimension 2"));
        systemResult.setDimensionsAverage(List.of(3.5, 4.0));
    }

    @Test
    void testGetAllAdministratorsSuccess() {
        Page<Administrator> adminPage = new PageImpl<>(List.of(administrator));
        when(administratorRepository.findAll(PageRequest.of(0, 10))).thenReturn(adminPage);

        try (MockedStatic<ConversionUtils> mockedStatic = mockStatic(ConversionUtils.class)) {
            mockedStatic.when(() -> ConversionUtils.convertToDTO(administrator, AdministratorDTO.class))
                    .thenReturn(new AdministratorDTO());

            Map<String, Object> result = (Map<String, Object>) service.getAllAdministrators(0, 10);

            assertNotNull(result);
            assertEquals(1, ((List<?>) result.get("administrators")).size());
            assertEquals(1, result.get("totalPages"));
        }
    }

    @Test
    void testGetAdminDashboardSuccess() {
        when(institutionRepository.findAll()).thenReturn(List.of(institution));
        when(processRepository.countByStatus(Process.ProcessStatus.IN_PROGRESS)).thenReturn(5L);
        when(processRepository.countByStatus(Process.ProcessStatus.FINISHED)).thenReturn(3L);
        when(systemResultRepository.findById(1L)).thenReturn(Optional.of(systemResult));

        Map<String, Object> result = (Map<String, Object>) service.getAdminDashboard();

        assertNotNull(result);
        assertEquals(1L, result.get("numberOfInstitutions"));
        assertEquals(5L, result.get("processesInProgress"));
        assertEquals(3L, result.get("totalCompletedAudits"));
        assertEquals(50L, result.get("peopleSurveyed"));

        List<DimensionAverageDTO> dimensions = (List<DimensionAverageDTO>) result.get("allDimensionLevels");
        assertEquals(2, dimensions.size());
        assertEquals("Dimension 1", dimensions.get(0).getName());
        assertEquals(3.5, dimensions.get(0).getAverage());
    }

    @Test
    void testGetAdminDashboardSystemResultNotFound() {
        when(institutionRepository.findAll()).thenReturn(List.of(institution));
        when(systemResultRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> service.getAdminDashboard());
    }
}
