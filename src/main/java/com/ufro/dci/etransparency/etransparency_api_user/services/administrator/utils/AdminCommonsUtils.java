package com.ufro.dci.etransparency.etransparency_api_user.services.administrator.utils;

import lombok.*;

import org.springframework.stereotype.Component;

import static com.ufro.dci.etransparency.etransparency_api_user.utils.Constants.*;

import java.util.*;

import com.ufro.dci.etransparency.etransparency_api_user.dtos.administrator.AdministratorDTO;
import com.ufro.dci.etransparency.etransparency_api_user.dtos.institution.InstitutionDTO;
import com.ufro.dci.etransparency.etransparency_api_user.exceptions.custom.ResourceNotFoundException;
import com.ufro.dci.etransparency.etransparency_api_user.models.administrator.Administrator;
import com.ufro.dci.etransparency.etransparency_api_user.models.institution.Institution;
import com.ufro.dci.etransparency.etransparency_api_user.repositories.administrator.AdministratorRepository;
import com.ufro.dci.etransparency.etransparency_api_user.repositories.institution.InstitutionRepository;
import com.ufro.dci.etransparency.etransparency_api_user.utils.ConversionUtils;

@Component
@RequiredArgsConstructor
public class AdminCommonsUtils {

    private final AdministratorRepository administratorRepository;

    private final InstitutionRepository institutionRepository;

    public Administrator findAdministratorById(Long administratorId) {
        return administratorRepository.findById(administratorId)
                .orElseThrow(() -> new ResourceNotFoundException(NOT_FOUND,
                        THE_ADMIN_WITH_ID + administratorId + WAS_NOT_FOUND_OR_INACTIVE));
    }

    public AdministratorDTO saveAndConvertToDTO(Administrator administrator) {
        Administrator savedAdministrator = administratorRepository.save(administrator);
        return ConversionUtils.convertToDTO(savedAdministrator, AdministratorDTO.class);
    }

    public List<InstitutionDTO> admingetTopInstitutions() {
        List<InstitutionDTO> institutionDTOs = institutionRepository.findAll().stream()
                .filter(Institution::isActive)
                .sorted(Comparator.comparingLong(Institution::getLevel).reversed())
                .limit(3)
                .map(institution -> ConversionUtils.convertToDTO(institution, InstitutionDTO.class))
                .toList();
        return institutionDTOs;
    }

}
