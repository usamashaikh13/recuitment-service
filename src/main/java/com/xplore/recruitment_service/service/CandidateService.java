package com.xplore.recruitment_service.service;

import com.xplore.recruitment_service.entity.Candidate;
import com.xplore.recruitment_service.repository.CandidateRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CandidateService {
    private final CandidateRepository candidateRepository;

    public CandidateService(CandidateRepository candidateRepository) {
        this.candidateRepository = candidateRepository;
    }

    public Candidate addCandidate(Candidate candidate) {
        candidateRepository.findByEmail(candidate.getEmail()).ifPresent(existing -> {
            throw new RuntimeException("Candidate with email " + candidate.getEmail() + " already exists");
        });
        return candidateRepository.save(candidate);
    }

    public List<Candidate> listAllCandidates() {
        return candidateRepository.findAll();
    }

    public Candidate getCandidate(Long id) {
        return candidateRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Candidate not found"));
    }

    public Candidate updateCandidate(Long id, Candidate candidate) {
        Candidate existing = getCandidate(id);
        existing.setName(candidate.getName());
        existing.setEmail(candidate.getEmail());
        existing.setPhone(candidate.getPhone());
        existing.setCurrentCompany(candidate.getCurrentCompany());
        existing.setCurrentDesignation(candidate.getCurrentDesignation());
        existing.setYearsExperience(candidate.getYearsExperience());
        existing.setSkills(candidate.getSkills());
        existing.setResumeUrl(candidate.getResumeUrl());
        existing.setPortfolioUrl(candidate.getPortfolioUrl());
        existing.setLinkedinUrl(candidate.getLinkedinUrl());
        existing.setSource(candidate.getSource());
        existing.setNotes(candidate.getNotes());
        return candidateRepository.save(existing);
    }
}
