package com.ufro.dci.etransparency.etransparency_api_user.services.manager;

import org.springframework.cache.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.ufro.dci.etransparency.etransparency_api_user.dtos.manager.ManagerDTO;
import com.ufro.dci.etransparency.etransparency_api_user.models.manager.Manager;
import com.ufro.dci.etransparency.etransparency_api_user.repositories.manager.ManagerRepository;
import com.ufro.dci.etransparency.etransparency_api_user.utils.ConversionUtils;
import lombok.RequiredArgsConstructor;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Implementación del servicio para la gestión de Managers.
 */
@Service
@RequiredArgsConstructor
@CacheConfig(cacheNames = "managers")
public class ManagerServiceImpl implements ManagerService {

    private final ManagerRepository managerRepository;

    /**
     * Obtiene una lista paginada de todos los managers activos.
     *
     * @param page Número de página (empezando desde 0).
     * @param size Tamaño de la página (número de managers por página).
     * @return Un mapa con la lista de managers activos y el número total de
     *         páginas.
     */
    @Override
    @Cacheable(key = "'allManagers_' + #page + '-' + #size")
    public Object getAllManagers(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Manager> managerPage = managerRepository.findAll(pageable);
        Map<String, Object> response = new HashMap<>();
        response.put("managers", managerPage.stream()
                .filter(Manager::isActive)
                .map(manager -> ConversionUtils.convertToDTO(manager, ManagerDTO.class))
                .collect(Collectors.toList()));
        response.put("totalPages", managerPage.getTotalPages());
        return response;
    }

}
