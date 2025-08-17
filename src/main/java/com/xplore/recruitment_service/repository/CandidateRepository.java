package com.xplore.recruitment_service.repository;

import com.xplore.recruitment_service.entity.Candidate;
import com.xplore.recruitment_service.entity.InterviewSlot;
import com.xplore.recruitment_service.entity.SlotStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CandidateRepository extends JpaRepository<Candidate, Long> {}


