package prismify.email.infrastructure.controllers.dto;

import lombok.*;
import prismify.email.domain.models.MailMessage;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SendMailRequestDTO {

    private String to;
    private String subject;
    private String body;

    public MailMessage toDomain() {
        return new MailMessage(to, subject, body);
    }

}
