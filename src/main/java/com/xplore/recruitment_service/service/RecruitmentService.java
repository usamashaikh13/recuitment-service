package com.xplore.recruitment_service.service;

import com.xplore.recruitment_service.dto.InterviewSlotResponse;
import com.xplore.recruitment_service.dto.ScheduleRequest;
import com.xplore.recruitment_service.entity.Candidate;
import com.xplore.recruitment_service.entity.InterviewStatus;
import com.xplore.recruitment_service.entity.Recruitment;
import com.xplore.recruitment_service.repository.CandidateRepository;
import com.xplore.recruitment_service.repository.RecruitmentRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class RecruitmentService {
    private static final Logger logger = LoggerFactory.getLogger(RecruitmentService.class);

    private final RecruitmentRepository recruitmentRepo;
    private final CandidateRepository candidateRepo;
    private final RestTemplate restTemplate;
    private final EmailNotificationService emailNotificationService;

    @Value("${interviewer.service.url}")
    private String interviewerUrl;

    public Recruitment scheduleInterview(ScheduleRequest req) {
        // 1. Validate candidate
        Candidate cand = candidateRepo.findById(req.getCandidateId())
                .orElseThrow(() -> new RuntimeException("Candidate not found"));

        // 2. Fetch all matching slots
        String slotsUrl = String.format(
                "%s/available?skills=%s&minExperience=%d&round=%s",
                interviewerUrl,
                String.join(",", req.getRequiredSkills()),
                req.getMinYearsExperience(),
                req.getRound()
        );

        logger.info("Fetching available slots: {}", slotsUrl);
        ResponseEntity<List<InterviewSlotResponse>> resp = restTemplate.exchange(
                slotsUrl, HttpMethod.GET, null,
                new ParameterizedTypeReference<>() {
                }
        );
        List<InterviewSlotResponse> slots = resp.getBody();
        if (slots == null || slots.isEmpty()) {
            throw new RuntimeException("No available slots for automated matching");
        }

        // 3. Advanced scoring: combine earliest time, least load, skill match depth
        // Fetch load metrics for each interviewer (e.g., number of booked slots in past week)
        // For demo, we assign a score = (daysUntilSlot * 2) + bookedCount
        Map<InterviewSlotResponse, Integer> scores = new HashMap<>();
        for (InterviewSlotResponse slot : slots) {
            // days until slot
            long daysUntil = ChronoUnit.DAYS.between(LocalDate.now(), slot.getStartTime().toLocalDate());
            // dummy load; replace with real count retrieval
            int bookedCount = getInterviewerLoad(slot.getInterviewerId());
            int score = (int) daysUntil * 2 + bookedCount;
            scores.put(slot, score);
        }
        // pick slot with lowest score
        InterviewSlotResponse selected = scores.entrySet().stream()
                .min(Map.Entry.comparingByValue())
                .get()
                .getKey();

        // 4. Book selected slot
        String bookUrl = String.format(
                "%s/%d/book?candidateId=%d",
                interviewerUrl,  // already includes /api/slots
                selected.getId(),
                cand.getId()
        );

        logger.info("Booking selected slot: {}", bookUrl);
        restTemplate.postForObject(bookUrl, null, InterviewSlotResponse.class);

        // 5. Persist assignment
        Recruitment rec = new Recruitment();
        rec.setCandidateId(cand.getId());
        rec.setInterviewerId(selected.getInterviewerId());
        rec.setInterviewSlotId(selected.getId());
        rec.setRound(req.getRound());
        rec.setStatus(InterviewStatus.SCHEDULED);
        Recruitment saved = recruitmentRepo.save(rec);
        
        // 6. Send email notifications to candidate and interviewer
        try {
            emailNotificationService.sendInterviewScheduledNotifications(
                    cand.getEmail(),
                    cand.getName(),
                    "interviewer@xplore.com", // This should be fetched from interviewer service
                    selected.getInterviewerName(),
                    selected.getStartTime(),
                    selected.getDurationMinutes(),
                    req.getRound()
            );
            logger.info("Email notifications sent for recruitment id: {}", saved.getId());
        } catch (Exception e) {
            logger.error("Failed to send email notifications: {}", e.getMessage());
            // Email failure shouldn't break the scheduling
        }
        
        return saved;
    }

    private int getInterviewerLoad(Long interviewerId) {
        return recruitmentRepo.findByInterviewerId(interviewerId).size();
    }

    public int getLoadForInterviewer(Long interviewerId) {
        return getInterviewerLoad(interviewerId);
    }

    public Recruitment updateStatus(Long recruitmentId, InterviewStatus status) {
        Recruitment rec = recruitmentRepo.findById(recruitmentId)
                .orElseThrow(() -> new RuntimeException("Recruitment not found"));
        rec.setStatus(status);
        return recruitmentRepo.save(rec);
    }

    public List<Recruitment> listAllRecruitments() {
        return recruitmentRepo.findAll();
    }

    public List<Recruitment> getInterviewsByCandidateId(Long candidateId) {
        return recruitmentRepo.findByCandidateId(candidateId);
    }

    public List<Recruitment> getInterviewsByInterviewerId(Long interviewerId) {
        return recruitmentRepo.findByInterviewerId(interviewerId);
    }

}