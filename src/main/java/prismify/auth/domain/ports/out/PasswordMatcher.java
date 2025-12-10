package prismify.auth.domain.ports.out;

public interface PasswordMatcher {
    boolean matches(String rawPassword, String hashedPassword);
}
