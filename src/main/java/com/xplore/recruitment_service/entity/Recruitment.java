package com.xplore.recruitment_service.entity;

import jakarta.persistence.*;
import lombok.*;           // add this

@Entity
@Table(name = "recruitments")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Recruitment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "interviewer_id", nullable = false)
    private Long interviewerId;

    @Column(name = "interview_slot_id", nullable = false)
    private Long interviewSlotId;

    @Column(name = "candidate_id", nullable = false)
    private Long candidateId;

    @Column(name = "application_id")
    private Long applicationId;

    @Column(name = "round", nullable = false)
    private String round;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private InterviewStatus status;

}
