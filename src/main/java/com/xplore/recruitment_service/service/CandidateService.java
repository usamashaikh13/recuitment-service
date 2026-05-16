package com.xplore.recruitment_service.service;

import com.xplore.recruitment_service.dto.CandidateTimeline;
import com.xplore.recruitment_service.entity.Candidate;
import com.xplore.recruitment_service.repository.CandidateRepository;
import com.xplore.recruitment_service.repository.JobApplicationRepository;
import com.xplore.recruitment_service.repository.OfferRepository;
import com.xplore.recruitment_service.repository.RecruitmentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CandidateService {
    private final CandidateRepository candidateRepository;
    private final JobApplicationRepository applicationRepository;
    private final RecruitmentRepository recruitmentRepository;
    private final OfferRepository offerRepository;

    public CandidateService(
            CandidateRepository candidateRepository,
            JobApplicationRepository applicationRepository,
            RecruitmentRepository recruitmentRepository,
            OfferRepository offerRepository) {
        this.candidateRepository = candidateRepository;
        this.applicationRepository = applicationRepository;
        this.recruitmentRepository = recruitmentRepository;
        this.offerRepository = offerRepository;
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
        existing.setTags(candidate.getTags());
        existing.setResumeUrl(candidate.getResumeUrl());
        existing.setPortfolioUrl(candidate.getPortfolioUrl());
        existing.setLinkedinUrl(candidate.getLinkedinUrl());
        existing.setSource(candidate.getSource());
        existing.setNotes(candidate.getNotes());
        return candidateRepository.save(existing);
    }

    public List<Candidate> findDuplicates(String email, String phone) {
        if (email != null && !email.isBlank()) {
            return candidateRepository.findByEmail(email).map(List::of).orElseGet(List::of);
        }
        if (phone != null && !phone.isBlank()) {
            return candidateRepository.findByPhone(phone);
        }
        return List.of();
    }

    public CandidateTimeline getTimeline(Long candidateId) {
        CandidateTimeline timeline = new CandidateTimeline();
        timeline.setCandidate(getCandidate(candidateId));
        timeline.setApplications(applicationRepository.findByCandidateId(candidateId));
        timeline.setInterviews(recruitmentRepository.findByCandidateId(candidateId));
        timeline.setOffers(offerRepository.findByCandidateId(candidateId));
        return timeline;
    }
}
