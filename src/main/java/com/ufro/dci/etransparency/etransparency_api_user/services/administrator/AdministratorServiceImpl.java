package com.ufro.dci.etransparency.etransparency_api_user.services.administrator;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import com.ufro.dci.etransparency.etransparency_api_user.dtos.administrator.AdministratorDTO;
import com.ufro.dci.etransparency.etransparency_api_user.models.administrator.Administrator;
import com.ufro.dci.etransparency.etransparency_api_user.models.institution.Institution;
import com.ufro.dci.etransparency.etransparency_api_user.models.process.Process;
import com.ufro.dci.etransparency.etransparency_api_user.repositories.administrator.AdministratorRepository;
import com.ufro.dci.etransparency.etransparency_api_user.repositories.auditor.AuditorRepository;
import com.ufro.dci.etransparency.etransparency_api_user.repositories.institution.InstitutionRepository;
import com.ufro.dci.etransparency.etransparency_api_user.repositories.process.ProcessRepository;
import com.ufro.dci.etransparency.etransparency_api_user.utils.ConversionUtils;
import lombok.RequiredArgsConstructor;

/**
 * Servicio para la gestión de Administradores.
 */
@Service
@RequiredArgsConstructor
public class AdministratorServiceImpl implements AdministratorService {

    private final AdministratorRepository administratorRepository;
    private final InstitutionRepository institutionRepository;
    private final AuditorRepository auditorRepository;
    private final ProcessRepository processRepository;

    /**
     * Obtiene una lista paginada de todos los administradores.
     *
     * @param page el número de página a obtener.
     * @param size el tamaño de la página.
     * @return un objeto que contiene la lista de administradores y el número total
     *         de páginas.
     */
    @Override
    public Object getAllAdministrators(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Administrator> adminPage = administratorRepository.findAll(pageable);
        Map<String, Object> response = new HashMap<>();
        response.put("administrators", adminPage.stream()
                .filter(Administrator::isActive)
                .map(administrator -> ConversionUtils.convertToDTO(administrator, AdministratorDTO.class))
                .collect(Collectors.toList()));
        response.put("totalPages", adminPage.getTotalPages());
        return response;
    }

    /**
     * Obtiene los datos del dashboard del administrador.
     *
     * @return un objeto que contiene diversos datos estadísticos para el dashboard.
     */
    @Override
    public Object getAdminDashboard() {
        Map<String, Object> response = new HashMap<>();
        Long institutionsCount = institutionRepository.findAll().stream().count();
        Long auditorsCount = auditorRepository.findAll().stream().count();
        Long processesInProgress = processRepository.countByStatus(Process.ProcessStatus.IN_PROGRESS);
        Long totalCompletedAudits = processRepository.countByStatus(Process.ProcessStatus.FINISHED);
        Long peopleSurveyed = institutionRepository.findAll().stream().mapToLong(Institution::getNEmployees).sum();
        response.put("numberOfInstitutions", institutionsCount);
        response.put("processesInProgress", processesInProgress);
        response.put("numberOfAuditors", auditorsCount);
        response.put("totalCompletedAudits", totalCompletedAudits);
        response.put("peopleSurveyed", peopleSurveyed);
        return response;
    }

}
