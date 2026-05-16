package com.xplore.recruitment_service.controller;

import com.xplore.recruitment_service.entity.WebhookEvent;
import com.xplore.recruitment_service.service.WebhookEventService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/webhook-events")
public class WebhookEventController {
    private final WebhookEventService webhookEventService;

    public WebhookEventController(WebhookEventService webhookEventService) {
        this.webhookEventService = webhookEventService;
    }

    @GetMapping
    public List<WebhookEvent> list(@RequestParam(required = false) String eventType) {
        return webhookEventService.list(eventType);
    }
}
