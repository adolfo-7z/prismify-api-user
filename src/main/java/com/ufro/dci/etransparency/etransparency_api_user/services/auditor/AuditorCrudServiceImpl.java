package com.ufro.dci.etransparency.etransparency_api_user.services.auditor;

import org.springframework.cache.annotation.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.ufro.dci.etransparency.etransparency_api_user.dtos.auditor.AuditorDTO;
import com.ufro.dci.etransparency.etransparency_api_user.dtos.auditor.AuditorUpdateDTO;
import com.ufro.dci.etransparency.etransparency_api_user.exceptions.custom.ResourceNotFoundException;
import com.ufro.dci.etransparency.etransparency_api_user.models.UserEntity.UserRole;
import com.ufro.dci.etransparency.etransparency_api_user.models.auditor.Auditor;
import com.ufro.dci.etransparency.etransparency_api_user.repositories.auditor.AuditorRepository;
import com.ufro.dci.etransparency.etransparency_api_user.utils.ConversionUtils;
import lombok.RequiredArgsConstructor;
import static com.ufro.dci.etransparency.etransparency_api_user.utils.Constants.*;
import java.util.Date;

/**
 * Servicio para la gestión de auditores, que incluye la creación,
 * actualización, obtención
 * y desactivación/activación de auditores. Utiliza el almacenamiento en caché
 * para optimizar
 * las consultas de auditores y el manejo de contraseñas seguras mediante
 * {@link PasswordEncoder}.
 * <p>
 * Esta clase implementa el servicio {@link AuditorCrudService}.
 * </p>
 * 
 * @author Adolfo Plaza
 * @version 1.0
 * @since 1.0
 */
@Service
@RequiredArgsConstructor
@CacheConfig(cacheNames = "auditors")
public class AuditorCrudServiceImpl implements AuditorCrudService {

    private final AuditorRepository auditorRepository;

    private final PasswordEncoder passwordEncoder;

    /**
     * Obtiene un auditor por su ID, asegurándose de que esté activo.
     * <p>
     * Si el auditor no está activo o no se encuentra, se lanza una
     * {@link ResourceNotFoundException}.
     * Utiliza almacenamiento en caché para optimizar las búsquedas.
     * </p>
     * 
     * @param auditorId ID del auditor a buscar
     * @return {@link AuditorDTO} con los detalles del auditor encontrado
     * @throws ResourceNotFoundException si no se encuentra el auditor o está
     *                                   inactivo
     */
    @Override
    @Cacheable(key = "#auditorId")
    public AuditorDTO getAuditor(Long auditorId) {
        return auditorRepository.findById(auditorId)
                .filter(Auditor::isActive)
                .map(auditor -> ConversionUtils.convertToDTO(auditor, AuditorDTO.class))
                .orElseThrow(() -> new ResourceNotFoundException(NOT_FOUND,
                        THE_AUDITOR_WITH_ID + auditorId + WAS_NOT_FOUND_OR_INACTIVE));
    }

    /**
     * Crea un nuevo auditor con la información proporcionada en el DTO.
     * <p>
     * La contraseña del auditor es codificada antes de almacenarla, y se
     * inicializan otros
     * campos predeterminados como el número de instituciones asignadas y auditorías
     * realizadas.
     * </p>
     * 
     * @param auditorDTO DTO con la información del auditor a crear
     * @return {@link AuditorDTO} con los detalles del auditor recién creado
     */
    @Override
    @CacheEvict(allEntries = true)
    public AuditorDTO createAuditor(AuditorDTO auditorDTO) {
        auditorDTO.setPassword(passwordEncoder.encode(auditorDTO.getPassword()));
        auditorDTO.setRole(UserRole.AUDITOR);
        auditorDTO.setNAssignedInstitutions(0L);
        auditorDTO.setNAuditsPerformed(0L);
        auditorDTO.setCreatedAt(new Date());
        auditorDTO.setUpdatedAt(new Date());
        Auditor auditor = ConversionUtils.convertToEntity(auditorDTO, Auditor.class);
        Auditor savedAuditor = auditorRepository.save(auditor);
        return ConversionUtils.convertToDTO(savedAuditor, AuditorDTO.class);
    }

    /**
     * Actualiza la información de un auditor existente.
     * <p>
     * Si se proporciona una nueva contraseña, esta se codifica antes de
     * actualizarse.
     * Este método utiliza la anotación {@link Transactional} para asegurar que
     * los cambios se realicen de manera atómica.
     * </p>
     * 
     * @param auditorId        ID del auditor a actualizar
     * @param auditorUpdateDTO DTO con los nuevos valores para el auditor
     * @return {@link AuditorDTO} con los detalles del auditor actualizado
     * @throws ResourceNotFoundException si no se encuentra el auditor o está
     *                                   inactivo
     */
    @Override
    @Transactional
    @CacheEvict(key = "#auditorId", allEntries = true)
    public AuditorDTO updateAuditor(Long auditorId, AuditorUpdateDTO auditorUpdateDTO) {
        Auditor auditor = auditorRepository.findById(auditorId)
                .filter(Auditor::isActive)
                .orElseThrow(() -> new ResourceNotFoundException(NOT_FOUND,
                        THE_AUDITOR_WITH_ID + auditorId + WAS_NOT_FOUND_OR_INACTIVE));

        if (auditorUpdateDTO.getPassword() != null && !auditorUpdateDTO.getPassword().isEmpty()) {
            auditorUpdateDTO.setPassword(passwordEncoder.encode(auditorUpdateDTO.getPassword()));
        }

        ConversionUtils.copyNonNullProperties(auditorUpdateDTO, auditor);
        auditor.setUpdatedAt(new Date());
        Auditor savedAuditor = auditorRepository.save(auditor);
        return ConversionUtils.convertToDTO(savedAuditor, AuditorDTO.class);
    }

    /**
     * Activa o desactiva un auditor.
     * <p>
     * Este método invierte el estado actual del auditor y lo guarda. Si el auditor
     * no
     * se encuentra o está inactivo, lanza una {@link ResourceNotFoundException}.
     * </p>
     * 
     * @param auditorId ID del auditor a modificar su estado
     * @return {@link AuditorDTO} con los detalles del auditor con su nuevo estado
     * @throws ResourceNotFoundException si no se encuentra el auditor
     */
    @Override
    @Transactional
    @CacheEvict(key = "#auditorId", allEntries = true)
    public AuditorDTO toggleAuditorStatus(Long auditorId) {
        return auditorRepository.findById(auditorId)
                .map(existingAuditor -> {
                    existingAuditor.setActive(!existingAuditor.isActive());
                    existingAuditor.setUpdatedAt(new Date());
                    Auditor savedAauditor = auditorRepository.save(existingAuditor);
                    return ConversionUtils.convertToDTO(savedAauditor, AuditorDTO.class);
                })
                .orElseThrow(() -> new ResourceNotFoundException(NOT_FOUND,
                        THE_AUDITOR_WITH_ID + auditorId + WAS_NOT_FOUND_OR_INACTIVE));
    }

}
