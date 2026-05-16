package com.xplore.recruitment_service.controller;

import com.xplore.recruitment_service.entity.JobOpening;
import com.xplore.recruitment_service.entity.JobStatus;
import com.xplore.recruitment_service.service.JobOpeningService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/jobs")
public class JobOpeningController {
    private final JobOpeningService jobOpeningService;

    public JobOpeningController(JobOpeningService jobOpeningService) {
        this.jobOpeningService = jobOpeningService;
    }

    @PostMapping
    public JobOpening create(@RequestBody JobOpening jobOpening) {
        return jobOpeningService.create(jobOpening);
    }

    @PutMapping("/{id}")
    public JobOpening update(@PathVariable Long id, @RequestBody JobOpening jobOpening) {
        return jobOpeningService.update(id, jobOpening);
    }

    @PatchMapping("/{id}/status")
    public JobOpening updateStatus(@PathVariable Long id, @RequestParam JobStatus status) {
        return jobOpeningService.updateStatus(id, status);
    }

    @PatchMapping("/{id}/request-approval")
    public JobOpening requestApproval(@PathVariable Long id) {
        return jobOpeningService.requestApproval(id);
    }

    @PatchMapping("/{id}/approve")
    public JobOpening approve(@PathVariable Long id, @RequestParam Long approverId) {
        return jobOpeningService.approve(id, approverId);
    }

    @PostMapping("/close-expired")
    public List<JobOpening> closeExpired() {
        return jobOpeningService.closeExpired();
    }

    @GetMapping("/{id}")
    public JobOpening getById(@PathVariable Long id) {
        return jobOpeningService.getById(id);
    }

    @GetMapping
    public List<JobOpening> list(
            @RequestParam(required = false) JobStatus status,
            @RequestParam(required = false) Long recruiterId) {
        return jobOpeningService.list(status, recruiterId);
    }
}
