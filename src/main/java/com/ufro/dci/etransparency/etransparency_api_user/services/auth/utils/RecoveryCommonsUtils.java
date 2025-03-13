package com.ufro.dci.etransparency.etransparency_api_user.services.auth.utils;

import static com.ufro.dci.etransparency.etransparency_api_user.utils.Constants.*;

import java.util.Optional;

import org.springframework.stereotype.Component;

import com.ufro.dci.etransparency.etransparency_api_user.exceptions.custom.ResourceNotFoundException;
import com.ufro.dci.etransparency.etransparency_api_user.models.UserEntity;
import com.ufro.dci.etransparency.etransparency_api_user.models.administrator.Administrator;
import com.ufro.dci.etransparency.etransparency_api_user.models.auditor.Auditor;
import com.ufro.dci.etransparency.etransparency_api_user.models.manager.Manager;
import com.ufro.dci.etransparency.etransparency_api_user.repositories.administrator.AdministratorRepository;
import com.ufro.dci.etransparency.etransparency_api_user.repositories.auditor.AuditorRepository;
import com.ufro.dci.etransparency.etransparency_api_user.repositories.manager.ManagerRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class RecoveryCommonsUtils {

    private final AdministratorRepository administratorRepository;

    private final ManagerRepository managerRepository;

    private final AuditorRepository auditorRepository;

    public UserEntity findUserByEmail(String email) {
        System.out.println("Email: " + email);
        Optional<Administrator> adminOpt = administratorRepository.findByEmail(email);
        if (adminOpt.isPresent()) {
            Administrator admin = adminOpt.get();
            return admin;
        }

        Optional<Manager> managerOpt = managerRepository.findByEmail(email);
        if (managerOpt.isPresent()) {
            Manager manager = managerOpt.get();
            return manager;
        }

        Optional<Auditor> auditorOpt = auditorRepository.findByEmail(email);
        if (auditorOpt.isPresent()) {
            Auditor auditor = auditorOpt.get();
            return auditor;
        }

        throw new ResourceNotFoundException(NOT_FOUND, "The user with the present email was not found");

    }

    public void saveUser(UserEntity user) {
        if (user instanceof Administrator admin) {
            administratorRepository.save(admin);
        } else if (user instanceof Manager manager) {
            managerRepository.save(manager);
        } else if (user instanceof Auditor auditor) {
            auditorRepository.save(auditor);
        } else {
            throw new IllegalArgumentException("Unknown user type: cannot save.");
        }
    }
    
}
