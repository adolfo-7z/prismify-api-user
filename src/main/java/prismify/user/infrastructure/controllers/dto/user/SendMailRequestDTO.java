package prismify.user.infrastructure.controllers.dto.user;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SendMailRequestDTO {

    private String to;

}