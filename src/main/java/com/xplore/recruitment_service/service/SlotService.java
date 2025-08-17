package com.xplore.recruitment_service.service;

import com.xplore.recruitment_service.dto.InterviewSlotResponse;
import com.xplore.recruitment_service.entity.InterviewSlot;
import com.xplore.recruitment_service.entity.SlotStatus;
import com.xplore.recruitment_service.repository.SlotRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.*;

@Service
public class SlotService {
    private final SlotRepository repo;

    public SlotService(SlotRepository repo) {
        this.repo = repo;
    }

    public InterviewSlot createSlot(InterviewSlot slot) {
        slot.setStatus(SlotStatus.AVAILABLE);
        return repo.save(slot);
    }

    public List<InterviewSlotResponse> getAllSlots() {
        return repo.findAll().stream().map(InterviewSlotResponse::fromEntity).collect(Collectors.toList());
    }

    public List<InterviewSlotResponse> findAvailable(Set<String> skills, int minExp, String round) {
        return repo.findByStatusAndRoundAndMinYearsExperienceLessThanEqual(
                        SlotStatus.AVAILABLE, round, minExp)
                .stream()
                .filter(s -> s.getTechnicalSkills().containsAll(skills))
                .map(InterviewSlotResponse::fromEntity)
                .collect(Collectors.toList());
    }

    public InterviewSlotResponse book(Long id, Long candidateId) {
        InterviewSlot slot = repo.findById(id).orElseThrow(() -> new RuntimeException("Slot not found"));
        if (slot.getStatus() != SlotStatus.AVAILABLE) throw new IllegalStateException("Slot not available");
        slot.setStatus(SlotStatus.BOOKED);
        slot.setBookedCandidateId(candidateId);
        return InterviewSlotResponse.fromEntity(repo.save(slot));
    }
}
