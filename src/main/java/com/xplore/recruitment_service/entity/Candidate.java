package com.xplore.recruitment_service.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Table(name = "candidates")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Candidate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "phone")
    private String phone;

    @Column(name = "current_company")
    private String currentCompany;

    @Column(name = "current_designation")
    private String currentDesignation;

    @Column(name = "years_experience")
    private Double yearsExperience;

    @ElementCollection
    @CollectionTable(name = "candidate_skills", joinColumns = @JoinColumn(name = "candidate_id"))
    @Column(name = "skill")
    private Set<String> skills;

    @ElementCollection
    @CollectionTable(name = "candidate_tags", joinColumns = @JoinColumn(name = "candidate_id"))
    @Column(name = "tag")
    private Set<String> tags;

    @Column(name = "resume_url")
    private String resumeUrl;

    @Column(name = "portfolio_url")
    private String portfolioUrl;

    @Column(name = "linkedin_url")
    private String linkedinUrl;

    @Column(name = "source")
    private String source;

    @Column(name = "notes", length = 2000)
    private String notes;
}
