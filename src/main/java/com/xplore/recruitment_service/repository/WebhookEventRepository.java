package com.xplore.recruitment_service.repository;

import com.xplore.recruitment_service.entity.WebhookEvent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WebhookEventRepository extends JpaRepository<WebhookEvent, Long> {
    List<WebhookEvent> findByEventType(String eventType);
}
