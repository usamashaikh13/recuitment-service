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

    public JobApplicationService(
            JobApplicationRepository applicationRepository,
            JobOpeningRepository jobOpeningRepository,
            CandidateRepository candidateRepository) {
        this.applicationRepository = applicationRepository;
        this.jobOpeningRepository = jobOpeningRepository;
        this.candidateRepository = candidateRepository;
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
        return applicationRepository.save(application);
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
        return applicationRepository.save(application);
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
