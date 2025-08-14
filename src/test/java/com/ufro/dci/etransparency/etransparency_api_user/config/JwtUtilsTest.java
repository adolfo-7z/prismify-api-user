package com.ufro.dci.etransparency.etransparency_api_user.config;

import static org.junit.jupiter.api.Assertions.*;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jwt.*;
import com.ufro.dci.etransparency.etransparency_api_user.config.security.utils.JwtUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.Date;

@ExtendWith(MockitoExtension.class)
class JwtUtilsTest {

    private JwtUtils jwtUtils;

    private final String secret = "9/pEt+AKgI8wmHh/vzv8gC08VmZLMZJReEEjTIqQfRAP7Yv0g9juGwi8FUH6Iyaw";
    private final String expiration = "60000";

    @BeforeEach
    void setup() throws Exception {
        jwtUtils = new JwtUtils();
        setField(jwtUtils, "secret", secret);
        setField(jwtUtils, "expiration", expiration);
    }

    private void setField(Object target, String fieldName, Object value) throws Exception {
        var field = target.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(target, value);
    }

    @Test
    void generateAccessToken_ShouldReturnValidToken() {
        String token = jwtUtils.generateAccessToken("usuario", 123L, "admin");
        assertNotNull(token);
        assertTrue(jwtUtils.isTokenValid(token));
        assertEquals("usuario", jwtUtils.getUsernameFromToken(token));
        assertEquals(123L, jwtUtils.getIdFromToken(token));
        assertEquals("admin", jwtUtils.getRoleFromToken(token));
    }

    @Test
    void isTokenValid_ShouldReturnFalse_ForInvalidToken() {
        assertFalse(jwtUtils.isTokenValid("this-is-not-a-token"));
    }

    @Test
    void isTokenValid_ShouldReturnFalse_ForExpiredToken() throws Exception {
        var now = Instant.now();
        JWTClaimsSet claims = new JWTClaimsSet.Builder()
                .subject("user")
                .claim("role", "admin")
                .claim("id", 1L)
                .issueTime(Date.from(now.minusSeconds(3600)))
                .expirationTime(Date.from(now.minusSeconds(1)))
                .build();
        JWSSigner signer = new MACSigner(secret.getBytes());
        SignedJWT signedJWT = new SignedJWT(new JWSHeader(JWSAlgorithm.HS256), claims);
        signedJWT.sign(signer);
        String expiredToken = signedJWT.serialize();

        assertFalse(jwtUtils.isTokenValid(expiredToken));
    }

    @Test
    void getAllClaims_ShouldReturnClaims() {
        String token = jwtUtils.generateAccessToken("mech", 999L, "user");
        JWTClaimsSet claims = jwtUtils.getAllClaims(token);
        assertEquals("mech", claims.getSubject());
        assertEquals(999L, claims.getClaim("id"));
        assertEquals("user", claims.getClaim("role"));
    }

    @Test
    void getClaim_ShouldExtractClaimUsingFunction() {
        String token = jwtUtils.generateAccessToken("forge", 555L, "tech");
        String subject = jwtUtils.getClaim(token, JWTClaimsSet::getSubject);
        Long id = jwtUtils.getClaim(token, claims -> (Long) claims.getClaim("id"));
        String role = jwtUtils.getClaim(token, claims -> (String) claims.getClaim("role"));
        assertEquals("forge", subject);
        assertEquals(555L, id);
        assertEquals("tech", role);
    }

    @Test
    void getUsernameFromToken_ShouldReturnSubject() {
        String token = jwtUtils.generateAccessToken("dominus", 777L, "lord");
        assertEquals("dominus", jwtUtils.getUsernameFromToken(token));
    }

    @Test
    void getIdFromToken_ShouldReturnId() {
        String token = jwtUtils.generateAccessToken("dominus", 777L, "lord");
        assertEquals(777L, jwtUtils.getIdFromToken(token));
    }

    @Test
    void getRoleFromToken_ShouldReturnRole() {
        String token = jwtUtils.generateAccessToken("dominus", 777L, "lord");
        assertEquals("lord", jwtUtils.getRoleFromToken(token));
    }

    @Test
    void getAllClaims_ShouldThrowRuntimeException_OnInvalidToken() {
        RuntimeException ex = assertThrows(RuntimeException.class, () -> jwtUtils.getAllClaims("bad.token"));
        assertTrue(ex.getMessage().contains("Invalid token"));
    }

}
