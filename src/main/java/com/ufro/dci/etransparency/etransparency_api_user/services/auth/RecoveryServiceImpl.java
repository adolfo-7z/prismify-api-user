package com.ufro.dci.etransparency.etransparency_api_user.services.auth;

import static com.ufro.dci.etransparency.etransparency_api_user.utils.Constants.*;

import com.ufro.dci.etransparency.etransparency_api_user.exceptions.custom.ResourceNotFoundException;
import com.ufro.dci.etransparency.etransparency_api_user.models.administrator.Administrator;
import com.ufro.dci.etransparency.etransparency_api_user.models.auditor.Auditor;
import com.ufro.dci.etransparency.etransparency_api_user.models.manager.Manager;
import com.ufro.dci.etransparency.etransparency_api_user.repositories.administrator.AdministratorRepository;
import com.ufro.dci.etransparency.etransparency_api_user.repositories.auditor.AuditorRepository;
import com.ufro.dci.etransparency.etransparency_api_user.repositories.manager.ManagerRepository;

public class RecoveryServiceImpl implements RecoveryService {

    private AdministratorRepository administratorRepository;

    private ManagerRepository managerRepository;

    private AuditorRepository auditorRepository;

    public String sendRecoveryCode(String email) {
        Administrator admin = administratorRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException(NOT_FOUND,
                        RESOURCES_NOT_FOUND));

        Manager manager = managerRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException(NOT_FOUND,
                        RESOURCES_NOT_FOUND));

        Auditor auditor = auditorRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException(NOT_FOUND,
                        RESOURCES_NOT_FOUND));
                        
        return null;
    }

    public String validateRecoveryCode(String recoveryCode) {
        return null;
    }

    public String validateNewPassword(String password, String validationPassword) {
        return null;
    }

}
