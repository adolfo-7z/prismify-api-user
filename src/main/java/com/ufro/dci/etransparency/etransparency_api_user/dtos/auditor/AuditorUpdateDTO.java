package com.ufro.dci.etransparency.etransparency_api_user.dtos.auditor;

import com.ufro.dci.etransparency.etransparency_api_user.dtos.UserUpdateDTO;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class AuditorUpdateDTO extends UserUpdateDTO {

    private String city;

    private Long nAssignedInstitutions;

    @Size(max = 20, message = "Website cannot exceed 20 characters")
    private String color;

    @Size(max = 5, message = "Website cannot exceed 5 characters")
    private String acronym;

}
