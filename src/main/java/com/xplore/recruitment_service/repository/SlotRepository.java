package com.xplore.recruitment_service.repository;

import com.xplore.recruitment_service.entity.InterviewSlot;
import com.xplore.recruitment_service.entity.SlotStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SlotRepository extends JpaRepository<InterviewSlot, Long> {
    List<InterviewSlot> findByStatusAndRoundAndMinYearsExperienceLessThanEqual(
            SlotStatus status, String round, int years);
}
