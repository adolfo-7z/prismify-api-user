package prismify.user.infrastructure.controllers.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InternalUserAuthDTO {

    private Long id;
    private String username;
    private String password;
    private String role;
    private boolean isActive;
    
}
