package com.ufro.dci.etransparency.etransparency_api_user.user.domain.ports.out;

import java.util.List;
import java.util.Optional;

import com.ufro.dci.etransparency.etransparency_api_user.user.domain.models.Role;
import com.ufro.dci.etransparency.etransparency_api_user.user.domain.models.User;

public interface UserRepository {
    User save(User user);

    User findById(Long id);

    User findByUsername(String username);

    User findByEmail(String email);

    boolean existsByEmail(String email);

    boolean existsByRole(Role role);

    List<User> findAll();

    List<User> findAllPaged(int page, int size, String dateOrder, String name);

    Optional<User> update(User user);

    boolean deleteById(Long id);
}
