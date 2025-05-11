package com.ufro.dci.etransparency.etransparency_api_user.config;

import static com.ufro.dci.etransparency.etransparency_api_user.utils.Constants.*;
import org.springframework.boot.autoconfigure.web.servlet.DispatcherServletPath;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.DispatcherServlet;

import jakarta.servlet.MultipartConfigElement;

@Configuration
public class CustomDispatcherServletConfig {

    @Bean
    public DispatcherServlet dispatcherServlet() {
        return new DispatcherServlet();
    }

    @Bean
    public ServletRegistrationBean<DispatcherServlet> dispatcherServletRegistration() {

        ServletRegistrationBean<DispatcherServlet> registration = new ServletRegistrationBean<>(dispatcherServlet(),
                "/v1/api/*");
        registration.setName("dispatcherServlet");
        registration.setMultipartConfig(new MultipartConfigElement("/tmp", MAX_FILE_SIZE, MAX_REQUEST_SIZE, 0));
        return registration;

    }

    @Bean
    public DispatcherServletPath dispatcherServletPath() {
        return () -> "/v1/api";
    }

}
