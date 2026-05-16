package com.xplore.recruitment_service.repository;

import com.xplore.recruitment_service.entity.ApplicationStage;
import com.xplore.recruitment_service.entity.JobApplication;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface JobApplicationRepository extends JpaRepository<JobApplication, Long> {
    List<JobApplication> findByJobId(Long jobId);
    List<JobApplication> findByCandidateId(Long candidateId);
    List<JobApplication> findByStage(ApplicationStage stage);
    Optional<JobApplication> findByJobIdAndCandidateId(Long jobId, Long candidateId);
}
