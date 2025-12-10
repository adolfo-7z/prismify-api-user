package prismify.auth.infrastructure.controllers.dto;

import com.fasterxml.jackson.annotation.JsonAlias;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthRequestDTO {

    @JsonAlias({"username", "identifier", "email"})
    private String identifier;

    private String password;
    
}
