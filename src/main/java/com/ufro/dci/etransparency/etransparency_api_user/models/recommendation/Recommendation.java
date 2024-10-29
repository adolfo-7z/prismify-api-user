package com.ufro.dci.etransparency.etransparency_api_user.models.recommendation;

import java.util.*;
import com.ufro.dci.etransparency.etransparency_api_user.models.process.Process;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(indexes = {
        @Index(name = "idx_recommendation_current_level", columnList = "currentLevel")
})
public class Recommendation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String dimension;

    private Long currentLevel;

    private Long targetLevel;

    @Column(columnDefinition = "TEXT")
    private String description;

    private Date createdAt;

    private Date updatedAt;

    @ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH,
            CascadeType.REFRESH })
    @JoinTable(name = "process_recommendation", joinColumns = @JoinColumn(name = "process_id"), inverseJoinColumns = @JoinColumn(name = "recommendation_id"))
    private List<Process> processes;

}
