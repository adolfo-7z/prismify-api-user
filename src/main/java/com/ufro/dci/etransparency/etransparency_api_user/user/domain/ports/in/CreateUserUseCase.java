package com.ufro.dci.etransparency.etransparency_api_user.user.domain.ports.in;

import com.ufro.dci.etransparency.etransparency_api_user.user.domain.models.User;

public interface CreateUserUseCase {
    User createUser(User user);
}
