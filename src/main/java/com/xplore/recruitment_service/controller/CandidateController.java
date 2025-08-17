package com.xplore.recruitment_service.controller;

import com.xplore.recruitment_service.entity.Candidate;
import com.xplore.recruitment_service.service.CandidateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/candidates")
public class CandidateController {
    private final CandidateService candidateService;

    @Autowired
    public CandidateController(CandidateService candidateService) {
        this.candidateService = candidateService;
    }

    @PostMapping
    public Candidate create(@RequestBody Candidate candidate) {
        return candidateService.addCandidate(candidate);
    }

    @GetMapping
    public List<Candidate> all() {
        return candidateService.listAllCandidates();
    }

    @GetMapping("/test")
    public List<String> test() {
        return List.of("working");
    }

}