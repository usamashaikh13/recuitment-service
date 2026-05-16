package com.xplore.recruitment_service.controller;

import com.xplore.recruitment_service.entity.Offer;
import com.xplore.recruitment_service.entity.OfferStatus;
import com.xplore.recruitment_service.service.OfferService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/offers")
public class OfferController {
    private final OfferService offerService;

    public OfferController(OfferService offerService) {
        this.offerService = offerService;
    }

    @PostMapping
    public Offer create(@RequestBody Offer offer) {
        return offerService.create(offer);
    }

    @PutMapping("/{id}")
    public Offer update(@PathVariable Long id, @RequestBody Offer offer) {
        return offerService.update(id, offer);
    }

    @PatchMapping("/{id}/status")
    public Offer updateStatus(@PathVariable Long id, @RequestParam OfferStatus status) {
        return offerService.updateStatus(id, status);
    }

    @GetMapping("/{id}")
    public Offer getById(@PathVariable Long id) {
        return offerService.getById(id);
    }

    @GetMapping
    public List<Offer> list(
            @RequestParam(required = false) Long candidateId,
            @RequestParam(required = false) Long applicationId,
            @RequestParam(required = false) OfferStatus status) {
        return offerService.list(candidateId, applicationId, status);
    }
}
