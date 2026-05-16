package com.xplore.recruitment_service.entity;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Entity
@Table(name = "job_templates")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class JobTemplate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private String title;
    private String department;
    private String employmentType;
    private Integer minExperience;
    private Integer maxExperience;

    @ElementCollection
    @CollectionTable(name = "job_template_skills", joinColumns = @JoinColumn(name = "template_id"))
    @Column(name = "skill")
    private Set<String> requiredSkills;

    @Column(length = 5000)
    private String description;

    private String salaryRange;
}
