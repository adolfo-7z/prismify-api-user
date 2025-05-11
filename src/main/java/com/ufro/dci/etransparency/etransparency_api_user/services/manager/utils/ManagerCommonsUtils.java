package com.ufro.dci.etransparency.etransparency_api_user.services.manager.utils;

import lombok.*;

import org.springframework.stereotype.Component;

import static com.ufro.dci.etransparency.etransparency_api_user.utils.Constants.*;

import com.ufro.dci.etransparency.etransparency_api_user.dtos.manager.ManagerDTO;
import com.ufro.dci.etransparency.etransparency_api_user.exceptions.custom.ResourceNotFoundException;
import com.ufro.dci.etransparency.etransparency_api_user.models.manager.Manager;
import com.ufro.dci.etransparency.etransparency_api_user.repositories.manager.ManagerRepository;
import com.ufro.dci.etransparency.etransparency_api_user.utils.ConversionUtils;

@Component
@RequiredArgsConstructor
public class ManagerCommonsUtils {

    private final ManagerRepository managerRepository;

    public Manager findManagerById(Long managerId){
        return managerRepository.findById(managerId)
                .filter(Manager::isActive)
                .orElseThrow(() -> new ResourceNotFoundException(NOT_FOUND,
                        THE_MANAGER_WITH_ID + managerId + WAS_NOT_FOUND_OR_INACTIVE));
    }

    public ManagerDTO saveAndConvertToDTO(Manager manager){
        Manager savedManager = managerRepository.save(manager);
        return ConversionUtils.convertToDTO(savedManager, ManagerDTO.class);
    }

}
