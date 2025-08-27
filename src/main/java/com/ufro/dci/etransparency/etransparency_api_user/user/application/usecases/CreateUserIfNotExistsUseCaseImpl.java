package com.ufro.dci.etransparency.etransparency_api_user.user.application.usecases;

import java.util.ArrayList;

import com.ufro.dci.etransparency.etransparency_api_user.user.domain.models.Role;
import com.ufro.dci.etransparency.etransparency_api_user.user.domain.models.User;
import com.ufro.dci.etransparency.etransparency_api_user.user.domain.ports.in.CreateUserIfNotExistsUseCase;
import com.ufro.dci.etransparency.etransparency_api_user.user.domain.ports.out.UserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CreateUserIfNotExistsUseCaseImpl implements CreateUserIfNotExistsUseCase {

    private final UserRepository userRepository;

    @Override
    public void createAdminIfMissing(String username, String email, String password) {
        boolean exists = userRepository.existsByRole(Role.ADMIN);
        if (exists) {
            return;
        }
        User admin = new User();
        admin.setUsername(username);
        admin.setEmail(email);
        admin.setPassword(password);
        admin.setRole(Role.ADMIN);
        admin.setActive(true);
        admin.setTotalInstitutions(0L);
        admin.setAuditsPerformed(0L);
        admin.setNotifications(new ArrayList<>());
        userRepository.save(admin);
    }

}
