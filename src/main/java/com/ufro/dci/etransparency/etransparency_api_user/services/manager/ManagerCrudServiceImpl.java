package com.ufro.dci.etransparency.etransparency_api_user.services.manager;

import java.util.*;

import lombok.*;

import org.springframework.cache.annotation.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ufro.dci.etransparency.etransparency_api_user.dtos.institution.InstitutionDTO;
import com.ufro.dci.etransparency.etransparency_api_user.dtos.manager.ManagerDTO;
import com.ufro.dci.etransparency.etransparency_api_user.dtos.manager.ManagerUpdateDTO;
import com.ufro.dci.etransparency.etransparency_api_user.exceptions.custom.ResourceNotFoundException;
import com.ufro.dci.etransparency.etransparency_api_user.models.UserEntity.UserRole;
import com.ufro.dci.etransparency.etransparency_api_user.models.manager.Manager;
import com.ufro.dci.etransparency.etransparency_api_user.services.manager.utils.ManagerCommonsUtils;
import com.ufro.dci.etransparency.etransparency_api_user.utils.ConversionUtils;

/**
 * Implementación del servicio para la gestión CRUD de gestores.
 * <p>
 * Esta clase proporciona métodos para crear, obtener, actualizar y cambiar el
 * estado de los gestores.
 * Utiliza almacenamiento en caché para optimizar el rendimiento y la
 * recuperación de datos.
 * </p>
 * 
 * @author Adolfo Plaza
 * @version 1.0
 * @since 1.0
 */
@Service
@RequiredArgsConstructor
@CacheConfig(cacheNames = "managers")
public class ManagerCrudServiceImpl implements ManagerCrudService {

    private final PasswordEncoder passwordEncoder;

    private final ManagerCommonsUtils managerCommonsUtils;

    /**
     * Obtiene un gestor por su identificador.
     * <p>
     * Este método busca un gestor activo en la base de datos. Si no se encuentra,
     * lanza una excepción {@link ResourceNotFoundException}.
     * Si el gestor tiene una institución asociada, también se convierte y se
     * agrega al {@link ManagerDTO}.
     * </p>
     *
     * @param managerId el identificador del gestor a buscar
     * @return el DTO del gestor correspondiente
     * @throws ResourceNotFoundException si no se encuentra el gestor o si está
     *                                   inactivo
     */
    @Override
    public ManagerDTO getManager(Long managerId) {
        Manager manager = managerCommonsUtils.findManagerById(managerId);
        ManagerDTO managerDTO = ConversionUtils.convertToDTO(manager, ManagerDTO.class);

        if (manager.getInstitution() != null) {
            InstitutionDTO institutionDTO = ConversionUtils.convertToDTO(manager.getInstitution(),
                    InstitutionDTO.class);
            managerDTO.setInstitution(institutionDTO);
        }

        return managerDTO;
    }

    /**
     * Crea un nuevo gestor.
     * <p>
     * Este método codifica la contraseña del gestor y establece su rol antes de
     * guardarlo en la base de datos.
     * Luego, devuelve el {@link ManagerDTO} del gestor creado.
     * </p>
     *
     * @param managerDTO el DTO del gestor a crear
     * @return el DTO del gestor creado
     */
    @Override
    @CacheEvict(allEntries = true)
    public ManagerDTO createManager(ManagerDTO managerDTO) {
        Manager manager = ConversionUtils.convertToEntity(managerDTO, Manager.class);
        manager.setPassword(passwordEncoder.encode(managerDTO.getPassword()));
        manager.setRole(UserRole.MANAGER);
        manager.setNotifications(new ArrayList<>());
        return managerCommonsUtils.saveAndConvertToDTO(manager);
    }

    /**
     * Actualiza un gestor existente.
     * <p>
     * Este método busca el gestor por su identificador. Si se encuentra y está
     * activo,
     * actualiza sus propiedades según el {@link ManagerUpdateDTO} proporcionado.
     * Si se proporciona una nueva contraseña, también se codifica antes de guardar.
     * </p>
     *
     * @param managerId        el identificador del gestor a actualizar
     * @param managerUpdateDTO el DTO con los nuevos datos del gestor
     * @return el DTO del gestor actualizado
     * @throws ResourceNotFoundException si no se encuentra el gestor o si está
     *                                   inactivo
     */
    @Override
    @Transactional
    @CacheEvict(key = "#managerId", allEntries = true)
    public ManagerDTO updateManager(Long managerId, ManagerUpdateDTO managerUpdateDTO) {
        Manager manager = managerCommonsUtils.findManagerById(managerId);

        if (managerUpdateDTO.getPassword() != null && !managerUpdateDTO.getPassword().isEmpty()) {
            managerUpdateDTO.setPassword(passwordEncoder.encode(managerUpdateDTO.getPassword()));
        }

        ConversionUtils.copyNonNullProperties(managerUpdateDTO, manager);
        return managerCommonsUtils.saveAndConvertToDTO(manager);
    }

    /**
     * Cambia el estado de un gestor (activo/inactivo).
     * <p>
     * Este método busca el gestor por su identificador. Si se encuentra, cambia su
     * estado
     * y lo guarda en la base de datos, devolviendo el DTO correspondiente.
     * </p>
     *
     * @param managerId el identificador del gestor cuyo estado se cambiará
     * @return el DTO del gestor con el nuevo estado
     * @throws ResourceNotFoundException si no se encuentra el gestor o si está
     *                                   inactivo
     */
    @Override
    @Transactional
    @CacheEvict(key = "#managerId", allEntries = true)
    public ManagerDTO toggleManagerStatus(Long managerId) {
        Manager manager = managerCommonsUtils.findManagerById(managerId);
        manager.setActive(!manager.isActive());
        return managerCommonsUtils.saveAndConvertToDTO(manager);
    }

}
