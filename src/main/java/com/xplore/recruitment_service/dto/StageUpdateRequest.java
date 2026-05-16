package com.xplore.recruitment_service.dto;

import com.xplore.recruitment_service.entity.ApplicationStage;

public class StageUpdateRequest {
    private ApplicationStage stage;
    private String notes;
    private String rejectionReason;

    public ApplicationStage getStage() {
        return stage;
    }

    public void setStage(ApplicationStage stage) {
        this.stage = stage;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getRejectionReason() {
        return rejectionReason;
    }

    public void setRejectionReason(String rejectionReason) {
        this.rejectionReason = rejectionReason;
    }
}
