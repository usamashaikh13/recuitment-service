package com.xplore.recruitment_service.controller;

import com.xplore.recruitment_service.entity.JobOpening;
import com.xplore.recruitment_service.entity.JobTemplate;
import com.xplore.recruitment_service.service.JobTemplateService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/job-templates")
public class JobTemplateController {
    private final JobTemplateService jobTemplateService;

    public JobTemplateController(JobTemplateService jobTemplateService) {
        this.jobTemplateService = jobTemplateService;
    }

    @PostMapping
    public JobTemplate create(@RequestBody JobTemplate template) {
        return jobTemplateService.create(template);
    }

    @GetMapping
    public List<JobTemplate> list() {
        return jobTemplateService.list();
    }

    @PostMapping("/{id}/jobs")
    public JobOpening createJob(@PathVariable Long id, @RequestBody JobOpening overrides) {
        return jobTemplateService.createJobFromTemplate(id, overrides);
    }
}
