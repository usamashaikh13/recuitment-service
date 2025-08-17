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
        return candidateRepository.save(candidate);
    }

    public List<Candidate> listAllCandidates() {
        return candidateRepository.findAll();
    }
}