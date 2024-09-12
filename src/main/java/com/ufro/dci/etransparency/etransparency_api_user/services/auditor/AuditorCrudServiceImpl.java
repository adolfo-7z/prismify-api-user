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
 * Servicio para la gestión de Auditores.
 */
@Service
@RequiredArgsConstructor
@CacheConfig(cacheNames = "auditors")
public class AuditorCrudServiceImpl implements AuditorCrudService {

    private final AuditorRepository auditorRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * Obtiene un auditor por su ID.
     *
     * @param auditorId ID del auditor.
     * @return DTO del auditor.
     * @throws ResourceNotFoundException si el auditor no es encontrado o está
     *                                   inactivo.
     */
    @Override
    @Cacheable(key="#auditorId")
    public AuditorDTO getAuditor(Long auditorId) {
        return auditorRepository.findById(auditorId)
                .filter(Auditor::isActive)
                .map(auditor -> ConversionUtils.convertToDTO(auditor, AuditorDTO.class))
                .orElseThrow(() -> new ResourceNotFoundException(NOT_FOUND,
                        THE_AUDITOR_WITH_ID + auditorId + WAS_NOT_FOUND_OR_INACTIVE));
    }

    /**
     * Crea un nuevo auditor.
     *
     * @param auditorDTO DTO del auditor a crear.
     * @return DTO del auditor creado.
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
     * Actualiza un auditor existente.
     *
     * @param auditorId        ID del auditor a actualizar.
     * @param auditorUpdateDTO DTO con los datos a actualizar.
     * @return DTO del auditor actualizado.
     * @throws ResourceNotFoundException si el auditor no es encontrado o está
     *                                   inactivo.
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
     * Alterna el estado de actividad de un auditor.
     *
     * @param auditorId ID del auditor.
     * @return DTO del auditor con el estado actualizado.
     * @throws ResourceNotFoundException si el auditor no es encontrado.
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
