package prismify.auth.domain.ports.in;

public interface ValidateTokenUseCase {
    boolean validateToken(String token);
}
