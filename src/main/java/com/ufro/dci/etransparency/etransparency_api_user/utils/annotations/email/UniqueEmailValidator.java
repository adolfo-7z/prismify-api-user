package com.ufro.dci.etransparency.etransparency_api_user.utils.annotations.email;

import com.ufro.dci.etransparency.etransparency_api_user.repositories.administrator.AdministratorRepository;
import com.ufro.dci.etransparency.etransparency_api_user.repositories.auditor.AuditorRepository;
import com.ufro.dci.etransparency.etransparency_api_user.repositories.manager.ManagerRepository;

import jakarta.validation.*;

public class UniqueEmailValidator implements ConstraintValidator<UniqueEmail, String> {

    private final AdministratorRepository administratorRepository;

    private final AuditorRepository auditorRepository;

    private final ManagerRepository managerRepository;

    public UniqueEmailValidator(AdministratorRepository administratorRepository, AuditorRepository auditorRepository,
            ManagerRepository managerRepository) {
        this.administratorRepository = administratorRepository;
        this.auditorRepository = auditorRepository;
        this.managerRepository = managerRepository;
    }

    @Override
    public void initialize(UniqueEmail constraintAnnotation) {
        // Este método se deja intencionalmente vacío porque la lógica de inicialización
        // no es necesaria para este validador.
    }

    @Override
    public boolean isValid(String email, ConstraintValidatorContext context) {
        return !administratorRepository.existsByEmail(email) &&
                !auditorRepository.existsByEmail(email) &&
                !managerRepository.existsByEmail(email);
    }
}
