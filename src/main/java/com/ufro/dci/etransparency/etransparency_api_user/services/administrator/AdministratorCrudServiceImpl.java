package com.ufro.dci.etransparency.etransparency_api_user.services.administrator;

import static com.ufro.dci.etransparency.etransparency_api_user.utils.Constants.*;

import java.util.ArrayList;

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
 * Servicio para la gestión de administradores en el sistema.
 * <p>
 * Esta clase implementa la lógica CRUD (Crear, Leer, Actualizar,
 * Desactivar/Activar) para los administradores
 * utilizando el repositorio {@link AdministratorRepository} y herramientas de
 * conversión de entidades a DTO.
 * </p>
 * 
 * @author Adolfo Plaza
 * @version 1.0
 * @since 1.0
 */
@Service
@RequiredArgsConstructor
public class AdministratorCrudServiceImpl implements AdministratorCrudService {

    private final AdministratorRepository administratorRepository;

    private final PasswordEncoder passwordEncoder;

    /**
     * Obtiene un administrador por su ID.
     * <p>
     * Busca el administrador en el repositorio, filtrando solo aquellos que están
     * activos. Si no se encuentra,
     * lanza una excepción {@link ResourceNotFoundException}.
     * </p>
     *
     * @param adminId el ID del administrador a buscar
     * @return el administrador encontrado, convertido a {@link AdministratorDTO}
     * @throws ResourceNotFoundException si no se encuentra el administrador o está
     *                                   inactivo
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
     * <p>
     * Recibe un DTO de administrador, codifica su contraseña, asigna el rol de
     * administrador y lo guarda
     * en el repositorio.
     * </p>
     *
     * @param administratorDTO el DTO con la información del administrador a crear
     * @return el administrador creado, convertido a {@link AdministratorDTO}
     */
    @Override
    public AdministratorDTO createAdministrator(AdministratorDTO administratorDTO) {
        administratorDTO.setPassword(passwordEncoder.encode(administratorDTO.getPassword()));
        administratorDTO.setRole(UserRole.ADMIN);
        administratorDTO.setNotifications(new ArrayList<>());
        Administrator administrator = ConversionUtils.convertToEntity(administratorDTO, Administrator.class);
        Administrator savedAdministrator = administratorRepository.save(administrator);
        return ConversionUtils.convertToDTO(savedAdministrator, AdministratorDTO.class);
    }

    /**
     * Actualiza la información de un administrador existente.
     * <p>
     * Encuentra el administrador por su ID, filtra los inactivos y actualiza su
     * información
     * utilizando los datos proporcionados en un DTO de actualización. Si se
     * proporciona una nueva contraseña,
     * esta es codificada antes de ser guardada.
     * </p>
     *
     * @param adminId                el ID del administrador a actualizar
     * @param administratorUpdateDTO el DTO con los datos a actualizar
     * @return el administrador actualizado, convertido a {@link AdministratorDTO}
     * @throws ResourceNotFoundException si no se encuentra el administrador o está
     *                                   inactivo
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
     * Activa o desactiva un administrador.
     * <p>
     * Si el administrador está activo, se desactiva y viceversa. Si no se
     * encuentra,
     * lanza una excepción {@link ResourceNotFoundException}.
     * </p>
     *
     * @param adminId el ID del administrador a activar o desactivar
     * @return el administrador actualizado, convertido a {@link AdministratorDTO}
     * @throws ResourceNotFoundException si no se encuentra el administrador
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
