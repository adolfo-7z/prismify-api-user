package com.ufro.dci.etransparency.etransparency_api_user.dtos.request;

import java.util.*;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.ufro.dci.etransparency.etransparency_api_user.models.request.InstitutionRequest.RequestStatus;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InstitutionRequestDTO {

    private Long id;

    private RequestStatus requestStatus;

    @NotNull(message = "Institution name cannot be null")
    @NotBlank(message = "Institution name cannot be blank")
    private String institutionName;

    @NotNull(message = "Phone number cannot be null")
    @NotBlank(message = "Phone number cannot be blank")
    private String phoneNumber;

    @NotNull(message = "Manager email cannot be null")
    @NotBlank(message = "Manager email cannot be blank")
    private String managerEmail;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Date createdAt;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Date updatedAt;

}
