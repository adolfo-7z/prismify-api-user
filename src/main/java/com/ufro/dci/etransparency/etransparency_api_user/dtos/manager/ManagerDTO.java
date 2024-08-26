package com.ufro.dci.etransparency.etransparency_api_user.dtos.manager;

import com.ufro.dci.etransparency.etransparency_api_user.dtos.UserDTO;
import com.ufro.dci.etransparency.etransparency_api_user.dtos.institution.InstitutionDTO;
import jakarta.validation.constraints.*;
import lombok.*;

@Data
@EqualsAndHashCode(callSuper = true)
public class ManagerDTO extends UserDTO{

    @NotNull(message = "Position cannot be null")
    @NotBlank(message = "Position cannot be blank")
    private String position;

    @NotNull(message = "Rut cannot be null")
    @NotBlank(message = "Rut cannot be blank")
    private String rut;

    private InstitutionDTO institution;
}
