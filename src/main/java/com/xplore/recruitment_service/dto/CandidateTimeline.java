package com.xplore.recruitment_service.dto;

import com.xplore.recruitment_service.entity.Candidate;
import com.xplore.recruitment_service.entity.JobApplication;
import com.xplore.recruitment_service.entity.Offer;
import com.xplore.recruitment_service.entity.Recruitment;

import java.util.List;

public class CandidateTimeline {
    private Candidate candidate;
    private List<JobApplication> applications;
    private List<Recruitment> interviews;
    private List<Offer> offers;

    public Candidate getCandidate() {
        return candidate;
    }

    public void setCandidate(Candidate candidate) {
        this.candidate = candidate;
    }

    public List<JobApplication> getApplications() {
        return applications;
    }

    public void setApplications(List<JobApplication> applications) {
        this.applications = applications;
    }

    public List<Recruitment> getInterviews() {
        return interviews;
    }

    public void setInterviews(List<Recruitment> interviews) {
        this.interviews = interviews;
    }

    public List<Offer> getOffers() {
        return offers;
    }

    public void setOffers(List<Offer> offers) {
        this.offers = offers;
    }
}
