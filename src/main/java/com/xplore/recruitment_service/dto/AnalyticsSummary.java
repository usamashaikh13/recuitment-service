package com.xplore.recruitment_service.dto;

import java.util.Map;

public class AnalyticsSummary {
    private Map<String, Long> funnelByStage;
    private Double averageTimeToHireDays;
    private double offerAcceptanceRate;

    public Map<String, Long> getFunnelByStage() {
        return funnelByStage;
    }

    public void setFunnelByStage(Map<String, Long> funnelByStage) {
        this.funnelByStage = funnelByStage;
    }

    public Double getAverageTimeToHireDays() {
        return averageTimeToHireDays;
    }

    public void setAverageTimeToHireDays(Double averageTimeToHireDays) {
        this.averageTimeToHireDays = averageTimeToHireDays;
    }

    public double getOfferAcceptanceRate() {
        return offerAcceptanceRate;
    }

    public void setOfferAcceptanceRate(double offerAcceptanceRate) {
        this.offerAcceptanceRate = offerAcceptanceRate;
    }
}
