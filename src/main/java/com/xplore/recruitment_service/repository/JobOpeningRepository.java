package com.xplore.recruitment_service.repository;

import com.xplore.recruitment_service.entity.JobOpening;
import com.xplore.recruitment_service.entity.JobStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JobOpeningRepository extends JpaRepository<JobOpening, Long> {
    List<JobOpening> findByStatus(JobStatus status);
    List<JobOpening> findByRecruiterId(Long recruiterId);
}
