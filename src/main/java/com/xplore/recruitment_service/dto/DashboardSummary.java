package com.xplore.recruitment_service.dto;

import java.util.Map;

public class DashboardSummary {
    private long totalCandidates;
    private long openJobs;
    private long totalApplications;
    private long scheduledInterviews;
    private long pendingOffers;
    private Map<String, Long> applicationsByStage;

    public long getTotalCandidates() {
        return totalCandidates;
    }

    public void setTotalCandidates(long totalCandidates) {
        this.totalCandidates = totalCandidates;
    }

    public long getOpenJobs() {
        return openJobs;
    }

    public void setOpenJobs(long openJobs) {
        this.openJobs = openJobs;
    }

    public long getTotalApplications() {
        return totalApplications;
    }

    public void setTotalApplications(long totalApplications) {
        this.totalApplications = totalApplications;
    }

    public long getScheduledInterviews() {
        return scheduledInterviews;
    }

    public void setScheduledInterviews(long scheduledInterviews) {
        this.scheduledInterviews = scheduledInterviews;
    }

    public long getPendingOffers() {
        return pendingOffers;
    }

    public void setPendingOffers(long pendingOffers) {
        this.pendingOffers = pendingOffers;
    }

    public Map<String, Long> getApplicationsByStage() {
        return applicationsByStage;
    }

    public void setApplicationsByStage(Map<String, Long> applicationsByStage) {
        this.applicationsByStage = applicationsByStage;
    }
}
