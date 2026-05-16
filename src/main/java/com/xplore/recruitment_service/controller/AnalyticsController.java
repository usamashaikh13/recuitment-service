package com.xplore.recruitment_service.controller;

import com.xplore.recruitment_service.dto.AnalyticsSummary;
import com.xplore.recruitment_service.service.AnalyticsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/analytics")
public class AnalyticsController {
    private final AnalyticsService analyticsService;

    public AnalyticsController(AnalyticsService analyticsService) {
        this.analyticsService = analyticsService;
    }

    @GetMapping("/summary")
    public AnalyticsSummary summary() {
        return analyticsService.summary();
    }

    @GetMapping("/funnel")
    public Map<String, Long> funnel() {
        return analyticsService.funnel();
    }
}
