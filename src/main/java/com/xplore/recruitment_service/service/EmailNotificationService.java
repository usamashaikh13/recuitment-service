package com.xplore.recruitment_service.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class EmailNotificationService {
    
    private static final Logger logger = LoggerFactory.getLogger(EmailNotificationService.class);
    
    private final RestTemplate restTemplate;
    
    @Value("${interviewer.service.url}")
    private String interviewerServiceUrl;
    
    public void sendInterviewScheduledNotifications(
            String candidateEmail,
            String candidateName,
            String interviewerEmail,
            String interviewerName,
            LocalDateTime interviewTime,
            int durationMinutes,
            String round) {
        
        sendCandidateEmail(candidateEmail, candidateName, interviewerName, interviewTime, durationMinutes, round);
        sendInterviewerEmail(interviewerEmail, interviewerName, candidateName, candidateEmail, interviewTime, durationMinutes, round);
    }
    
    private void sendCandidateEmail(
            String candidateEmail,
            String candidateName,
            String interviewerName,
            LocalDateTime interviewTime,
            int durationMinutes,
            String round) {
        
        try {
            String emailUrl = interviewerServiceUrl.replace("/api/slots", "/api/email/candidate");
            
            Map<String, Object> emailData = new HashMap<>();
            emailData.put("candidateEmail", candidateEmail);
            emailData.put("candidateName", candidateName);
            emailData.put("interviewerName", interviewerName);
            emailData.put("interviewTime", interviewTime.toString());
            emailData.put("durationMinutes", durationMinutes);
            emailData.put("round", round);
            
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<Map<String, Object>> request = new HttpEntity<>(emailData, headers);
            
            restTemplate.postForObject(emailUrl, request, String.class);
            logger.info("Candidate email notification sent successfully to: {}", candidateEmail);
            
        } catch (Exception e) {
            logger.error("Failed to send candidate email notification: {}", e.getMessage());
        }
    }
    
    private void sendInterviewerEmail(
            String interviewerEmail,
            String interviewerName,
            String candidateName,
            String candidateEmail,
            LocalDateTime interviewTime,
            int durationMinutes,
            String round) {
        
        try {
            String emailUrl = interviewerServiceUrl.replace("/api/slots", "/api/email/interviewer");
            
            Map<String, Object> emailData = new HashMap<>();
            emailData.put("interviewerEmail", interviewerEmail);
            emailData.put("interviewerName", interviewerName);
            emailData.put("candidateName", candidateName);
            emailData.put("candidateEmail", candidateEmail);
            emailData.put("interviewTime", interviewTime.toString());
            emailData.put("durationMinutes", durationMinutes);
            emailData.put("round", round);
            
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<Map<String, Object>> request = new HttpEntity<>(emailData, headers);
            
            restTemplate.postForObject(emailUrl, request, String.class);
            logger.info("Interviewer email notification sent successfully to: {}", interviewerEmail);
            
        } catch (Exception e) {
            logger.error("Failed to send interviewer email notification: {}", e.getMessage());
        }
    }
}
