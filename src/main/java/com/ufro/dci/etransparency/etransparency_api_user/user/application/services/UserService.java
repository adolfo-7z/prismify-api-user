package com.ufro.dci.etransparency.etransparency_api_user.user.application.services;

import java.util.List;

import com.ufro.dci.etransparency.etransparency_api_user.user.domain.models.User;
import com.ufro.dci.etransparency.etransparency_api_user.user.domain.ports.in.*;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserService implements CreateUserUseCase, RetrieveUserUseCase, UpdateUserUseCase, DeleteUserUseCase {

    private final CreateUserUseCase createUserUseCase;
    private final RetrieveUserUseCase retrieveUserUseCase;
    private final UpdateUserUseCase updateUserUseCase;
    private final DeleteUserUseCase deleteUserUseCase;

    @Override
    public User createUser(User user) {
        return createUserUseCase.createUser(user);
    }

    @Override
    public User getUserById(Long id) {
        return retrieveUserUseCase.getUserById(id);
    }

    @Override
    public User getUserByUsername(String username) {
        return retrieveUserUseCase.getUserByUsername(username);
    }

    @Override
    public List<User> getAllUsers(int page, int size, String dateOrder, String name) {
        return retrieveUserUseCase.getAllUsers(page, size, dateOrder, name);
    }

    @Override
    public List<String> getUserNotifications(Long id) {
        return retrieveUserUseCase.getUserNotifications(id);
    }

    @Override
    public User updateUser(Long id, User updatedUser) {
        return updateUserUseCase.updateUser(id, updatedUser);
    }

    @Override
    public String toggleUserStatus(Long id) {
        return updateUserUseCase.toggleUserStatus(id);
    }

    @Override
    public void incrementAuditsPerformed(Long id) {
        updateUserUseCase.incrementAuditsPerformed(id);
    }

    @Override
    public String deleteUser(Long id) {
        return deleteUserUseCase.deleteUser(id);
    }

}
