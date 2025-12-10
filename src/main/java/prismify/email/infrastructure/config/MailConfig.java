package prismify.email.infrastructure.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import prismify.email.application.services.MailService;
import prismify.email.application.usecases.ComposeMailUseCaseImpl;
import prismify.email.domain.ports.out.SendMailPort;
import prismify.email.infrastructure.adapters.SmtpMailSenderAdapter;

/**
 * Configuración de los beans relacionados con el envío de correos electrónicos.
 * <p>
 * Esta clase define los beans necesarios para el servicio de correo
 * electrónico,
 * incluyendo la configuración del servicio de envío y el puerto de envío.
 * 
 * @author Adolfo Plaza
 */
@Configuration
public class MailConfig {

    /**
     * Crea y configura un bean de {@link MailService}.
     * <p>
     * Este servicio se inicializa con la dirección de correo predeterminada de la
     * aplicación
     * y el puerto de envío de correo.
     *
     * @param adminMail    dirección de correo predeterminada definida en las
     *                     propiedades de la aplicación
     * @param sendMailPort puerto de envío de correo
     * @return instancia configurada de {@link MailService}
     */
    @Bean
    MailService mailService(@Value("${spring.application.default-email}") String adminMail,
            SendMailPort sendMailPort) {
        return new MailService(new ComposeMailUseCaseImpl(adminMail, sendMailPort));
    }

    /**
     * Crea y configura un bean de {@link SendMailPort}.
     * <p>
     * Este bean se obtiene a partir del adaptador {@link SmtpMailSenderAdapter} que
     * implementa
     * la interfaz {@link SendMailPort}.
     *
     * @param adapter adaptador SMTP para el envío de correos
     * @return instancia de {@link SendMailPort} implementada por el adaptador
     *         proporcionado
     */
    @Bean
    SendMailPort sendMailPort(SmtpMailSenderAdapter adapter) {
        return adapter;
    }

}
