package com.ufro.dci.etransparency.etransparency_api_user.user.application.usecases;

import java.util.List;

import com.ufro.dci.etransparency.etransparency_api_user.user.domain.models.User;
import com.ufro.dci.etransparency.etransparency_api_user.user.domain.ports.in.RetrieveUserUseCase;
import com.ufro.dci.etransparency.etransparency_api_user.user.domain.ports.out.UserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class RetrieveUserUseCaseImpl implements RetrieveUserUseCase {

    private final UserRepository userRepository;

    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public List<User> getAllUsers(int page, int size, String dateOrder, String name) {
        return userRepository.findAllPaged(page, size, dateOrder, name);
    }

    @Override
    public List<String> getUserNotifications(Long id) {
        return userRepository.findById(id).getNotifications();
    }

}
