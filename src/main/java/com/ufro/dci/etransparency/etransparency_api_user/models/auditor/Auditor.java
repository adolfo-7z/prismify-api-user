package com.ufro.dci.etransparency.etransparency_api_user.models.auditor;

import java.util.*;
import com.ufro.dci.etransparency.etransparency_api_user.models.UserEntity;
import com.ufro.dci.etransparency.etransparency_api_user.models.institution.Institution;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Auditor extends UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long nAssignedInstitutions;

    private Long nAuditsPerformed;

    private Date createdAt;

    private Date updatedAt;

    @NotNull
    @Column(length = 30)
    private String city;

    @Column(length = 20)
    private String color;

    @Column(length = 5)
    private String acronym;

    @OneToMany(mappedBy = "auditor", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH}, orphanRemoval = false)
    private List<Institution> institutions;
}
