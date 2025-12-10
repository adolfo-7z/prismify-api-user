package prismify.auth.domain.ports.in;

import org.apache.hc.client5.http.auth.InvalidCredentialsException;

public interface LoginUseCase {
    String login(String identifier, String rawPassword) throws InvalidCredentialsException;
}
