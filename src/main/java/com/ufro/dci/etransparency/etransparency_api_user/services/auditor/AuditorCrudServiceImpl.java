package com.ufro.dci.etransparency.etransparency_api_user.services.auditor;

import java.util.*;

import lombok.*;

import org.springframework.cache.annotation.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ufro.dci.etransparency.etransparency_api_user.dtos.auditor.AuditorDTO;
import com.ufro.dci.etransparency.etransparency_api_user.dtos.auditor.AuditorUpdateDTO;
import com.ufro.dci.etransparency.etransparency_api_user.exceptions.custom.ResourceNotFoundException;
import com.ufro.dci.etransparency.etransparency_api_user.models.UserEntity.UserRole;
import com.ufro.dci.etransparency.etransparency_api_user.models.auditor.Auditor;
import com.ufro.dci.etransparency.etransparency_api_user.services.auditor.utils.AuditorCommonsUtils;
import com.ufro.dci.etransparency.etransparency_api_user.utils.ConversionUtils;

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

    private final PasswordEncoder passwordEncoder;

    private final AuditorCommonsUtils auditorCommonsUtils;

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
        Auditor auditor = auditorCommonsUtils.findAuditorById(auditorId);
        return ConversionUtils.convertToDTO(auditor, AuditorDTO.class);
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
        Auditor auditor = ConversionUtils.convertToEntity(auditorDTO, Auditor.class);
        auditor.setPassword(passwordEncoder.encode(auditorDTO.getPassword()));
        auditor.setRole(UserRole.AUDITOR);
        auditor.setNAssignedInstitutions(0L);
        auditor.setNAuditsPerformed(0L);
        auditor.setCreatedAt(new Date());
        auditor.setUpdatedAt(new Date());
        auditor.setNotifications(new ArrayList<>());

        return auditorCommonsUtils.saveAndConvertDTO(auditor);
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
        Auditor auditor = auditorCommonsUtils.findAuditorById(auditorId);

        if (auditorUpdateDTO.getPassword() != null && !auditorUpdateDTO.getPassword().isEmpty()) {
            auditorUpdateDTO.setPassword(passwordEncoder.encode(auditorUpdateDTO.getPassword()));
        }

        ConversionUtils.copyNonNullProperties(auditorUpdateDTO, auditor);
        auditor.setUpdatedAt(new Date());
        return auditorCommonsUtils.saveAndConvertDTO(auditor);
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
        Auditor auditor = auditorCommonsUtils.findAuditorById(auditorId);
        auditor.setActive(!auditor.isActive());
        auditor.setUpdatedAt(new Date());
        return auditorCommonsUtils.saveAndConvertDTO(auditor);
    }

}
