package com.ufro.dci.etransparency.etransparency_api_user.dtos.institution;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InstitutionUpdateDTO {

    private Long id;

    @JsonIgnore
    private boolean isActive = true;

    @Size(max = 50, message = "Address cannot exceed 50 characters")
    private String address;

    @Size(max = 30, message = "City cannot exceed 30 characters")
    private String city;

    @Size(max = 150, message = "Name cannot exceed 150 characters")
    private String name;

    @Size(max = 30, message = "Region cannot exceed 30 characters")
    private String region;

    @Size(max = 15, message = "Phone Number cannot exceed 15 characters")
    private String phoneNumber;

    @Size(max = 15, message = "Whatsapp cannot exceed 15 characters")
    private String whatsapp;

    private Long nEmployees;

    @Size(max = 20, message = "Color cannot exceed 20 characters")
    private String color;

    @Size(max = 5, message = "Acronym cannot exceed 5 characters")
    private String acronym;

    private Long level;

    @Size(max = 100, message = "Website cannot exceed 100 characters")
    private String website;

    @Size(max = 10, message = "Company Rut cannot exceed 10 characters")
    private String companyRut;

    @Size(max = 50, message = "Company name cannot exceed 50 characters")
    private String companyName;
    
}
