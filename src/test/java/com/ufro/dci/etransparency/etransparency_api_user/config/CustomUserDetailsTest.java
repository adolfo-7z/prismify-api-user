package com.ufro.dci.etransparency.etransparency_api_user.config;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import com.ufro.dci.etransparency.etransparency_api_user.config.security.models.CustomUserDetails;
import com.ufro.dci.etransparency.etransparency_api_user.models.UserEntity;
import com.ufro.dci.etransparency.etransparency_api_user.models.UserEntity.UserRole;
import java.util.Collection;

class CustomUserDetailsTest {

    @Mock
    private UserEntity userEntity;

    private CustomUserDetails customUserDetails;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Configura el UserEntity simulado con un rol y credenciales
        userEntity = new UserEntity();
        userEntity.setUsername("testUser");
        userEntity.setPassword("testPassword");
        userEntity.setRole(UserRole.ADMIN);

        customUserDetails = new CustomUserDetails(userEntity);
    }

    @Test
    void testGetAuthorities() {
        Collection<? extends GrantedAuthority> authorities = customUserDetails.getAuthorities();
        assertNotNull(authorities);
        assertEquals(1, authorities.size());
        assertTrue(authorities.contains(new SimpleGrantedAuthority("ROLE_ADMIN")));
    }

    @Test
    void testGetPassword() {
        assertEquals("testPassword", customUserDetails.getPassword());
    }

    @Test
    void testGetUsername() {
        assertEquals("testUser", customUserDetails.getUsername());
    }

    @Test
    void testIsAccountNonExpired() {
        assertTrue(customUserDetails.isAccountNonExpired());
    }

    @Test
    void testIsAccountNonLocked() {
        assertTrue(customUserDetails.isAccountNonLocked());
    }

    @Test
    void testIsCredentialsNonExpired() {
        assertTrue(customUserDetails.isCredentialsNonExpired());
    }

    @Test
    void testIsEnabled() {
        assertTrue(customUserDetails.isEnabled());
    }

    @Test
    void testGetUserEntity() {
        assertEquals(userEntity, customUserDetails.getUserEntity());
    }
}
