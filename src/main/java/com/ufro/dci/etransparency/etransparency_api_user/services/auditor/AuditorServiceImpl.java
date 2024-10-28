package com.ufro.dci.etransparency.etransparency_api_user.services.auditor;

import java.util.*;

import lombok.*;

import java.util.stream.Collectors;

import org.springframework.cache.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.ufro.dci.etransparency.etransparency_api_user.dtos.auditor.AuditorDTO;
import com.ufro.dci.etransparency.etransparency_api_user.models.auditor.Auditor;
import com.ufro.dci.etransparency.etransparency_api_user.repositories.auditor.AuditorRepository;
import com.ufro.dci.etransparency.etransparency_api_user.utils.ConversionUtils;

/**
 * Servicio para la gestión de auditores en la aplicación.
 * <p>
 * Esta clase implementa el servicio {@link AuditorService} y proporciona
 * métodos
 * para acceder a la información de los auditores, incluyendo funcionalidades de
 * paginación y filtrado.
 * </p>
 * 
 * @author Adolfo Plaza
 * @version 1.0
 * @since 1.0
 */
@Service
@RequiredArgsConstructor
@CacheConfig(cacheNames = "auditors")
public class AuditorServiceImpl implements AuditorService {

    private final AuditorRepository auditorRepository;

    /**
     * Recupera todos los auditores con opciones de paginación, ordenamiento y
     * filtrado por nombre.
     * <p>
     * Si se proporciona un nombre, se filtran los auditores cuyos nombres contienen
     * esa cadena. De lo contrario, se devuelven todos los auditores.
     * Solo se incluyen auditores activos en la respuesta.
     * </p>
     *
     * @param page          el número de página a recuperar (0 para la primera
     *                      página)
     * @param size          el tamaño de la página (número de auditores por página)
     * @param sortDirection la dirección de ordenamiento, puede ser "asc" para
     *                      ascendente o "desc" para descendente
     * @param name          el nombre para filtrar auditores (puede ser nulo o
     *                      vacío)
     * @return un objeto que contiene la lista de auditores activos y el número
     *         total de páginas
     */
    @Override
    @Cacheable(key = "'allAuditors_' + #page + '-' + #size + '-' + #sortDirection + '-' + #name")
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

}
