package com.ufro.dci.etransparency.etransparency_api_user.auth.infrastructure.security.config;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockitoAnnotations;
import org.springframework.context.annotation.Import;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.ufro.dci.etransparency.etransparency_api_user.auth.domain.model.AuthUserDetails;
import com.ufro.dci.etransparency.etransparency_api_user.auth.domain.ports.out.LoadAuthUserPort;

@ExtendWith(SpringExtension.class)
@Import(SecurityBeansConfig.class)
class SecurityBeansConfigTest {

    private SecurityBeansConfig securityBeansConfig;

    @MockitoBean
    private LoadAuthUserPort loadAuthUserPort;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        securityBeansConfig = new SecurityBeansConfig();
    }

    @Test
    void shouldReturnUserDetailsWhenUserExists() {
        AuthUserDetails mockUser = new AuthUserDetails(1L, "usuario123", "hashedPass123", "ADMIN", true);
        when(loadAuthUserPort.loadByUsername("usuario123"))
                .thenReturn(Optional.of(mockUser));
        UserDetailsService userDetailsService = securityBeansConfig.userDetailsService(loadAuthUserPort);
        UserDetails userDetails = userDetailsService.loadUserByUsername("usuario123");
        assertNotNull(userDetails);
        assertEquals("usuario123", userDetails.getUsername());
        assertEquals("hashedPass123", userDetails.getPassword());
        assertTrue(userDetails.isAccountNonLocked(), "Expected account to be non-locked");
        assertTrue(userDetails.getAuthorities().stream()
                .anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN")));
    }

    @Test
    void shouldThrowExceptionWhenUserNotFound() {
        when(loadAuthUserPort.loadByUsername("notusuario")).thenReturn(Optional.empty());
        UserDetailsService userDetailsService = securityBeansConfig.userDetailsService(loadAuthUserPort);
        assertThrows(UsernameNotFoundException.class,
                () -> userDetailsService.loadUserByUsername("notusuario"),
                "Expected UsernameNotFoundException when user is missing");
    }

    @Test
    void shouldSetAccountLockedWhenUserInactive() {
        AuthUserDetails mockUser = new AuthUserDetails(1L, "inactivo", "pass456", "MANAGER", false);
        when(loadAuthUserPort.loadByUsername("inactivo"))
                .thenReturn(Optional.of(mockUser));
        UserDetailsService userDetailsService = securityBeansConfig.userDetailsService(loadAuthUserPort);
        UserDetails userDetails = userDetailsService.loadUserByUsername("inactivo");
        assertFalse(userDetails.isAccountNonLocked(), "Expected account to be locked for inactive user");
    }

}
