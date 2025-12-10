package prismify.user.domain.ports.in;

import prismify.user.domain.models.User;

public interface CreateUserUseCase {
    User createUser(User user);
}
