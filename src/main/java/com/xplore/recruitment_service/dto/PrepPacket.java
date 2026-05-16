package com.xplore.recruitment_service.dto;

import com.xplore.recruitment_service.entity.Candidate;
import com.xplore.recruitment_service.entity.JobApplication;
import com.xplore.recruitment_service.entity.JobOpening;
import com.xplore.recruitment_service.entity.Recruitment;

public class PrepPacket {
    private Recruitment recruitment;
    private Candidate candidate;
    private JobApplication application;
    private JobOpening job;

    public Recruitment getRecruitment() {
        return recruitment;
    }

    public void setRecruitment(Recruitment recruitment) {
        this.recruitment = recruitment;
    }

    public Candidate getCandidate() {
        return candidate;
    }

    public void setCandidate(Candidate candidate) {
        this.candidate = candidate;
    }

    public JobApplication getApplication() {
        return application;
    }

    public void setApplication(JobApplication application) {
        this.application = application;
    }

    public JobOpening getJob() {
        return job;
    }

    public void setJob(JobOpening job) {
        this.job = job;
    }
}
