package com.xplore.recruitment_service.service;

import com.xplore.recruitment_service.entity.WebhookEvent;
import com.xplore.recruitment_service.repository.WebhookEventRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WebhookEventService {
    private final WebhookEventRepository webhookEventRepository;

    public WebhookEventService(WebhookEventRepository webhookEventRepository) {
        this.webhookEventRepository = webhookEventRepository;
    }

    public WebhookEvent publish(String eventType, String aggregateType, Long aggregateId, String payload) {
        WebhookEvent event = new WebhookEvent();
        event.setEventType(eventType);
        event.setAggregateType(aggregateType);
        event.setAggregateId(aggregateId);
        event.setPayload(payload);
        return webhookEventRepository.save(event);
    }

    public List<WebhookEvent> list(String eventType) {
        if (eventType != null) {
            return webhookEventRepository.findByEventType(eventType);
        }
        return webhookEventRepository.findAll();
    }
}
