package com.ufro.dci.etransparency.etransparency_api_user.email.infrastructure.config;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.ufro.dci.etransparency.etransparency_api_user.email.application.services.MailService;
import com.ufro.dci.etransparency.etransparency_api_user.email.domain.ports.out.SendMailPort;
import com.ufro.dci.etransparency.etransparency_api_user.email.infrastructure.adapters.SmtpMailSenderAdapter;

@ExtendWith(SpringExtension.class)
@Import(MailConfig.class)
class MailConfigTest {

    @MockitoBean
    private SendMailPort sendMailPort;

    @Autowired
    private MailService mailService;

    @Test
    void shouldCreateMailServiceBean() {
        assertNotNull(mailService);
    }

    @Test
    void shouldCreateSendMailPortBean() {
        MailConfig config = new MailConfig();
        SmtpMailSenderAdapter adapter = Mockito.mock(SmtpMailSenderAdapter.class);
        SendMailPort port = config.sendMailPort(adapter);
        assertNotNull(port);
        assertInstanceOf(SmtpMailSenderAdapter.class, port);
    }

}
