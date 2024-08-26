package com.ufro.dci.etransparency.etransparency_api_user.models.request;

import java.util.*;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
public class InstitutionRequest {

    public enum RequestStatus {
        UNREAD, READ
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(length = 150)
    private String institutionName;

    @NotNull
    @Column(length = 15)
    private String phoneNumber;

    @NotNull
    @Column(length = 100)
    private String managerEmail;

    @Enumerated(EnumType.STRING)
    private RequestStatus requestStatus;

    private Date createdAt;

    private Date updatedAt;

}
