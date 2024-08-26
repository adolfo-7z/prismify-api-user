package com.ufro.dci.etransparency.etransparency_api_user.services.administrator;

import static com.ufro.dci.etransparency.etransparency_api_user.utils.Constants.*;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.ufro.dci.etransparency.etransparency_api_user.dtos.administrator.AdministratorDTO;
import com.ufro.dci.etransparency.etransparency_api_user.dtos.administrator.AdministratorUpdateDTO;
import com.ufro.dci.etransparency.etransparency_api_user.exceptions.custom.ResourceNotFoundException;
import com.ufro.dci.etransparency.etransparency_api_user.models.UserEntity.UserRole;
import com.ufro.dci.etransparency.etransparency_api_user.models.administrator.Administrator;
import com.ufro.dci.etransparency.etransparency_api_user.repositories.administrator.AdministratorRepository;
import com.ufro.dci.etransparency.etransparency_api_user.utils.ConversionUtils;

import lombok.RequiredArgsConstructor;

/**
 * Servicio para la gestión de Administradores.
 */
@Service
@RequiredArgsConstructor
public class AdministratorCrudServiceImpl implements AdministratorCrudService {

    private final AdministratorRepository administratorRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * Obtiene un administrador por su ID.
     *
     * @param adminId ID del administrador.
     * @return DTO del administrador.
     * @throws ResourceNotFoundException si el administrador no es encontrado o está
     *                                   inactivo.
     */
    @Override
    public AdministratorDTO getAdministrator(Long adminId) {
        return administratorRepository.findById(adminId)
                .filter(Administrator::isActive)
                .map(administrator -> ConversionUtils.convertToDTO(administrator, AdministratorDTO.class))
                .orElseThrow(() -> new ResourceNotFoundException(NOT_FOUND,
                        +adminId + WAS_NOT_FOUND_OR_INACTIVE));
    }

    /**
     * Crea un nuevo administrador.
     *
     * @param administratorDTO DTO del administrador a crear.
     * @return DTO del administrador creado.
     */
    @Override
    public AdministratorDTO createAdministrator(AdministratorDTO administratorDTO) {
        administratorDTO.setPassword(passwordEncoder.encode(administratorDTO.getPassword()));
        administratorDTO.setRole(UserRole.ADMIN);
        Administrator administrator = ConversionUtils.convertToEntity(administratorDTO, Administrator.class);
        Administrator savedAdministrator = administratorRepository.save(administrator);
        return ConversionUtils.convertToDTO(savedAdministrator, AdministratorDTO.class);
    }

    /**
     * Actualiza un administrador existente.
     *
     * @param adminId                ID del administrador a actualizar.
     * @param administratorUpdateDTO DTO con los datos a actualizar.
     * @return DTO del administrador actualizado.
     * @throws ResourceNotFoundException si el administrador no es encontrado o está
     *                                   inactivo.
     */
    @Override
    @Transactional
    public AdministratorDTO updateAdministrator(Long adminId, AdministratorUpdateDTO administratorUpdateDTO) {
        Administrator administrator = administratorRepository.findById(adminId)
                .filter(Administrator::isActive)
                .orElseThrow(() -> new ResourceNotFoundException(NOT_FOUND,
                        THE_ADMIN_WITH_ID + adminId + WAS_NOT_FOUND_OR_INACTIVE));
        if (administratorUpdateDTO.getPassword() != null && !administratorUpdateDTO.getPassword().isEmpty()) {
            administratorUpdateDTO.setPassword(passwordEncoder.encode(administratorUpdateDTO.getPassword()));
        }
        ConversionUtils.copyNonNullProperties(administratorUpdateDTO, administrator);
        Administrator savedAdministrator = administratorRepository.save(administrator);
        return ConversionUtils.convertToDTO(savedAdministrator, AdministratorDTO.class);
    }

    /**
     * Alterna el estado de actividad de un administrador.
     *
     * @param adminId ID del administrador.
     * @return DTO del administrador con el estado actualizado.
     * @throws ResourceNotFoundException si el administrador no es encontrado.
     */
    @Override
    @Transactional
    public AdministratorDTO toggleAdministratorStatus(Long adminId) {
        return administratorRepository.findById(adminId)
                .map(existingAdministrator -> {
                    existingAdministrator.setActive(!existingAdministrator.isActive());
                    Administrator savedAdministrator = administratorRepository.save(existingAdministrator);
                    return ConversionUtils.convertToDTO(savedAdministrator, AdministratorDTO.class);
                })
                .orElseThrow(() -> new ResourceNotFoundException(NOT_FOUND,
                        THE_ADMIN_WITH_ID + adminId + WAS_NOT_FOUND_OR_INACTIVE));
    }

}
