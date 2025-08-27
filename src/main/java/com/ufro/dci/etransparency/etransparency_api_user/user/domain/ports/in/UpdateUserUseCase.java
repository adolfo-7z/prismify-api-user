package com.ufro.dci.etransparency.etransparency_api_user.user.domain.ports.in;

import com.ufro.dci.etransparency.etransparency_api_user.user.domain.models.User;

public interface UpdateUserUseCase {
    User updateUser(Long id, User updatedUser);

    String toggleUserStatus(Long id);

    void incrementAuditsPerformed(Long id);
}
