package com.xplore.recruitment_service.controller;

import com.xplore.recruitment_service.dto.StageUpdateRequest;
import com.xplore.recruitment_service.entity.ApplicationStage;
import com.xplore.recruitment_service.entity.JobApplication;
import com.xplore.recruitment_service.service.JobApplicationService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/applications")
public class JobApplicationController {
    private final JobApplicationService applicationService;

    public JobApplicationController(JobApplicationService applicationService) {
        this.applicationService = applicationService;
    }

    @PostMapping
    public JobApplication apply(@RequestBody JobApplication application) {
        return applicationService.apply(application);
    }

    @PatchMapping("/{id}/stage")
    public JobApplication updateStage(@PathVariable Long id, @RequestBody StageUpdateRequest request) {
        return applicationService.updateStage(id, request);
    }

    @GetMapping("/{id}")
    public JobApplication getById(@PathVariable Long id) {
        return applicationService.getById(id);
    }

    @GetMapping
    public List<JobApplication> list(
            @RequestParam(required = false) Long jobId,
            @RequestParam(required = false) Long candidateId,
            @RequestParam(required = false) ApplicationStage stage) {
        return applicationService.list(jobId, candidateId, stage);
    }
}
