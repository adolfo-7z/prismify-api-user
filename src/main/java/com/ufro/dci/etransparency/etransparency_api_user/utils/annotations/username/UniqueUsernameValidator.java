package com.ufro.dci.etransparency.etransparency_api_user.utils.annotations.username;

import com.ufro.dci.etransparency.etransparency_api_user.repositories.administrator.AdministratorRepository;
import com.ufro.dci.etransparency.etransparency_api_user.repositories.auditor.AuditorRepository;
import com.ufro.dci.etransparency.etransparency_api_user.repositories.manager.ManagerRepository;

import jakarta.validation.*;

public class UniqueUsernameValidator implements ConstraintValidator<UniqueUsername, String> {

    private final AdministratorRepository administratorRepository;

    private final AuditorRepository auditorRepository;

    private final ManagerRepository managerRepository;


    public UniqueUsernameValidator(AdministratorRepository administratorRepository, AuditorRepository auditorRepository,
            ManagerRepository managerRepository) {
        this.administratorRepository = administratorRepository;
        this.auditorRepository = auditorRepository;
        this.managerRepository = managerRepository;
    }

    @Override
    public void initialize(UniqueUsername constraintAnnotation) {
    }

    @Override
    public boolean isValid(String username, ConstraintValidatorContext context) {
        return !administratorRepository.existsByUsername(username) &&
                !auditorRepository.existsByUsername(username) &&
                !managerRepository.existsByUsername(username);
    }
}
