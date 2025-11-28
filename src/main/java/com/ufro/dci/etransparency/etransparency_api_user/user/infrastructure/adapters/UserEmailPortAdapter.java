package com.ufro.dci.etransparency.etransparency_api_user.user.infrastructure.adapters;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.ufro.dci.etransparency.etransparency_api_user.user.infrastructure.controllers.dto.user.SendMailRequestDTO;
import com.ufro.dci.etransparency.etransparency_api_user.user.infrastructure.controllers.exception.custom.UserEmailPortException;
import com.ufro.dci.etransparency.etransparency_api_user.user.domain.ports.out.UserEmailPort;

import lombok.RequiredArgsConstructor;

/**
 * Implementación del puerto {@link UserEmailPort} que permite enviar correos
 * electrónicos
 * relacionados con el usuario a través de un servicio externo expuesto vía API
 * REST.
 * <p>
 * Esta clase actúa como adaptador, delegando el envío de correos a un servicio
 * remoto
 * utilizando {@link RestTemplate}. Actualmente soporta el envío de códigos de
 * recuperación
 * de cuenta y notificación de que contraseña fue actualizada.
 * 
 *
 * @author Adolfo Plaza
 */
@Component
@RequiredArgsConstructor
public class UserEmailPortAdapter implements UserEmailPort {

    private final RestTemplate restTemplate;

    @Value("${user.api.url}")
    private String userApiUrl;

    /**
     * Envía un correo electrónico con un código de recuperación de cuenta al
     * destinatario especificado.
     *
     * @param to dirección de correo electrónico del destinatario.
     * @throws UserEmailPortException si ocurre un error durante el envío del
     *                                correo.
     */
    @Override
    public void sendRecoveryCodeEmail(String to, String code) {
        postMail("/internal/mail/send/recovery/code/" + code, to);
    }

    /**
     * Envía un correo electrónico con alerta de que se ha cambiado la contraseña
     * del usuario.
     *
     * @param to dirección de correo electrónico del destinatario.
     * @throws UserEmailPortException si ocurre un error durante el envío del
     *                                correo.
     */
    @Override
    public void sendNewPasswordAlert(String to) {
        postMail("/internal/mail/send/recovery/new", to);
    }

    /**
     * Realiza una petición POST al servicio de correo del API de usuario.
     *
     * @param endpoint endpoint relativo del servicio de correo.
     * @param to       dirección de correo electrónico del destinatario.
     * @throws UserEmailPortException si ocurre un error en la llamada al servicio
     *                                remoto.
     */
    private void postMail(String endpoint, String to) {
        String url = userApiUrl + endpoint;
        SendMailRequestDTO request = new SendMailRequestDTO(to);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<SendMailRequestDTO> entity = new HttpEntity<>(request, headers);
        try {
            restTemplate.postForEntity(url, entity, Void.class);
        } catch (RestClientException e) {
            throw new UserEmailPortException();
        }
    }

}
