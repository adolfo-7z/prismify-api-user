package com.ufro.dci.etransparency.etransparency_api_user.services.email;

import java.io.IOException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;

/**
 * Servicio para el envío de correos electrónicos HTML utilizando plantillas
 * Thymeleaf.
 * <p>
 * Esta clase implementa el servicio {@link EmailService} para enviar correos
 * electrónicos con contenido HTML,
 * incluyendo imágenes embebidas.
 * 
 * @author Adolfo Plaza
 * @version 1.0
 * @since 1.0
 */
@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender javaMailSender;

    private final TemplateEngine templateEngine;

    /**
     * Envía un correo electrónico en formato HTML.
     * <p>
     * Este método crea un mensaje de correo utilizando el {@link JavaMailSender},
     * configura sus destinatarios, asunto y contenido. También se utiliza Thymeleaf
     * para procesar la plantilla HTML y se embebe una imagen en el correo.
     * </p>
     *
     * @param to      la dirección de correo electrónico del destinatario
     * @param subject el asunto del correo
     * @param message el contenido del mensaje
     * @throws MessagingException si ocurre un error durante la creación o el envío
     *                            del correo
     * @throws IOException        si ocurre un error al cargar los recursos
     *                            necesarios, como imágenes
     */
    @Override
    public void sendHtmlEmail(String to, String subject, String message) throws MessagingException, IOException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);

        helper.setTo(to);
        helper.setSubject(subject);
        helper.setFrom("no-reply@tutransparencia.cl");

        Context context = new Context();
        context.setVariable("subject", subject);
        context.setVariable("message", message);

        String htmlContent = templateEngine.process("email-template2.html", context);
        helper.setText(htmlContent, true);

        javaMailSender.send(mimeMessage);
    }

}
