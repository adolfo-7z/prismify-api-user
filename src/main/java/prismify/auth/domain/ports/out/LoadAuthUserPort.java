package prismify.auth.domain.ports.out;

import java.util.Optional;

import prismify.auth.domain.model.AuthUserDetails;

public interface LoadAuthUserPort {
    Optional<AuthUserDetails> loadByUsername(String username);

    Optional<AuthUserDetails> loadByEmail(String email);
}
