package com.ufro.dci.etransparency.etransparency_api_user.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.ufro.dci.etransparency.etransparency_api_user.config.security.handler.CustomAccessDeniedHandler;

@Configuration
public class ApplicationConfig {

    @Bean
    public CustomAccessDeniedHandler customAccessDeniedHandler() {
        return new CustomAccessDeniedHandler();
    }
}
