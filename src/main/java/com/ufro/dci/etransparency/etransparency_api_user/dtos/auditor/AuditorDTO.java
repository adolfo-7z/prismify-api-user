package com.ufro.dci.etransparency.etransparency_api_user.dtos.auditor;

import java.util.Date;
import com.ufro.dci.etransparency.etransparency_api_user.dtos.UserDTO;
import jakarta.validation.constraints.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class AuditorDTO extends UserDTO {

    @NotNull(message = "City cannot be null")
    @NotBlank(message = "City cannot be blank")
    private String city;

    private Long nAssignedInstitutions;

    private Long nAuditsPerformed;

    private Date createdAt;

    private Date updatedAt;

    @Size(max = 20, message = "Website cannot exceed 20 characters")
    private String color;

    @Size(max = 5, message = "Website cannot exceed 5 characters")
    private String acronym;
}
