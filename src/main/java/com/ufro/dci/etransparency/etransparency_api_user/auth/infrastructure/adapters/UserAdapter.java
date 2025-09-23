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

/**
 * Adaptador que implementa {@link LoadAuthUserPort} para obtener detalles de
 * usuario
 * desde un servicio externo a través de HTTP usando {@link RestTemplate}.
 * <p>
 * Esta clase se encarga de consultar la API de usuarios interna y mapear la
 * respuesta
 * a la entidad {@link AuthUserDetails}.
 * 
 * @author Adolfo Plaza
 */
@Component
public class UserAdapter implements LoadAuthUserPort {

    private final RestTemplate restTemplate;
    private String userApiUrl;

    /**
     * Constructor de {@link UserAdapter}.
     * 
     * @param restTemplate Instancia de {@link RestTemplate} utilizada para realizar
     *                     llamadas HTTP a la API de usuarios.
     * @param userApiUrl   URL base del servicio de usuarios. Se inyecta desde
     *                     propiedades de configuración.
     */
    public UserAdapter(RestTemplate restTemplate,
            @Value("${user.api.url}") String userApiUrl) {
        this.restTemplate = restTemplate;
        this.userApiUrl = userApiUrl;
    }

    /**
     * Obtiene los detalles de un usuario a partir de su nombre de usuario.
     * <p>
     * Realiza una llamada HTTP GET a la API interna y, si se encuentra el usuario,
     * lo mapea a {@link AuthUserDetails}. En caso de que el usuario no exista,
     * retorna un {@link Optional#empty()}.
     * 
     * @param username Nombre de usuario a buscar.
     * @return {@link Optional} que contiene los detalles del usuario si se
     *         encuentra,
     *         o vacío si no existe.
     * @throws UserPortException Si ocurre un error en la comunicación con la API
     *                           o cualquier otra excepción no controlada.
     */
    @Override
    public Optional<AuthUserDetails> loadByUsername(String username) {
        String url = userApiUrl + "/internal/users/by-username/" + username;
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
