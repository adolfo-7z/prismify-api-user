package com.ufro.dci.etransparency.etransparency_api_user.dtos.manager;

import com.ufro.dci.etransparency.etransparency_api_user.dtos.UserUpdateDTO;
import com.ufro.dci.etransparency.etransparency_api_user.dtos.institution.InstitutionDTO;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ManagerUpdateDTO extends UserUpdateDTO {

    private String position;

    private String rut;

    private InstitutionDTO institution;

}
