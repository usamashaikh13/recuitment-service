package com.xplore.recruitment_service.dto;

import java.util.Set;

public class ScheduleRequest {
    private Long candidateId;
    private Set<String> requiredSkills;
    private int minYearsExperience;
    private String round; // L1 or L2

    // Getters and setters
    public Long getCandidateId() { return candidateId; }
    public void setCandidateId(Long candidateId) { this.candidateId = candidateId; }

    public Set<String> getRequiredSkills() { return requiredSkills; }
    public void setRequiredSkills(Set<String> requiredSkills) { this.requiredSkills = requiredSkills; }

    public int getMinYearsExperience() { return minYearsExperience; }
    public void setMinYearsExperience(int minYearsExperience) { this.minYearsExperience = minYearsExperience; }

    public String getRound() { return round; }
    public void setRound(String round) { this.round = round; }
}