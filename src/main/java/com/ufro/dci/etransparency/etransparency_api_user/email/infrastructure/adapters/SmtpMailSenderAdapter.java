package com.ufro.dci.etransparency.etransparency_api_user.email.infrastructure.adapters;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.ufro.dci.etransparency.etransparency_api_user.email.domain.models.MailMessage;
import com.ufro.dci.etransparency.etransparency_api_user.email.domain.ports.out.SendMailPort;
import com.ufro.dci.etransparency.etransparency_api_user.email.infrastructure.controllers.exception.custom.MailSendException;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

/**
 * Implementación de {@link SendMailPort} que envía correos electrónicos
 * utilizando SMTP a través de {@link JavaMailSender} y plantillas Thymeleaf.
 * 
 * <p>
 * Esta clase prepara un mensaje MIME con contenido HTML generado a partir
 * de una plantilla Thymeleaf y lo envía a la dirección de correo del
 * destinatario.
 * 
 * <p>
 * El remitente del correo se establece por defecto como
 * <code>no-reply@etransparencia.cl</code>.
 * 
 * @author Adolfo Plaza
 */
@Component
public class SmtpMailSenderAdapter implements SendMailPort {

    private final JavaMailSender mailSender;
    private final TemplateEngine templateEngine;

    /**
     * Constructor que inicializa el adaptador con un {@link JavaMailSender} y un
     * {@link TemplateEngine}.
     *
     * @param mailSender     el servicio de envío de correos SMTP
     * @param templateEngine el motor de plantillas Thymeleaf para generar contenido
     *                       HTML
     */
    public SmtpMailSenderAdapter(JavaMailSender mailSender, TemplateEngine templateEngine) {
        this.mailSender = mailSender;
        this.templateEngine = templateEngine;
    }

    /**
     * Envía un correo electrónico con contenido HTML generado a partir de una
     * plantilla.
     *
     * <p>
     * El mensaje se genera utilizando la plantilla
     * <code>email-template.html</code>,
     * y se establecen las variables <code>subject</code> y <code>message</code>
     * para el contenido
     * del correo.
     *
     * @param message el mensaje de correo a enviar
     * @throws RuntimeException si ocurre un error al enviar el correo
     */
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
            throw new MailSendException();
        }
    }

}
