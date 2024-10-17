package com.ufro.dci.etransparency.etransparency_api_user.models.result;

import java.util.*;
import com.ufro.dci.etransparency.etransparency_api_user.models.process.Process;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
public class ProcessResult {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private boolean isActive;

    private Double peopleAnswered;

    private List<Long> dimensionLevels;

    private Long institutionLevel;

    private Long daysPassed;

    private Long daysLeft;

    private Long answeredSurveys;

    private Long surveyQuorum;

    private Long totalSurveys;

    private Date estimatedCompletionDate;

    @OneToOne(mappedBy = "processResult")
    private Process process;

    @OneToMany(mappedBy = "processResult", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<ClusterResult> clusters;

    @ElementCollection
    @CollectionTable(name = "response_timestamps", joinColumns = @JoinColumn(name = "process_result_id"))
    private List<ResponseAverage> responseAverages;

}
