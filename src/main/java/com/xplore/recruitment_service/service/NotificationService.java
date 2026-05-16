package com.xplore.recruitment_service.service;

import com.xplore.recruitment_service.entity.ApplicationStage;
import com.xplore.recruitment_service.entity.Candidate;
import com.xplore.recruitment_service.entity.Offer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {
    private static final Logger logger = LoggerFactory.getLogger(NotificationService.class);

    public void notifyStageChanged(Candidate candidate, ApplicationStage stage) {
        logger.info("Stage-change notification queued for candidate {} to stage {}",
                candidate.getEmail(), stage);
    }

    public void notifyOfferExpiring(Offer offer) {
        logger.info("Offer expiry alert queued for offer {}", offer.getId());
    }
}
