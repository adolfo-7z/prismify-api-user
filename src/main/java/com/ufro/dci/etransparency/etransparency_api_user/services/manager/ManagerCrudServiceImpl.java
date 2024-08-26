package com.ufro.dci.etransparency.etransparency_api_user.services.manager;

import static com.ufro.dci.etransparency.etransparency_api_user.utils.Constants.*;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.ufro.dci.etransparency.etransparency_api_user.dtos.institution.InstitutionDTO;
import com.ufro.dci.etransparency.etransparency_api_user.dtos.manager.ManagerDTO;
import com.ufro.dci.etransparency.etransparency_api_user.dtos.manager.ManagerUpdateDTO;
import com.ufro.dci.etransparency.etransparency_api_user.exceptions.custom.ResourceNotFoundException;
import com.ufro.dci.etransparency.etransparency_api_user.models.UserEntity.UserRole;
import com.ufro.dci.etransparency.etransparency_api_user.models.manager.Manager;
import com.ufro.dci.etransparency.etransparency_api_user.repositories.manager.ManagerRepository;
import com.ufro.dci.etransparency.etransparency_api_user.utils.ConversionUtils;
import lombok.RequiredArgsConstructor;

/**
 * Servicio para la gestión de Gestores Institucionales.
 */
@Service
@RequiredArgsConstructor
public class ManagerCrudServiceImpl implements ManagerCrudService {

    private final ManagerRepository managerRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * Obtiene un gestor por su ID.
     *
     * @param managerId ID del gestor.
     * @return DTO del gestor.
     * @throws ResourceNotFoundException si el gestor no es encontrado o está
     *                                   inactivo.
     */
    @Override
    public ManagerDTO getManager(Long managerId) {
        Manager manager = managerRepository.findById(managerId)
                .filter(Manager::isActive)
                .orElseThrow(() -> new ResourceNotFoundException(NOT_FOUND,
                        THE_MANAGER_WITH_ID + managerId + WAS_NOT_FOUND_OR_INACTIVE));

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
     *
     * @param managerDTO DTO del gestor a crear.
     * @return DTO del gestor creado.
     */
    @Override
    public ManagerDTO createManager(ManagerDTO managerDTO) {
        managerDTO.setPassword(passwordEncoder.encode(managerDTO.getPassword()));
        managerDTO.setRole(UserRole.MANAGER);
        Manager manager = ConversionUtils.convertToEntity(managerDTO, Manager.class);
        Manager savedManager = managerRepository.save(manager);
        return ConversionUtils.convertToDTO(savedManager, ManagerDTO.class);
    }

    /**
     * Actualiza un gestor existente.
     *
     * @param managerId        ID del gestor a actualizar.
     * @param managerUpdateDTO DTO con los datos a actualizar.
     * @return DTO del gestor actualizado.
     * @throws ResourceNotFoundException si el gestor no es encontrado o está
     *                                   inactivo.
     */
    @Override
    @Transactional
    public ManagerDTO updateManager(Long managerId, ManagerUpdateDTO managerUpdateDTO) {
        Manager manager = managerRepository.findById(managerId)
                .filter(Manager::isActive)
                .orElseThrow(() -> new ResourceNotFoundException(NOT_FOUND,
                        THE_MANAGER_WITH_ID + managerId + WAS_NOT_FOUND_OR_INACTIVE));

        if (managerUpdateDTO.getPassword() != null && !managerUpdateDTO.getPassword().isEmpty()) {
            managerUpdateDTO.setPassword(passwordEncoder.encode(managerUpdateDTO.getPassword()));
        }

        ConversionUtils.copyNonNullProperties(managerUpdateDTO, manager);
        Manager savedManager = managerRepository.save(manager);
        return ConversionUtils.convertToDTO(savedManager, ManagerDTO.class);
    }

    /**
     * Alterna el estado de actividad de un gestor.
     *
     * @param managerId ID del gestor.
     * @return DTO del gestor con el estado actualizado.
     * @throws ResourceNotFoundException si el gestor no es encontrado.
     */
    @Override
    @Transactional
    public ManagerDTO toggleManagerStatus(Long managerId) {
        return managerRepository.findById(managerId)
                .map(existingInstitution -> {
                    existingInstitution.setActive(!existingInstitution.isActive());
                    Manager savedInstitution = managerRepository.save(existingInstitution);
                    return ConversionUtils.convertToDTO(savedInstitution, ManagerDTO.class);
                })
                .orElseThrow(() -> new ResourceNotFoundException(NOT_FOUND,
                        THE_MANAGER_WITH_ID + managerId + WAS_NOT_FOUND_OR_INACTIVE));
    }

}
