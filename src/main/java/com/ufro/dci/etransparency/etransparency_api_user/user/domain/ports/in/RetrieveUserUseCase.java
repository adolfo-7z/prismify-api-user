package com.ufro.dci.etransparency.etransparency_api_user.user.domain.ports.in;

import org.springframework.data.domain.Page;

import com.ufro.dci.etransparency.etransparency_api_user.user.domain.models.Role;
import com.ufro.dci.etransparency.etransparency_api_user.user.domain.models.User;

public interface RetrieveUserUseCase {

    User getUserById(Long id);

    User getUserByUsername(String username);

    User getUserByEmail(String email);

    Page<User> getAllUsers(int page, int size, String username, String email, String date, Boolean active, Role role);

}
