package com.ufro.dci.etransparency.etransparency_api_user.services.manager;

import lombok.RequiredArgsConstructor;

import java.util.*;

import java.util.stream.Collectors;

import org.springframework.cache.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ufro.dci.etransparency.etransparency_api_user.dtos.manager.ManagerDTO;
import com.ufro.dci.etransparency.etransparency_api_user.models.manager.Manager;
import com.ufro.dci.etransparency.etransparency_api_user.repositories.manager.ManagerRepository;
import com.ufro.dci.etransparency.etransparency_api_user.utils.ConversionUtils;

/**
 * Implementación del servicio para la gestión de gestores.
 * <p>
 * Esta clase proporciona métodos para recuperar información sobre
 * gestores
 * desde el repositorio correspondiente. Se utiliza la anotación
 * {@link Cacheable}
 * para almacenar en caché los resultados de las consultas.
 * </p>
 * 
 * @author Adolfo Plaza
 * @version 1.0
 * @since 1.0
 */
@Service
@RequiredArgsConstructor
@CacheConfig(cacheNames = "managers")
public class ManagerServiceImpl implements ManagerService {

    private final ManagerRepository managerRepository;

    /**
     * Recupera una lista paginada de gestores activos.
     * <p>
     * Este método consulta el repositorio para obtener una página de
     * gestores,
     * filtra aquellos que están activos y los convierte a objetos
     * {@link ManagerDTO}.
     * Los resultados se almacenan en caché para mejorar el rendimiento.
     * </p>
     *
     * @param page el número de página que se desea recuperar
     * @param size el tamaño de la página (número de gestores por página)
     * @return un objeto que contiene la lista de gestores activos y el total
     *         de páginas
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
