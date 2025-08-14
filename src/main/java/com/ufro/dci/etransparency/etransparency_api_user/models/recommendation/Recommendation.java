package com.ufro.dci.etransparency.etransparency_api_user.models.recommendation;

import java.util.*;
import com.ufro.dci.etransparency.etransparency_api_user.models.process.Process;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

        @ManyToMany(mappedBy = "recommendations")
        private List<Process> processes;

}
