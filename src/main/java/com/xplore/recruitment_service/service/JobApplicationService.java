package com.xplore.recruitment_service.service;

import com.xplore.recruitment_service.dto.StageUpdateRequest;
import com.xplore.recruitment_service.entity.ApplicationStage;
import com.xplore.recruitment_service.entity.JobApplication;
import com.xplore.recruitment_service.entity.JobStatus;
import com.xplore.recruitment_service.repository.CandidateRepository;
import com.xplore.recruitment_service.repository.JobApplicationRepository;
import com.xplore.recruitment_service.repository.JobOpeningRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JobApplicationService {
    private final JobApplicationRepository applicationRepository;
    private final JobOpeningRepository jobOpeningRepository;
    private final CandidateRepository candidateRepository;
    private final NotificationService notificationService;
    private final WebhookEventService webhookEventService;

    public JobApplicationService(
            JobApplicationRepository applicationRepository,
            JobOpeningRepository jobOpeningRepository,
            CandidateRepository candidateRepository,
            NotificationService notificationService,
            WebhookEventService webhookEventService) {
        this.applicationRepository = applicationRepository;
        this.jobOpeningRepository = jobOpeningRepository;
        this.candidateRepository = candidateRepository;
        this.notificationService = notificationService;
        this.webhookEventService = webhookEventService;
    }

    public JobApplication apply(JobApplication application) {
        candidateRepository.findById(application.getCandidateId())
                .orElseThrow(() -> new RuntimeException("Candidate not found"));

        var job = jobOpeningRepository.findById(application.getJobId())
                .orElseThrow(() -> new RuntimeException("Job opening not found"));
        if (job.getStatus() != JobStatus.OPEN) {
            throw new RuntimeException("Candidates can only apply to OPEN jobs");
        }

        applicationRepository.findByJobIdAndCandidateId(
                application.getJobId(), application.getCandidateId()
        ).ifPresent(existing -> {
            throw new RuntimeException("Candidate has already applied to this job");
        });

        if (application.getStage() == null) {
            application.setStage(ApplicationStage.APPLIED);
        }
        JobApplication saved = applicationRepository.save(application);
        webhookEventService.publish("application.created", "application", saved.getId(),
                "candidateId=" + saved.getCandidateId() + ",jobId=" + saved.getJobId());
        return saved;
    }

    public JobApplication updateStage(Long id, StageUpdateRequest request) {
        JobApplication application = getById(id);
        application.setStage(request.getStage());
        if (request.getNotes() != null) {
            application.setScreeningNotes(request.getNotes());
        }
        if (request.getRejectionReason() != null) {
            application.setRejectionReason(request.getRejectionReason());
        }
        JobApplication saved = applicationRepository.save(application);
        candidateRepository.findById(saved.getCandidateId())
                .ifPresent(candidate -> notificationService.notifyStageChanged(candidate, saved.getStage()));
        webhookEventService.publish("application.stage_changed", "application", saved.getId(),
                "stage=" + saved.getStage());
        return saved;
    }

    public JobApplication getById(Long id) {
        return applicationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Application not found"));
    }

    public List<JobApplication> list(Long jobId, Long candidateId, ApplicationStage stage) {
        if (jobId != null) {
            return applicationRepository.findByJobId(jobId);
        }
        if (candidateId != null) {
            return applicationRepository.findByCandidateId(candidateId);
        }
        if (stage != null) {
            return applicationRepository.findByStage(stage);
        }
        return applicationRepository.findAll();
    }
}
