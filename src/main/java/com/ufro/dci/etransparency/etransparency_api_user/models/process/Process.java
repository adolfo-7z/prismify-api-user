package com.ufro.dci.etransparency.etransparency_api_user.models.process;

import java.util.Date;
import java.util.List;
import com.ufro.dci.etransparency.etransparency_api_user.models.evidence.Evidence;
import com.ufro.dci.etransparency.etransparency_api_user.models.institution.Institution;
import com.ufro.dci.etransparency.etransparency_api_user.models.maturity.MaturityModel;
import com.ufro.dci.etransparency.etransparency_api_user.models.recommendation.Recommendation;
import com.ufro.dci.etransparency.etransparency_api_user.models.result.ProcessResult;
import com.ufro.dci.etransparency.etransparency_api_user.models.survey.Survey;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
@Cacheable
@Table(indexes = {
        @Index(name = "idx_process_status", columnList = "status"),
        @Index(name = "idx_process_request_status", columnList = "requestStatus"),
        @Index(name = "idx_process_institution", columnList = "institution"),
        @Index(name = "idx_process_maturity_model", columnList = "maturityModel"),
        @Index(name = "idx_process_process_result", columnList = "processResult"),
})
public class Process {

    public enum ProcessStatus {
        UNINITIATED, IN_PROGRESS, REJECTED, AUDIT, APPEALABLE, APPEAL, FINISHED
    }

    public enum RequestStatus {
        UNREAD, READ, ACCEPTED, REJECTED
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private ProcessStatus status;

    @Enumerated(EnumType.STRING)
    private RequestStatus requestStatus;

    @NotNull
    @Column(length = 30)
    private String name;

    private Date startDate;

    private Date endDate;

    private Long step;

    @Column(columnDefinition = "TEXT")
    private String surveyLink;

    @Column(columnDefinition = "TEXT")
    private String employeesNames;

    @Column(columnDefinition = "TEXT")
    private String employeesEmails;

    @Column(columnDefinition = "TEXT")
    private String employeesRut;

    private Long maxLevelScore;

    private Long nEmployees;

    private Date createdAt;

    private Date updatedAt;

    @ElementCollection
    @CollectionTable(name = "process_milestones", joinColumns = @JoinColumn(name = "process_id"))
    @Column(name = "milestone")
    private List<String> milestones;

    @ManyToOne
    @JoinColumn(name = "institution_id", nullable = false)
    private Institution institution;

    @ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH })
    @JoinColumn(name = "maturity_model_id", referencedColumnName = "id")
    private MaturityModel maturityModel;

    @OneToOne(cascade = { CascadeType.ALL })
    @JoinColumn(name = "process_result_id", referencedColumnName = "id")
    private ProcessResult processResult;

    @OneToMany(mappedBy = "process", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Evidence> evidences;

    @ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH,
            CascadeType.REFRESH }, fetch = FetchType.LAZY)
    @JoinTable(name = "process_recommendation", joinColumns = @JoinColumn(name = "recommendation_id"), inverseJoinColumns = @JoinColumn(name = "process_id"))
    private List<Recommendation> recommendations;

    @OneToOne
    @JoinColumn(name = "survey_id")
    @ToString.Exclude
    private Survey survey;

}
