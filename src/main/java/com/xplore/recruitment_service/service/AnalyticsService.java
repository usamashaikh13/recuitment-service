package com.xplore.recruitment_service.service;

import com.xplore.recruitment_service.dto.AnalyticsSummary;
import com.xplore.recruitment_service.entity.ApplicationStage;
import com.xplore.recruitment_service.entity.JobApplication;
import com.xplore.recruitment_service.entity.OfferStatus;
import com.xplore.recruitment_service.repository.JobApplicationRepository;
import com.xplore.recruitment_service.repository.OfferRepository;
import org.springframework.stereotype.Service;

import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class AnalyticsService {
    private final JobApplicationRepository applicationRepository;
    private final OfferRepository offerRepository;

    public AnalyticsService(JobApplicationRepository applicationRepository, OfferRepository offerRepository) {
        this.applicationRepository = applicationRepository;
        this.offerRepository = offerRepository;
    }

    public AnalyticsSummary summary() {
        AnalyticsSummary summary = new AnalyticsSummary();
        summary.setFunnelByStage(funnel());
        summary.setAverageTimeToHireDays(averageTimeToHireDays());
        summary.setOfferAcceptanceRate(offerAcceptanceRate());
        return summary;
    }

    public Map<String, Long> funnel() {
        return Arrays.stream(ApplicationStage.values())
                .collect(Collectors.toMap(Enum::name, stage -> (long) applicationRepository.findByStage(stage).size()));
    }

    public Double averageTimeToHireDays() {
        List<JobApplication> hired = applicationRepository.findByStage(ApplicationStage.HIRED);
        return hired.stream()
                .filter(application -> application.getAppliedAt() != null && application.getUpdatedAt() != null)
                .mapToLong(application -> ChronoUnit.DAYS.between(application.getAppliedAt(), application.getUpdatedAt()))
                .average()
                .stream()
                .boxed()
                .findFirst()
                .orElse(null);
    }

    public double offerAcceptanceRate() {
        long accepted = offerRepository.findByStatus(OfferStatus.ACCEPTED).size();
        long decided = accepted
                + offerRepository.findByStatus(OfferStatus.DECLINED).size()
                + offerRepository.findByStatus(OfferStatus.WITHDRAWN).size();
        if (decided == 0) {
            return 0;
        }
        return (accepted * 100.0) / decided;
    }
}
