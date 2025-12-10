package prismify.commons.config;

import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.data.web.config.EnableSpringDataWebSupport.PageSerializationMode;
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
@EnableSpringDataWebSupport(pageSerializationMode = PageSerializationMode.VIA_DTO)
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
    RestTemplate restTemplate(@Value("${transparencia.api.key}") String apiKey) {
        RestTemplate restTemplate = new RestTemplate(
                new HttpComponentsClientHttpRequestFactory(HttpClients.createDefault()));
        restTemplate.getInterceptors().add((request, body, execution) -> {
            request.getHeaders().add("X-API-KEY", apiKey);
            return execution.execute(request, body);
        });
        return restTemplate;
    }

}
