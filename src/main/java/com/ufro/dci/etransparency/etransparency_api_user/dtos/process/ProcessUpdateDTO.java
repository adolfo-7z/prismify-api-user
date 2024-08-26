package com.ufro.dci.etransparency.etransparency_api_user.dtos.process;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.ufro.dci.etransparency.etransparency_api_user.dtos.institution.SimpleInstitutionDTO;
import com.ufro.dci.etransparency.etransparency_api_user.models.process.Process.ProcessStatus;
import com.ufro.dci.etransparency.etransparency_api_user.models.process.Process.RequestStatus;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProcessUpdateDTO {

    private Long id;

    private ProcessStatus status;

    private RequestStatus requestStatus;

    private String name;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Date startDate;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Date endDate;

    private Double levelAverage;

    private Long step;

    private String employeesNames;

    private String employeesEmails;

    private String employeesRut;

    private String surveyLink;

    private SimpleInstitutionDTO simepleInstitutionDTO;

}
