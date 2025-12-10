package prismify.email.domain.models;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class MailMessage {

    private String to;
    private String subject;
    private String body;

}
