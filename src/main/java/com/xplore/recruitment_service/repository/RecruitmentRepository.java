package com.xplore.recruitment_service.repository;

import com.xplore.recruitment_service.entity.Recruitment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RecruitmentRepository extends JpaRepository<Recruitment, Long> {
    List<Recruitment> findByCandidateId(Long candidateId);
    List<Recruitment> findByInterviewerId(Long interviewerId);
    List<Recruitment> findByApplicationId(Long applicationId);
    List<Recruitment> findByInterviewSlotId(Long interviewSlotId);
}
