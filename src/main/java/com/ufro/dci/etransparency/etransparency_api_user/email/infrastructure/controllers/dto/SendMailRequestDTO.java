package com.ufro.dci.etransparency.etransparency_api_user.email.infrastructure.controllers.dto;

import com.ufro.dci.etransparency.etransparency_api_user.email.domain.models.MailMessage;
import lombok.*;

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
