package com.ufro.dci.etransparency.etransparency_api_user.services.auditor.utils;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Component;

import static com.ufro.dci.etransparency.etransparency_api_user.utils.Constants.*;

import com.ufro.dci.etransparency.etransparency_api_user.dtos.auditor.AuditorDTO;
import com.ufro.dci.etransparency.etransparency_api_user.exceptions.custom.ResourceNotFoundException;
import com.ufro.dci.etransparency.etransparency_api_user.models.auditor.Auditor;
import com.ufro.dci.etransparency.etransparency_api_user.repositories.auditor.AuditorRepository;
import com.ufro.dci.etransparency.etransparency_api_user.utils.ConversionUtils;

@Component
@RequiredArgsConstructor
public class AuditorCommonsUtils {

    private final AuditorRepository auditorRepository;

    public Auditor findAuditorById(Long auditorId) {
        return auditorRepository.findById(auditorId)
                .filter(Auditor::isActive)
                .orElseThrow(() -> new ResourceNotFoundException(NOT_FOUND,
                        THE_AUDITOR_WITH_ID + auditorId + WAS_NOT_FOUND_OR_INACTIVE));
    }

    public AuditorDTO saveAndConvertDTO(Auditor auditor){
        Auditor savedAuditor = auditorRepository.save(auditor);
        return ConversionUtils.convertToDTO(savedAuditor, AuditorDTO.class);
    }

}
