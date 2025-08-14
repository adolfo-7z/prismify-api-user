package com.ufro.dci.etransparency.etransparency_api_user.models.dimension;

import java.util.*;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import com.ufro.dci.etransparency.etransparency_api_user.models.evidence.Evidence;
import com.ufro.dci.etransparency.etransparency_api_user.models.level.Level;
import com.ufro.dci.etransparency.etransparency_api_user.models.maturity.MaturityModel;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Dimension {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private boolean isActive;

    @NotNull
    @Column(length = 50)
    private String name;

    @NotNull
    @Column(length = 150)
    private String description;

    @ElementCollection
    @CollectionTable(name = "evidence_requirements", joinColumns = @JoinColumn(name = "dimension_id"))
    private List<EvidenceRequirement> evidenceRequirements;

    @ManyToOne
    @JoinColumn(name = "maturity_model_id")
    private MaturityModel maturityModel;

    @OneToMany(mappedBy = "dimension", cascade = { CascadeType.ALL }, orphanRemoval = true)
    private List<Level> levels;

    @OneToMany(mappedBy = "dimension", cascade = { CascadeType.ALL }, orphanRemoval = true)
    private List<Evidence> evidence;
}
