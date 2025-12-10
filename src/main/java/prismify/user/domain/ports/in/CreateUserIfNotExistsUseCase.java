package prismify.user.domain.ports.in;

public interface CreateUserIfNotExistsUseCase {
    void createAdminIfMissing(String username, String email, String password);
}
