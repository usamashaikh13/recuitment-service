package com.xplore.recruitment_service.service;

import com.xplore.recruitment_service.entity.JobOpening;
import com.xplore.recruitment_service.entity.JobStatus;
import com.xplore.recruitment_service.repository.JobOpeningRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JobOpeningService {
    private final JobOpeningRepository jobOpeningRepository;

    public JobOpeningService(JobOpeningRepository jobOpeningRepository) {
        this.jobOpeningRepository = jobOpeningRepository;
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
        existing.setStatus(jobOpening.getStatus());
        existing.setHiringManagerId(jobOpening.getHiringManagerId());
        existing.setRecruiterId(jobOpening.getRecruiterId());
        return jobOpeningRepository.save(existing);
    }

    public JobOpening updateStatus(Long id, JobStatus status) {
        JobOpening existing = getById(id);
        existing.setStatus(status);
        return jobOpeningRepository.save(existing);
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
