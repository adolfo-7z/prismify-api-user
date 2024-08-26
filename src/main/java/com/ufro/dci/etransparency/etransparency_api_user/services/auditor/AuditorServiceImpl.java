package com.ufro.dci.etransparency.etransparency_api_user.services.auditor;

import java.util.*;
import java.util.stream.Collectors;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import com.ufro.dci.etransparency.etransparency_api_user.dtos.auditor.AuditorDTO;
import com.ufro.dci.etransparency.etransparency_api_user.models.auditor.Auditor;
import com.ufro.dci.etransparency.etransparency_api_user.repositories.auditor.AuditorRepository;
import com.ufro.dci.etransparency.etransparency_api_user.utils.ConversionUtils;

import lombok.RequiredArgsConstructor;

/**
 * Servicio para operaciones relacionadas con los auditores.
 */
@Service
@RequiredArgsConstructor
public class AuditorServiceImpl implements AuditorService {

    private final AuditorRepository auditorRepository;

    /**
     * Obtiene una lista paginada de todos los auditores activos.
     *
     * @param page Número de página.
     * @param size Tamaño de la página.
     * @return Un mapa que contiene la lista de auditores activos y el número total de páginas.
     */
    @Override
    public Object getAllAuditors(int page, int size, String sortDirection, String name) {
        Sort sort = sortDirection.equalsIgnoreCase("asc") ? Sort.by("updatedAt").ascending()
                                : Sort.by("updatedAt").descending();
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<Auditor> auditorPage;
        if (name != null && !name.isEmpty()) {
            auditorPage = auditorRepository.findByUsernameContainingIgnoreCase(name, pageable);
        } else {
            auditorPage = auditorRepository.findAll(pageable);
        }
        Map<String, Object> response = new HashMap<>();
        response.put("auditors", auditorPage.stream()
                .filter(Auditor::isActive)
                .map(auditor -> ConversionUtils.convertToDTO(auditor, AuditorDTO.class))
                .collect(Collectors.toList()));
        response.put("totalPages", auditorPage.getTotalPages());
        return response;
    }

    /**
     * Obtiene los resultados de una encuesta específica.
     *
     * @param processId ID del proceso de encuesta.
     * @return Resultados de la encuesta.
     */
    @Override
    public Object getSurveyResults(Long processId) {
        return null;
    }

}
