package com.ufro.dci.etransparency.etransparency_api_user.auth.infrastructure.adapters;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.ufro.dci.etransparency.etransparency_api_user.auth.domain.model.AuthUserDetails;
import com.ufro.dci.etransparency.etransparency_api_user.auth.domain.ports.out.LoadAuthUserPort;
import com.ufro.dci.etransparency.etransparency_api_user.auth.infrastructure.controllers.exception.custom.UserPortException;

import lombok.Data;

@Component
public class UserAdapter implements LoadAuthUserPort {

    private final RestTemplate restTemplate;
    private String userApiUrl;

    public UserAdapter(RestTemplate restTemplate,
            @Value("${user.api.url}") String userApiUrl) {
        this.restTemplate = restTemplate;
        this.userApiUrl = userApiUrl;
    }

    @Override
    public Optional<AuthUserDetails> loadByUsername(String username) {
        String url = userApiUrl + "/users/internal/by-username/" + username;
        try {
            ResponseEntity<InternalUserAuthDTO> response = restTemplate.getForEntity(url, InternalUserAuthDTO.class);
            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                InternalUserAuthDTO dto = response.getBody();
                if (dto != null) {
                    return Optional.of(new AuthUserDetails(
                            dto.getId(),
                            dto.getUsername(),
                            dto.getPassword(),
                            dto.getRole(),
                            dto.isActive()));
                }
            }
        } catch (HttpClientErrorException.NotFound e) {
            if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
                return Optional.empty();
            }
            throw new UserPortException();
        } catch (Exception e) {
            throw new UserPortException();
        }
        return Optional.empty();
    }

    @Data
    public static class InternalUserAuthDTO {
        private Long id;
        private String username;
        private String password;
        private String role;
        private boolean active;
    }

}
