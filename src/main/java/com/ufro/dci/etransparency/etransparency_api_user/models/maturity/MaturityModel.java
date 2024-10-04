package com.ufro.dci.etransparency.etransparency_api_user.models.maturity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import java.util.*;
import com.ufro.dci.etransparency.etransparency_api_user.models.dimension.Dimension;
import com.ufro.dci.etransparency.etransparency_api_user.models.process.Process;
import lombok.*;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class MaturityModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private boolean isActive;

    @NotNull
    @Column(length = 50)
    private String name;

    @Column(length = 150)
    private String description;

    @NotNull
    private Long timeLimit;

    private Double approvalPercentage;

    private Date createdAt;

    private Date updatedAt;

    @OneToMany(mappedBy = "maturityModel", cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH,
            CascadeType.REFRESH }, orphanRemoval = false)
    private List<Process> processes;

    @OneToMany(mappedBy = "maturityModel", cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH,
            CascadeType.REFRESH }, orphanRemoval = false)
    private List<Dimension> dimensions;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "file_id")
    private MaturityModelFile maturityModelFile;

}