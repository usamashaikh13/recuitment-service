package com.xplore.recruitment_service.service;

import com.xplore.recruitment_service.entity.ApplicationStage;
import com.xplore.recruitment_service.entity.Offer;
import com.xplore.recruitment_service.entity.OfferStatus;
import com.xplore.recruitment_service.repository.CandidateRepository;
import com.xplore.recruitment_service.repository.JobApplicationRepository;
import com.xplore.recruitment_service.repository.JobOpeningRepository;
import com.xplore.recruitment_service.repository.OfferRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OfferService {
    private final OfferRepository offerRepository;
    private final JobApplicationRepository applicationRepository;
    private final CandidateRepository candidateRepository;
    private final JobOpeningRepository jobOpeningRepository;

    public OfferService(
            OfferRepository offerRepository,
            JobApplicationRepository applicationRepository,
            CandidateRepository candidateRepository,
            JobOpeningRepository jobOpeningRepository) {
        this.offerRepository = offerRepository;
        this.applicationRepository = applicationRepository;
        this.candidateRepository = candidateRepository;
        this.jobOpeningRepository = jobOpeningRepository;
    }

    public Offer create(Offer offer) {
        applicationRepository.findById(offer.getApplicationId())
                .orElseThrow(() -> new RuntimeException("Application not found"));
        candidateRepository.findById(offer.getCandidateId())
                .orElseThrow(() -> new RuntimeException("Candidate not found"));
        jobOpeningRepository.findById(offer.getJobId())
                .orElseThrow(() -> new RuntimeException("Job opening not found"));
        if (offer.getStatus() == null) {
            offer.setStatus(OfferStatus.DRAFT);
        }
        return offerRepository.save(offer);
    }

    public Offer update(Long id, Offer offer) {
        Offer existing = getById(id);
        existing.setTitle(offer.getTitle());
        existing.setSalary(offer.getSalary());
        existing.setCurrency(offer.getCurrency());
        existing.setJoiningDate(offer.getJoiningDate());
        existing.setStatus(offer.getStatus());
        existing.setNotes(offer.getNotes());
        return offerRepository.save(existing);
    }

    public Offer updateStatus(Long id, OfferStatus status) {
        Offer offer = getById(id);
        offer.setStatus(status);
        Offer saved = offerRepository.save(offer);
        applicationRepository.findById(offer.getApplicationId()).ifPresent(application -> {
            if (status == OfferStatus.SENT || status == OfferStatus.APPROVED) {
                application.setStage(ApplicationStage.OFFER);
            } else if (status == OfferStatus.ACCEPTED) {
                application.setStage(ApplicationStage.HIRED);
            } else if (status == OfferStatus.DECLINED || status == OfferStatus.WITHDRAWN) {
                application.setStage(ApplicationStage.WITHDRAWN);
            }
            applicationRepository.save(application);
        });
        return saved;
    }

    public Offer getById(Long id) {
        return offerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Offer not found"));
    }

    public List<Offer> list(Long candidateId, Long applicationId, OfferStatus status) {
        if (candidateId != null) {
            return offerRepository.findByCandidateId(candidateId);
        }
        if (applicationId != null) {
            return offerRepository.findByApplicationId(applicationId);
        }
        if (status != null) {
            return offerRepository.findByStatus(status);
        }
        return offerRepository.findAll();
    }
}
