package com.ufro.dci.etransparency.etransparency_api_user.services.administrator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

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
import com.ufro.dci.etransparency.etransparency_api_user.services.administrator.utils.AdminCommonsUtils;

import static com.ufro.dci.etransparency.etransparency_api_user.utils.Constants.*;
import com.ufro.dci.etransparency.etransparency_api_user.utils.ConversionUtils;

import lombok.RequiredArgsConstructor;

/**
 * Servicio para la gestión de administradores y el dashboard de administrador.
 * <p>
 * Esta clase implementa el servicio {@link AdministratorService} para obtener
 * información
 * sobre los administradores, así como para generar estadísticas y datos
 * relevantes para el
 * dashboard de un administrador.
 * </p>
 * 
 * @author Adolfo Plaza
 * @version 1.0
 * @since 1.0
 */
@Service
@RequiredArgsConstructor
public class AdministratorServiceImpl implements AdministratorService {

    private final AdministratorRepository administratorRepository;

    private final InstitutionRepository institutionRepository;

    private final AuditorRepository auditorRepository;

    private final ProcessRepository processRepository;

    private final SystemResultRepository systemResultRepository;

    private final AdminCommonsUtils adminCommonsUtils;

    /**
     * Obtiene una lista paginada de administradores activos.
     * <p>
     * Este método retorna una lista de administradores activos con información
     * paginada y también el número total de páginas.
     * </p>
     * 
     * @param page el número de la página solicitada
     * @param size el tamaño de cada página
     * @return un objeto con la lista de administradores activos y el total de
     *         páginas
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
     * Genera los datos para el dashboard del administrador.
     * <p>
     * Este método retorna información relevante como el número de instituciones,
     * procesos en curso, auditorías completadas, y el total de empleados
     * encuestados.
     * </p>
     * 
     * @return un objeto con los datos relevantes para el dashboard del
     *         administrador
     */
    @Override
    public Object getAdminDashboard() {
        Map<String, Object> response = new HashMap<>();
        Long institutionsCount = institutionRepository.findAll().stream().count();
        Long auditorsCount = auditorRepository.findAll().stream().count();
        Long processesInProgress = processRepository.countByStatus(Process.ProcessStatus.IN_PROGRESS);
        Long totalCompletedAudits = processRepository.countByStatus(Process.ProcessStatus.FINISHED);
        Long peopleSurveyed = institutionRepository.findAll().stream().mapToLong(Institution::getNEmployees).sum();

        SystemResult systemResult = systemResultRepository.findTopByOrderByIdDesc()
                .orElseThrow(() -> new ResourceNotFoundException(NOT_FOUND,
                        "System results were not found"));
        List<String> dimensionNames = systemResult.getDimensions();

        List<DimensionAverageDTO> dimensionAverageDTOs = new ArrayList<>();

        for (String dimensionName : dimensionNames) {
            DimensionAverageDTO dimensionAverageDTO = new DimensionAverageDTO();
            dimensionAverageDTO.setName(dimensionName);
            if (systemResult.getDimensionsAverage().isEmpty()) {
                dimensionAverageDTO.setAverage(0D);
            } else {
                dimensionAverageDTO
                        .setAverage(systemResult.getDimensionsAverage().get(dimensionNames.indexOf(dimensionName)));
            }
            dimensionAverageDTOs.add(dimensionAverageDTO);
        }

        response.put("numberOfInstitutions", institutionsCount);
        response.put("processesInProgress", processesInProgress);
        response.put("numberOfAuditors", auditorsCount);
        response.put("totalCompletedAudits", totalCompletedAudits);
        response.put("peopleSurveyed", peopleSurveyed);
        response.put("allDimensionLevels", dimensionAverageDTOs);
        response.put("top", adminCommonsUtils.admingetTopInstitutions());
        return response;
    }

}
