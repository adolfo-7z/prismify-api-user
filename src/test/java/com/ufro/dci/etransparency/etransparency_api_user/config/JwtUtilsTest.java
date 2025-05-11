package com.ufro.dci.etransparency.etransparency_api_user.config;

import static org.junit.jupiter.api.Assertions.*;
import com.ufro.dci.etransparency.etransparency_api_user.config.security.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.lang.reflect.Field;
import java.security.Key;

 class JwtUtilsTest {

    @InjectMocks
    private JwtUtils jwtUtils;

    @Mock
    private Key key;

    @BeforeEach
     void setUp() throws NoSuchFieldException, IllegalAccessException {
        MockitoAnnotations.openMocks(this);

        // Establece el valor de secretKey usando una clave de 256 bits en Base64 (32 caracteres)
        Field secretKeyField = JwtUtils.class.getDeclaredField("secretKey");
        secretKeyField.setAccessible(true);
        secretKeyField.set(jwtUtils, "c2VjdXJlS2V5Rm9yVE9LZW5jcnlwdGlvbkF1dGhUb2tlbjEyMzQ1Njc4OTAxMjM=");

        // Establece el valor de expirationTime
        Field expirationTimeField = JwtUtils.class.getDeclaredField("expirationTime");
        expirationTimeField.setAccessible(true);
        expirationTimeField.set(jwtUtils, "3600000"); // 1 hora en milisegundos
    }

    @Test
     void testGenerateAccessToken() {
        String username = "testUser";
        Long id = 1L;
        String role = "ROLE_USER";

        String token = jwtUtils.generateAccessToken(username, id, role);
        assertNotNull(token);

        Claims claims = jwtUtils.getAllClaims(token);
        assertEquals(username, claims.getSubject());
        assertEquals(id, claims.get("id", Long.class));
        assertEquals(role, claims.get("role", String.class));
    }

    @Test
     void testIsTokenValid() {
        String token = jwtUtils.generateAccessToken("testUser", 1L, "ROLE_USER");

        assertTrue(jwtUtils.isTokenValid(token));
    }

    @Test
     void testIsTokenValidWithInvalidToken() {
        String invalidToken = "invalidTokenString";

        assertFalse(jwtUtils.isTokenValid(invalidToken));
    }

    @Test
     void testGetAllClaims() {
        String token = jwtUtils.generateAccessToken("testUser", 1L, "ROLE_USER");

        Claims claims = jwtUtils.getAllClaims(token);
        assertEquals("testUser", claims.getSubject());
        assertEquals(1L, claims.get("id", Long.class));
        assertEquals("ROLE_USER", claims.get("role", String.class));
    }

    @Test
     void testGetClaim() {
        String token = jwtUtils.generateAccessToken("testUser", 1L, "ROLE_USER");

        String username = jwtUtils.getClaim(token, Claims::getSubject);
        assertEquals("testUser", username);
    }

    @Test
     void testGetUsernameFromToken() {
        String token = jwtUtils.generateAccessToken("testUser", 1L, "ROLE_USER");

        String username = jwtUtils.getUsernameFromToken(token);
        assertEquals("testUser", username);
    }

    @Test
     void testGetIdFromToken() {
        String token = jwtUtils.generateAccessToken("testUser", 1L, "ROLE_USER");

        Long id = jwtUtils.getIdFromToken(token);
        assertEquals(1L, id);
    }

    @Test
     void testGetRoleFromToken() {
        String token = jwtUtils.generateAccessToken("testUser", 1L, "ROLE_USER");

        String role = jwtUtils.getRoleFromToken(token);
        assertEquals("ROLE_USER", role);
    }

    @Test
     void testExpiredTokenIsInvalid() throws InterruptedException, NoSuchFieldException, IllegalAccessException {
        // Configura expirationTime a un valor muy corto (1 ms)
        Field expirationTimeField = JwtUtils.class.getDeclaredField("expirationTime");
        expirationTimeField.setAccessible(true);
        expirationTimeField.set(jwtUtils, "1");

        String token = jwtUtils.generateAccessToken("testUser", 1L, "ROLE_USER");

        // Espera a que expire
        Thread.sleep(10);

        assertFalse(jwtUtils.isTokenValid(token));
    }
}

