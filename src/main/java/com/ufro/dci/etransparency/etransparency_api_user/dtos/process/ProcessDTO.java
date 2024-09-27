package com.ufro.dci.etransparency.etransparency_api_user.dtos.process;

import java.util.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.ufro.dci.etransparency.etransparency_api_user.dtos.institution.InstitutionDTO;
import com.ufro.dci.etransparency.etransparency_api_user.dtos.institution.SimpleInstitutionDTO;
import com.ufro.dci.etransparency.etransparency_api_user.dtos.maturity.MaturityModelDTO;
import com.ufro.dci.etransparency.etransparency_api_user.models.process.Process.ProcessStatus;
import com.ufro.dci.etransparency.etransparency_api_user.models.process.Process.RequestStatus;

import jakarta.validation.constraints.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProcessDTO {
    private Long id;

    private ProcessStatus status;

    private RequestStatus requestStatus;

    @NotNull(message = "Name cannot be null")
    @NotBlank(message = "Name cannot be blank")
    private String name;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Date startDate;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Date endDate;

    private Long step;

    private String employeesNames;

    private String employeesEmails;

    private String employeesRut;

    private String surveyLink;

    private Long nEmployees;

    private Date createdAt;

    private Date updatedAt;

    private List<String> milestones;

    private SimpleInstitutionDTO simpleInstitutionDTO;

    @JsonIgnore
    private InstitutionDTO institution;

    @JsonIgnore
    private MaturityModelDTO maturityModel;
}
