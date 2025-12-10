package prismify.auth.infrastructure.security.jwt;

import static org.assertj.core.api.Assertions.*;
import java.lang.reflect.Field;
import java.time.Instant;
import java.util.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.expression.ParseException;

import com.nimbusds.jwt.JWTClaimsSet;

import prismify.auth.domain.model.AuthUserDetails;
import prismify.auth.infrastructure.controllers.exception.custom.JWTException;

@ExtendWith(MockitoExtension.class)
class JwtTokenProviderTest {

    private JwtTokenProvider jwtTokenProvider;

    private final String secret = "my-super-secret-key-12345678901234567890";
    private final String expiration = "3600000";
    private final AuthUserDetails user = new AuthUserDetails(10L, "juan_perez", "password", "ADMIN", true);

    @BeforeEach
    void setup() throws Exception {
        jwtTokenProvider = new JwtTokenProvider();
        injectValue(jwtTokenProvider, "secret", secret);
        injectValue(jwtTokenProvider, "expiration", expiration);
    }

    private void injectValue(Object target, String fieldName, String value) throws Exception {
        Field field = JwtTokenProvider.class.getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(target, value);
    }

    @Test
    void shouldGenerateValidToken() throws ParseException {
        String token = jwtTokenProvider.generateToken(user);
        JWTClaimsSet claims = jwtTokenProvider.getClaims(token);
        assertThat(claims.getSubject()).isEqualTo("juan_perez");
        assertThat(claims.getClaim("role")).isEqualTo("ADMIN");
        assertThat(claims.getClaim("id")).isEqualTo(10L);
        assertThat(claims.getClaim("isActive")).isEqualTo(true);
        assertThat(claims.getExpirationTime()).isAfter(Date.from(Instant.now()));
    }

    @Test
    void shouldThrowException_WhenTokenCannotBeSigned() throws Exception {
        injectValue(jwtTokenProvider, "secret", "short");
        assertThatThrownBy(() -> jwtTokenProvider.generateToken(user))
                .isInstanceOf(JWTException.class)
                .hasMessageContaining("Error generating JWT token");
    }

    @Test
    void shouldValidateProperToken() {
        String token = jwtTokenProvider.generateToken(user);
        boolean valid = jwtTokenProvider.validateToken(token);
        assertThat(valid).isTrue();
    }

    @Test
    void shouldReturnFalse_WhenSignatureInvalid() {
        String token = jwtTokenProvider.generateToken(user);
        assertThatCode(() -> injectValue(jwtTokenProvider, "secret", "different-secret-key-999"))
                .doesNotThrowAnyException();
        boolean valid = jwtTokenProvider.validateToken(token);
        assertThat(valid).isFalse();
    }

    @Test
    void shouldReturnFalse_WhenTokenExpired() throws Exception {
        injectValue(jwtTokenProvider, "expiration", "-10000");
        String token = jwtTokenProvider.generateToken(user);
        boolean valid = jwtTokenProvider.validateToken(token);
        assertThat(valid).isFalse();
    }

    @Test
    void shouldReturnFalse_WhenTokenMalformed() {
        boolean valid = jwtTokenProvider.validateToken("not-a-real-token");

        assertThat(valid).isFalse();
    }

    @Test
    void shouldReturnClaims_WhenTokenValid() {
        String token = jwtTokenProvider.generateToken(user);
        JWTClaimsSet claims = jwtTokenProvider.getClaims(token);
        assertThat(claims.getSubject()).isEqualTo("juan_perez");
    }

    @Test
    void shouldThrowException_WhenTokenInvalid() {
        assertThatThrownBy(() -> jwtTokenProvider.getClaims("invalid.token"))
                .isInstanceOf(JWTException.class)
                .hasMessageContaining("Invalid token");
    }

    @Test
    void shouldExtractUsername() {
        String token = jwtTokenProvider.generateToken(user);
        String username = jwtTokenProvider.extractUsername(token);
        assertThat(username).isEqualTo("juan_perez");
    }

    @Test
    void shouldExtractUserId() {
        String token = jwtTokenProvider.generateToken(user);
        Long id = jwtTokenProvider.extractUserId(token);
        assertThat(id).isEqualTo(10L);
    }

    @Test
    void shouldExtractRole() {
        String token = jwtTokenProvider.generateToken(user);
        String role = jwtTokenProvider.extractRole(token);
        assertThat(role).isEqualTo("ADMIN");
    }

    @Test
    void shouldExtractIsActive() {
        String token = jwtTokenProvider.generateToken(user);
        boolean isActive = jwtTokenProvider.extractIsActive(token);
        assertThat(isActive).isTrue();
    }

}
