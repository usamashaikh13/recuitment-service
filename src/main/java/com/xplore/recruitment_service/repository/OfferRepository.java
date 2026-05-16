package com.xplore.recruitment_service.repository;

import com.xplore.recruitment_service.entity.Offer;
import com.xplore.recruitment_service.entity.OfferStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface OfferRepository extends JpaRepository<Offer, Long> {
    List<Offer> findByCandidateId(Long candidateId);
    List<Offer> findByApplicationId(Long applicationId);
    List<Offer> findByStatus(OfferStatus status);
    List<Offer> findByStatusInAndExpiresAtBefore(List<OfferStatus> statuses, LocalDateTime now);
}
