package com.ufro.dci.etransparency.etransparency_api_user.commons.config;

import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

/**
 * Configuración global de la aplicación.
 * <p>
 * Esta clase define los beans de Spring que estarán disponibles
 * en todo el contexto de la aplicación.
 * 
 * @author Adolfo Plaza
 */
@Configuration
public class GlobalConfig {

    /**
     * Crea y configura un {@link RestTemplate} que se puede
     * inyectar en otros componentes de Spring.
     * <p>
     * Este {@code RestTemplate} utiliza
     * {@link HttpComponentsClientHttpRequestFactory}
     * con un cliente HTTP por defecto de Apache HttpComponents.
     * 
     * @return un objeto {@link RestTemplate} listo para usarse en la aplicación
     */
    @Bean
    RestTemplate restTemplate() {
        return new RestTemplate(new HttpComponentsClientHttpRequestFactory(HttpClients.createDefault()));
    }

}
