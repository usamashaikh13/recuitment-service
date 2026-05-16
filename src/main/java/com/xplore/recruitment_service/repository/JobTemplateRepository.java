package com.xplore.recruitment_service.repository;

import com.xplore.recruitment_service.entity.JobTemplate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobTemplateRepository extends JpaRepository<JobTemplate, Long> {
}
