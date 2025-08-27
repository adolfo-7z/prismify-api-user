package com.ufro.dci.etransparency.etransparency_api_user.email.infrastructure.adapters;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.javamail.JavaMailSender;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.ufro.dci.etransparency.etransparency_api_user.email.domain.models.MailMessage;
import jakarta.mail.internet.MimeMessage;

@ExtendWith(MockitoExtension.class)
class SmtpMailSenderAdapterTest {

    @Mock
    private JavaMailSender mailSender;

    @Mock
    private TemplateEngine templateEngine;

    @InjectMocks
    private SmtpMailSenderAdapter smtpMailSenderAdapter;

    @Mock
    private MimeMessage mimeMessage;

    @Captor
    private ArgumentCaptor<Context> contextCaptor;

    @BeforeEach
    void setUp() {
        when(mailSender.createMimeMessage()).thenReturn(mimeMessage);
    }

    @Test
    void testSend_Success() throws Exception {
        MailMessage message = new MailMessage();
        message.setTo("user@test.com");
        message.setSubject("Test Subject");
        message.setBody("This is the body");
        when(templateEngine.process(eq("email-template.html"), any(Context.class)))
                .thenReturn("<html>processed</html>");
        smtpMailSenderAdapter.send(message);
        verify(mailSender).createMimeMessage();
        verify(templateEngine).process(eq("email-template.html"), contextCaptor.capture());
        verify(mailSender).send(mimeMessage);
        Context usedContext = contextCaptor.getValue();
        assertEquals("Test Subject", usedContext.getVariable("subject"));
        assertEquals("This is the body", usedContext.getVariable("message"));
    }

}
