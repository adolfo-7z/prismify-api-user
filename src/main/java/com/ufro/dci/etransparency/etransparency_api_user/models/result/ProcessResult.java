package com.ufro.dci.etransparency.etransparency_api_user.models.result;

import java.util.Date;
import java.util.List;

import com.ufro.dci.etransparency.etransparency_api_user.models.process.Process;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

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

    @ElementCollection
    @CollectionTable(name = "cluster_result", joinColumns = @JoinColumn(name = "process_result_id"))
    private List<ClusterResult> clusters;

    @ElementCollection
    @CollectionTable(name = "response_average", joinColumns = @JoinColumn(name = "process_result_id"))
    private List<ResponseAverage> responseAverages;

}
