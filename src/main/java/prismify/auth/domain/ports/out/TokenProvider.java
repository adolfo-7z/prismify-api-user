package prismify.auth.domain.ports.out;

import com.nimbusds.jwt.JWTClaimsSet;

import prismify.auth.domain.model.AuthUserDetails;

public interface TokenProvider {
    String generateToken(AuthUserDetails user);

    boolean validateToken(String token);

    JWTClaimsSet getClaims(String token);

    String extractUsername(String token);

    Long extractUserId(String token);

    String extractRole(String token);

    boolean extractIsActive(String token);
}
