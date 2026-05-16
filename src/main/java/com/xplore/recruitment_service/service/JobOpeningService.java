package com.xplore.recruitment_service.service;

import com.xplore.recruitment_service.entity.JobOpening;
import com.xplore.recruitment_service.entity.JobStatus;
import com.xplore.recruitment_service.repository.JobOpeningRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class JobOpeningService {
    private final JobOpeningRepository jobOpeningRepository;
    private final WebhookEventService webhookEventService;

    public JobOpeningService(JobOpeningRepository jobOpeningRepository, WebhookEventService webhookEventService) {
        this.jobOpeningRepository = jobOpeningRepository;
        this.webhookEventService = webhookEventService;
    }

    public JobOpening create(JobOpening jobOpening) {
        if (jobOpening.getStatus() == null) {
            jobOpening.setStatus(JobStatus.DRAFT);
        }
        return jobOpeningRepository.save(jobOpening);
    }

    public JobOpening update(Long id, JobOpening jobOpening) {
        JobOpening existing = getById(id);
        existing.setTitle(jobOpening.getTitle());
        existing.setDepartment(jobOpening.getDepartment());
        existing.setLocation(jobOpening.getLocation());
        existing.setEmploymentType(jobOpening.getEmploymentType());
        existing.setMinExperience(jobOpening.getMinExperience());
        existing.setMaxExperience(jobOpening.getMaxExperience());
        existing.setRequiredSkills(jobOpening.getRequiredSkills());
        existing.setDescription(jobOpening.getDescription());
        existing.setSalaryRange(jobOpening.getSalaryRange());
        existing.setHeadcount(jobOpening.getHeadcount());
        existing.setFilledCount(jobOpening.getFilledCount());
        existing.setStatus(jobOpening.getStatus());
        existing.setHiringManagerId(jobOpening.getHiringManagerId());
        existing.setRecruiterId(jobOpening.getRecruiterId());
        existing.setApprovedBy(jobOpening.getApprovedBy());
        existing.setApprovedAt(jobOpening.getApprovedAt());
        existing.setExpiresAt(jobOpening.getExpiresAt());
        return jobOpeningRepository.save(existing);
    }

    public JobOpening updateStatus(Long id, JobStatus status) {
        JobOpening existing = getById(id);
        existing.setStatus(status);
        JobOpening saved = jobOpeningRepository.save(existing);
        webhookEventService.publish("job.status_changed", "job", saved.getId(), "status=" + status);
        return saved;
    }

    public JobOpening requestApproval(Long id) {
        return updateStatus(id, JobStatus.PENDING_APPROVAL);
    }

    public JobOpening approve(Long id, Long approverId) {
        JobOpening existing = getById(id);
        existing.setApprovedBy(approverId);
        existing.setApprovedAt(LocalDateTime.now());
        existing.setStatus(JobStatus.OPEN);
        JobOpening saved = jobOpeningRepository.save(existing);
        webhookEventService.publish("job.approved", "job", saved.getId(), "approvedBy=" + approverId);
        return saved;
    }

    public List<JobOpening> closeExpired() {
        List<JobOpening> expired = jobOpeningRepository.findByStatusAndExpiresAtBefore(JobStatus.OPEN, LocalDateTime.now());
        expired.forEach(job -> job.setStatus(JobStatus.CLOSED));
        return jobOpeningRepository.saveAll(expired);
    }

    public JobOpening getById(Long id) {
        return jobOpeningRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Job opening not found"));
    }

    public List<JobOpening> list(JobStatus status, Long recruiterId) {
        if (status != null) {
            return jobOpeningRepository.findByStatus(status);
        }
        if (recruiterId != null) {
            return jobOpeningRepository.findByRecruiterId(recruiterId);
        }
        return jobOpeningRepository.findAll();
    }
}
