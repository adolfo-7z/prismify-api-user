package prismify.user.domain.ports.in;

import prismify.user.domain.models.User;

public interface UpdateUserUseCase {

    User updateUser(Long id, User updatedUser);

    String toggleUserStatus(Long id);

    void incrementAuditsPerformed(Long id);

}
