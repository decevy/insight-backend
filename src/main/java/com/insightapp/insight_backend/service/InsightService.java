package com.insightapp.insight_backend.service;

import com.insightapp.insight_backend.dto.*;
import com.insightapp.insight_backend.dto.request.CreateInsightRequest;
import com.insightapp.insight_backend.entity.*;
import com.insightapp.insight_backend.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
public class InsightService {

    @Autowired
    private InsightRepository insightRepository;

    @Autowired
    private ConceptRepository conceptRepository;

    @Autowired
    private InsightConceptRepository insightConceptRepository;

    @Autowired
    private UserRepository userRepository;

    public InsightDTO createInsight(CreateInsightRequest request, String username) {
        // Find user
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Create insight
        Insight insight = new Insight(request.getContent(), user);
        insight.setIsPublic(request.getIsPublic());
        insight = insightRepository.save(insight);

        // Process concept associations
        if (!CollectionUtils.isEmpty(request.getConcepts())) {
            for (CreateInsightRequest.ConceptAssociationRequest conceptReq : request.getConcepts()) {
                Concept concept = findOrCreateConcept(conceptReq.getConceptName());

                InsightConcept insightConcept = new InsightConcept(
                        insight,
                        concept,
                        BigDecimal.valueOf(conceptReq.getWeight()),
                        conceptReq.getSource());
                insightConceptRepository.save(insightConcept);
            }
        }

        return loadAndConvertInsightToDTO(insight);
    }

    public Page<InsightDTO> getPublicInsights(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return insightRepository.findByIsPublicTrueOrderByCreatedAtDesc(pageable)
                .map(this::loadAndConvertInsightToDTO);
    }

    public Page<InsightDTO> getUserInsights(String username, int page, int size) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Pageable pageable = PageRequest.of(page, size);
        return insightRepository.findByUserIdOrderByCreatedAtDesc(user.getId(), pageable)
                .map(this::loadAndConvertInsightToDTO);
    }

    public Page<InsightDTO> searchInsights(String searchTerm, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return insightRepository.searchByContent(searchTerm, pageable)
                .map(this::loadAndConvertInsightToDTO);
    }

    public Page<InsightDTO> getInsightsByConcept(String conceptName, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return insightRepository.findByConceptName(conceptName, pageable)
                .map(this::loadAndConvertInsightToDTO);
    }

    public Optional<InsightDTO> getInsightById(UUID id) {
        return insightRepository.findById(id)
                .map(this::loadAndConvertInsightToDTO);
    }

    private Concept findOrCreateConcept(String conceptName) {
        // First try to find by canonical name
        Optional<Concept> existingConcept = conceptRepository.findByCanonicalName(conceptName.toLowerCase());
        if (existingConcept.isPresent()) {
            return existingConcept.get();
        }

        // Try to find by synonyms or related terms
        List<Concept> conceptsBySynonym = conceptRepository.findBySynonymOrRelatedTerm(conceptName.toLowerCase());
        if (!conceptsBySynonym.isEmpty()) {
            return conceptsBySynonym.get(0);
        }

        // Create new concept
        Concept newConcept = new Concept(conceptName.toLowerCase());
        newConcept.setLanguages(new String[] { "en" }); // Default to English
        return conceptRepository.save(newConcept);
    }

    private InsightDTO loadAndConvertInsightToDTO(Insight insight) {
        InsightDTO dto = new InsightDTO(
                insight.getId(),
                insight.getContent(),
                insight.getUser().getUsername(),
                insight.getIsPublic(),
                insight.getCreatedAt(),
                insight.getUpdatedAt());

        // Load concept associations
        List<InsightConcept> insightConcepts = insightConceptRepository
                .findConceptsByInsightId(insight.getId());

        List<ConceptAssociationDTO> conceptDTOs = insightConcepts.stream()
                .map(ic -> new ConceptAssociationDTO(
                        ic.getConcept().getId(),
                        ic.getConcept().getCanonicalName(),
                        ic.getConcept().getSynonyms(),
                        ic.getWeight(),
                        ic.getSource()))
                .collect(Collectors.toList());

        dto.setConcepts(conceptDTOs);
        return dto;
    }
}