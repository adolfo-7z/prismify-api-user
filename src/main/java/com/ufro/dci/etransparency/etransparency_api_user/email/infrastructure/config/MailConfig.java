package com.ufro.dci.etransparency.etransparency_api_user.email.infrastructure.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.ufro.dci.etransparency.etransparency_api_user.email.application.services.MailService;
import com.ufro.dci.etransparency.etransparency_api_user.email.application.usecases.ComposeMailUseCaseImpl;
import com.ufro.dci.etransparency.etransparency_api_user.email.domain.ports.out.SendMailPort;
import com.ufro.dci.etransparency.etransparency_api_user.email.infrastructure.adapters.SmtpMailSenderAdapter;

@Configuration
public class MailConfig {

    @Bean
    MailService mailService(@Value("${spring.application.default-username}") String adminMail,
            SendMailPort sendMailPort) {
        return new MailService(new ComposeMailUseCaseImpl(adminMail, sendMailPort));
    }

    @Bean
    SendMailPort sendMailPort(SmtpMailSenderAdapter adapter) {
        return adapter;
    }

}
