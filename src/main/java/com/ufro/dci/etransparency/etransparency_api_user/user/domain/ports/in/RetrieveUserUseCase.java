package com.ufro.dci.etransparency.etransparency_api_user.user.domain.ports.in;

import java.util.List;

import com.ufro.dci.etransparency.etransparency_api_user.user.domain.models.User;

public interface RetrieveUserUseCase {

    User getUserById(Long id);

    User getUserByUsername(String username);

    List<User> getAllUsers(int page, int size, String dateOrder, String name);
    
}
