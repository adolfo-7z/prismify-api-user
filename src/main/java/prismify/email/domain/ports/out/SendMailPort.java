package prismify.email.domain.ports.out;

import prismify.email.domain.models.MailMessage;

public interface SendMailPort {
    void send(MailMessage message);
}
