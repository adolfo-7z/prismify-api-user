package com.ufro.dci.etransparency.etransparency_api_user.models.institution;

import java.util.*;
import com.ufro.dci.etransparency.etransparency_api_user.models.auditor.Auditor;
import com.ufro.dci.etransparency.etransparency_api_user.models.manager.Manager;
import com.ufro.dci.etransparency.etransparency_api_user.models.process.Process;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
@Cacheable
@Table(indexes = {
    @Index(name = "idx_institution_manager", columnList = "manager"),
        @Index(name = "idx_institution_auditor", columnList = "auditor"),
        @Index(name = "idx_institution_level", columnList = "level"),
        @Index(name = "idx_institution_name", columnList = "name")
})
public class Institution {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private boolean isActive;

    @NotNull
    @Column(length = 50)
    private String address;

    @NotNull
    @Column(length = 30)
    private String city;

    @NotNull
    @Column(length = 150)
    private String name;

    @NotNull
    @Column(length = 50)
    private String region;

    @NotNull
    @Column(length = 15)
    private String phoneNumber;

    @NotNull
    @Column(length = 15)
    private String whatsapp;

    private Long nEmployees;

    @Column(length = 20)
    private String color;

    @Column(length = 5)
    private String acronym;

    private Long level;

    @NotNull
    @Column(length = 100)
    private String website;

    @NotNull
    @Column(length = 10)
    private String companyRut;

    @NotNull
    @Column(length = 50)
    private String companyName;

    private Long nProcesses;

    private Date createdAt;

    private Date updatedAt;

    @OneToOne
    @JoinColumn(name = "manager_id", referencedColumnName = "id")
    private Manager manager;

    @ManyToOne
    @JoinColumn(name = "auditor_id")
    private Auditor auditor;

    @OneToMany(mappedBy = "institution", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Process> processes;

}
