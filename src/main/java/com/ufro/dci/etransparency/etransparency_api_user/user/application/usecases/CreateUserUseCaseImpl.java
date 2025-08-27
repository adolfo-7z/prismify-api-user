package com.ufro.dci.etransparency.etransparency_api_user.user.application.usecases;

import java.util.ArrayList;

import com.ufro.dci.etransparency.etransparency_api_user.user.domain.models.User;
import com.ufro.dci.etransparency.etransparency_api_user.user.domain.ports.in.CreateUserUseCase;
import com.ufro.dci.etransparency.etransparency_api_user.user.domain.ports.out.PasswordHasher;
import com.ufro.dci.etransparency.etransparency_api_user.user.domain.ports.out.UserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CreateUserUseCaseImpl implements CreateUserUseCase {

    private final UserRepository userRepository;
    private final PasswordHasher hasher;

    @Override
    public User createUser(User user) {
        user.setActive(true);
        user.setTotalInstitutions(0L);
        user.setAuditsPerformed(0L);
        user.setNotifications(new ArrayList<>());
        String hashedPssword = hasher.hash(user.getPassword());
        user.setPassword(hashedPssword);
        return userRepository.save(user);
    }

}
