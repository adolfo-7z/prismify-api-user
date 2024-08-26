package com.ufro.dci.etransparency.etransparency_api_user.models.level;

import java.util.*;
import com.ufro.dci.etransparency.etransparency_api_user.models.dimension.Dimension;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Level {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private boolean isActive;

    @NotNull
    @Column(length = 50)
    private String name;

    @ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH,
            CascadeType.REFRESH })
    @JoinTable(name = "dimension_level", joinColumns = @JoinColumn(name = "level_id"), inverseJoinColumns = @JoinColumn(name = "dimension_id"))
    private List<Dimension> dimensions;
}
