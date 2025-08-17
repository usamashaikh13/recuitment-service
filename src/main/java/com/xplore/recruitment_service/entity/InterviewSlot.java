package com.xplore.recruitment_service.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "interview_slots")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class InterviewSlot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "interviewer_id", nullable = false)
    private Long interviewerId;

    @Column(name = "interviewer_name", nullable = false)
    private String interviewerName;

    @ElementCollection
    @CollectionTable(name = "slot_skills", joinColumns = @JoinColumn(name = "slot_id"))
    @Column(name = "skill")
    private Set<String> technicalSkills;

    @Column(name = "min_years_experience", nullable = false)
    private int minYearsExperience;

    @Column(name = "start_time", nullable = false)
    private LocalDateTime startTime;

    @Column(name = "duration_minutes", nullable = false)
    private int durationMinutes;

    @Column(name = "round", nullable = false)
    private String round;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private SlotStatus status;

    @Column(name = "booked_candidate_id")
    private Long bookedCandidateId;
}
