package com.xplore.recruitment_service.controller;

import com.xplore.recruitment_service.dto.ScheduleRequest;
import com.xplore.recruitment_service.entity.InterviewStatus;
import com.xplore.recruitment_service.entity.Recruitment;
import com.xplore.recruitment_service.service.RecruitmentService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/recruitments")
public class RecruitmentController {
    private final RecruitmentService recruitmentService;

    public RecruitmentController(RecruitmentService recruitmentService) {
        this.recruitmentService = recruitmentService;
    }

    @PostMapping("/schedule")
    public Recruitment schedule(@RequestBody ScheduleRequest request) {
        return recruitmentService.scheduleInterview(request);
    }

    @PatchMapping("/{id}/status")
    public Recruitment updateStatus(
            @PathVariable Long id,
            @RequestParam InterviewStatus status) {
        return recruitmentService.updateStatus(id, status);
    }

    @GetMapping
    public List<Recruitment> getAll() {
        return recruitmentService.listAllRecruitments();
    }

    @GetMapping("/candidate/{candidateId}")
    public List<Recruitment> byCandidate(@PathVariable Long candidateId) {
        return recruitmentService.getInterviewsByCandidateId(candidateId);
    }

    @GetMapping("/interviewer/{interviewerId}")
    public List<Recruitment> byInterviewer(@PathVariable Long interviewerId) {
        return recruitmentService.getInterviewsByInterviewerId(interviewerId);
    }

    @GetMapping("/load/{interviewerId}")
    public int getInterviewerLoad(@PathVariable Long interviewerId) {
        return recruitmentService.getLoadForInterviewer(interviewerId);
    }
}