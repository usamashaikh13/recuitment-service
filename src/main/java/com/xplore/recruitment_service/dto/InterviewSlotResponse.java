package com.xplore.recruitment_service.dto;

import com.xplore.recruitment_service.entity.InterviewSlot;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Set;

@Data
public class InterviewSlotResponse {
    private Long id;
    private Long interviewerId;
    private String interviewerName;
    private LocalDateTime startTime;
    private int durationMinutes;
    private String round;
    private Set<String> technicalSkills;

    public static InterviewSlotResponse fromEntity(InterviewSlot slot) {
        InterviewSlotResponse dto = new InterviewSlotResponse();
        dto.setId(slot.getId());
        dto.setInterviewerId(slot.getInterviewerId());
        dto.setInterviewerName(slot.getInterviewerName());
        dto.setStartTime(slot.getStartTime());
        dto.setDurationMinutes(slot.getDurationMinutes());
        dto.setRound(slot.getRound());
        dto.setTechnicalSkills(slot.getTechnicalSkills());
        return dto;
    }
}