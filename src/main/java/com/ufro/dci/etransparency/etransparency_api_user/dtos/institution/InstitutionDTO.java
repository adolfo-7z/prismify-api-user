package com.ufro.dci.etransparency.etransparency_api_user.dtos.institution;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ufro.dci.etransparency.etransparency_api_user.dtos.auditor.AuditorDTO;
import com.ufro.dci.etransparency.etransparency_api_user.dtos.manager.ManagerDTO;
import com.ufro.dci.etransparency.etransparency_api_user.utils.annotations.rut.ValidRut;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InstitutionDTO {
    private Long id;

    @JsonIgnore
    private boolean isActive = true;

    @NotNull(message = "Address cannot be null")
    @NotBlank(message = "Address cannot be blank")
    @Size(max = 50, message = "Address cannot exceed 50 characters")
    private String address;

    @NotNull(message = "City cannot be null")
    @NotBlank(message = "City cannot be blank")
    @Size(max = 30, message = "City cannot exceed 30 characters")
    private String city;

    @NotNull(message = "Name cannot be null")
    @NotBlank(message = "Name cannot be blank")
    @Size(max = 150, message = "Name cannot exceed 150 characters")
    private String name;

    @NotNull(message = "Region cannot be null")
    @NotBlank(message = "Region cannot be blank")
    @Size(max = 30, message = "Region cannot exceed 30 characters")
    private String region;

    @NotNull(message = "Phone Number cannot be null")
    @NotBlank(message = "Phone Number cannot be blank")
    @Size(max = 15, message = "Phone Number cannot exceed 15 characters")
    private String phoneNumber;

    @NotNull(message = "Whatsapp cannot be null")
    @NotBlank(message = "Whatsapp cannot be blank")
    @Size(max = 15, message = "Whatsapp cannot exceed 15 characters")
    private String whatsapp;

    private Long nEmployees;

    @Size(max = 20, message = "Color cannot exceed 20 characters")
    private String color;

    @Size(max = 5, message = "Acronym cannot exceed 5 characters")
    private String acronym;

    private Long level;

    @NotNull(message = "Website cannot be null")
    @NotBlank(message = "Website cannot be blank")
    @Size(max = 100, message = "Website cannot exceed 100 characters")
    private String website;

    @NotNull(message = "Company Rut cannot be null")
    @NotBlank(message = "Company Rut cannot be blank")
    @Size(max = 10, message = "Company Rut cannot exceed 10 characters")
    @ValidRut
    private String companyRut;

    @NotNull(message = "Company name cannot be null")
    @NotBlank(message = "Company name cannot be blank")
    @Size(max = 50, message = "Company name cannot exceed 50 characters")
    private String companyName;

    private Long nProcesses;

    private Date createdAt;

    private Date updatedAt;

    private ManagerDTO manager;

    private AuditorDTO auditor;
}
