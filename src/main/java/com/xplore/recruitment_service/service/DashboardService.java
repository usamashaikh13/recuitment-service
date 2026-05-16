package com.xplore.recruitment_service.service;

import com.xplore.recruitment_service.dto.DashboardSummary;
import com.xplore.recruitment_service.entity.ApplicationStage;
import com.xplore.recruitment_service.entity.InterviewStatus;
import com.xplore.recruitment_service.entity.JobStatus;
import com.xplore.recruitment_service.entity.OfferStatus;
import com.xplore.recruitment_service.repository.CandidateRepository;
import com.xplore.recruitment_service.repository.JobApplicationRepository;
import com.xplore.recruitment_service.repository.JobOpeningRepository;
import com.xplore.recruitment_service.repository.OfferRepository;
import com.xplore.recruitment_service.repository.RecruitmentRepository;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class DashboardService {
    private final CandidateRepository candidateRepository;
    private final JobOpeningRepository jobOpeningRepository;
    private final JobApplicationRepository applicationRepository;
    private final RecruitmentRepository recruitmentRepository;
    private final OfferRepository offerRepository;

    public DashboardService(
            CandidateRepository candidateRepository,
            JobOpeningRepository jobOpeningRepository,
            JobApplicationRepository applicationRepository,
            RecruitmentRepository recruitmentRepository,
            OfferRepository offerRepository) {
        this.candidateRepository = candidateRepository;
        this.jobOpeningRepository = jobOpeningRepository;
        this.applicationRepository = applicationRepository;
        this.recruitmentRepository = recruitmentRepository;
        this.offerRepository = offerRepository;
    }

    public DashboardSummary getSummary() {
        DashboardSummary summary = new DashboardSummary();
        summary.setTotalCandidates(candidateRepository.count());
        summary.setOpenJobs(jobOpeningRepository.findByStatus(JobStatus.OPEN).size());
        summary.setTotalApplications(applicationRepository.count());
        summary.setScheduledInterviews(recruitmentRepository.findAll().stream()
                .filter(recruitment -> recruitment.getStatus() == InterviewStatus.SCHEDULED)
                .count());
        summary.setPendingOffers(offerRepository.findAll().stream()
                .filter(offer -> offer.getStatus() == OfferStatus.APPROVAL_PENDING
                        || offer.getStatus() == OfferStatus.APPROVED
                        || offer.getStatus() == OfferStatus.SENT)
                .count());

        Map<String, Long> byStage = Arrays.stream(ApplicationStage.values())
                .collect(Collectors.toMap(Enum::name, stage -> (long) applicationRepository.findByStage(stage).size()));
        summary.setApplicationsByStage(byStage);
        return summary;
    }
}
