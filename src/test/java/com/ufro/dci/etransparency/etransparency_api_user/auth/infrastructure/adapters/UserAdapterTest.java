package com.ufro.dci.etransparency.etransparency_api_user.auth.infrastructure.adapters;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.ufro.dci.etransparency.etransparency_api_user.auth.domain.model.AuthUserDetails;
import com.ufro.dci.etransparency.etransparency_api_user.auth.infrastructure.controllers.exception.custom.UserPortException;

@ExtendWith(MockitoExtension.class)
class UserAdapterTest {

    @Mock
    private RestTemplate restTemplate;

    private UserAdapter userAdapter;

    private final String apiUrl = "http://user-api";

    @BeforeEach
    void setUp() {
        userAdapter = new UserAdapter(restTemplate, apiUrl);
    }

    @Test
    void shouldReturnAuthUserDetails_WhenUserExists() {
        String username = "juancito";
        String fullUrl = apiUrl + "/internal/users/by-username/" + username;
        UserAdapter.InternalUserAuthDTO dto = new UserAdapter.InternalUserAuthDTO();
        dto.setId(42L);
        dto.setUsername(username);
        dto.setPassword("hashed_password");
        dto.setRole("ADMIN");
        dto.setActive(true);
        ResponseEntity<UserAdapter.InternalUserAuthDTO> response = new ResponseEntity<>(dto, HttpStatus.OK);
        when(restTemplate.getForEntity(fullUrl, UserAdapter.InternalUserAuthDTO.class)).thenReturn(response);
        Optional<AuthUserDetails> result = userAdapter.loadByUsername(username);
        assertThat(result).isPresent()
                .get()
                .satisfies(user -> {
                    assertThat(user.id()).isEqualTo(42L);
                    assertThat(user.username()).isEqualTo("juancito");
                    assertThat(user.hashedPassword()).isEqualTo("hashed_password");
                    assertThat(user.role()).isEqualTo("ADMIN");
                    assertThat(user.isActive()).isTrue();
                });
    }

    @Test
    void shouldThrowUserPortException_OnUnexpectedError() {
        String username = "juan";
        String fullUrl = apiUrl + "/users/internal/by-username/" + username;
        when(restTemplate.getForEntity(fullUrl, UserAdapter.InternalUserAuthDTO.class))
                .thenThrow(new RuntimeException("Data Corrupted"));
        assertThatThrownBy(() -> userAdapter.loadByUsername(username))
                .isInstanceOf(UserPortException.class);
    }

}
