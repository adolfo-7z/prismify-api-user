package com.ufro.dci.etransparency.etransparency_api_user.user.application.usecases;


import com.ufro.dci.etransparency.etransparency_api_user.user.application.support.PartialUpdateMapper;
import com.ufro.dci.etransparency.etransparency_api_user.user.domain.models.User;
import com.ufro.dci.etransparency.etransparency_api_user.user.domain.ports.in.UpdateUserUseCase;
import com.ufro.dci.etransparency.etransparency_api_user.user.domain.ports.out.UserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UpdateUserUseCaseImpl implements UpdateUserUseCase {

    private final UserRepository userRepository;

    @Override
    public User updateUser(Long id, User updatedUser) {
        User user = userRepository.findById(id);
        PartialUpdateMapper.copyNonNullFields(updatedUser, user);
        userRepository.update(user);
        return user;
    }

    @Override
    public String toggleUserStatus(Long id) {
        User user = userRepository.findById(id);
        boolean wasActive = user.isActive();
        user.setActive(!wasActive);
        userRepository.update(user);
        return wasActive ? "User deactivated" : "User activated";
    }

    @Override
    public void incrementAuditsPerformed(Long id) {
        User user = userRepository.findById(id);
        Long auditsPerformed = user.getAuditsPerformed();
        user.setAuditsPerformed(auditsPerformed + 1L);
        userRepository.update(user);
    }

}
