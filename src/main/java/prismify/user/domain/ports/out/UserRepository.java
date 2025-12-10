package prismify.user.domain.ports.out;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;

import prismify.user.domain.models.Role;
import prismify.user.domain.models.User;

public interface UserRepository {
    User save(User user);

    User findById(Long id);

    User findByUsername(String username);

    User findByEmail(String email);

    User findByRole(Role role);

    boolean existsByEmail(String email);

    boolean existsByUsername(String username);

    boolean existsByRole(Role role);

    List<User> findAll();

    Page<User> findAll(int page, int size, String username, String email, String date, Boolean active, Role role);

    Optional<User> update(User user);

    boolean deleteById(Long id);
}
