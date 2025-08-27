package com.ufro.dci.etransparency.etransparency_api_user.email.infrastructure.adapters;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.ufro.dci.etransparency.etransparency_api_user.email.domain.models.MailMessage;
import com.ufro.dci.etransparency.etransparency_api_user.email.domain.ports.out.SendMailPort;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Component
public class SmtpMailSenderAdapter implements SendMailPort {

    private final JavaMailSender mailSender;
    private final TemplateEngine templateEngine;

    public SmtpMailSenderAdapter(JavaMailSender mailSender, TemplateEngine templateEngine) {
        this.mailSender = mailSender;
        this.templateEngine = templateEngine;
    }

    @Override
    public void send(MailMessage message) {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
            helper.setTo(message.getTo());
            helper.setSubject(message.getSubject());
            helper.setFrom("no-reply@etransparencia.cl");
            Context context = new Context();
            context.setVariable("subject", message.getSubject());
            context.setVariable("message", message.getBody());
            String htmlContent = templateEngine.process("email-template.html", context);
            helper.setText(htmlContent, true);
            mailSender.send(mimeMessage);
        } catch (MessagingException e) {
            throw new RuntimeException("Failed to send email", e);
        }
    }

}
