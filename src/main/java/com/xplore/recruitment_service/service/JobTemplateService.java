package com.xplore.recruitment_service.service;

import com.xplore.recruitment_service.entity.JobOpening;
import com.xplore.recruitment_service.entity.JobStatus;
import com.xplore.recruitment_service.entity.JobTemplate;
import com.xplore.recruitment_service.repository.JobOpeningRepository;
import com.xplore.recruitment_service.repository.JobTemplateRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JobTemplateService {
    private final JobTemplateRepository templateRepository;
    private final JobOpeningRepository jobOpeningRepository;

    public JobTemplateService(JobTemplateRepository templateRepository, JobOpeningRepository jobOpeningRepository) {
        this.templateRepository = templateRepository;
        this.jobOpeningRepository = jobOpeningRepository;
    }

    public JobTemplate create(JobTemplate template) {
        return templateRepository.save(template);
    }

    public List<JobTemplate> list() {
        return templateRepository.findAll();
    }

    public JobOpening createJobFromTemplate(Long templateId, JobOpening overrides) {
        JobTemplate template = templateRepository.findById(templateId)
                .orElseThrow(() -> new RuntimeException("Job template not found"));
        JobOpening job = new JobOpening();
        job.setTitle(overrides.getTitle() != null ? overrides.getTitle() : template.getTitle());
        job.setDepartment(overrides.getDepartment() != null ? overrides.getDepartment() : template.getDepartment());
        job.setLocation(overrides.getLocation());
        job.setEmploymentType(template.getEmploymentType());
        job.setMinExperience(template.getMinExperience());
        job.setMaxExperience(template.getMaxExperience());
        job.setRequiredSkills(template.getRequiredSkills());
        job.setDescription(template.getDescription());
        job.setSalaryRange(template.getSalaryRange());
        job.setHeadcount(overrides.getHeadcount());
        job.setHiringManagerId(overrides.getHiringManagerId());
        job.setRecruiterId(overrides.getRecruiterId());
        job.setExpiresAt(overrides.getExpiresAt());
        job.setStatus(JobStatus.DRAFT);
        return jobOpeningRepository.save(job);
    }
}
