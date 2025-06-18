package com.insightapp.insight_backend.service;

import com.insightapp.insight_backend.dto.ConceptDTO;
import com.insightapp.insight_backend.entity.Concept;
import com.insightapp.insight_backend.repository.ConceptRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ConceptService {

    @Autowired
    private ConceptRepository conceptRepository;

    public List<ConceptDTO> getAllConcepts() {
        return conceptRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public Optional<ConceptDTO> getConceptById(UUID id) {
        return conceptRepository.findById(id)
                .map(this::convertToDTO);
    }

    public List<ConceptDTO> searchConcepts(String searchTerm) {
        return conceptRepository.searchByTerm(searchTerm)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<String> suggestConcepts(String partialTerm) {
        List<Concept> concepts = conceptRepository.searchByTerm(partialTerm);
        return concepts.stream()
                .map(Concept::getCanonicalName)
                .distinct()
                .limit(10)
                .collect(Collectors.toList());
    }

    private ConceptDTO convertToDTO(Concept concept) {
        ConceptDTO dto = new ConceptDTO();
        dto.setId(concept.getId());
        dto.setCanonicalName(concept.getCanonicalName());
        dto.setSynonyms(concept.getSynonyms());
        dto.setRelatedTerms(concept.getRelatedTerms());
        dto.setAntonyms(concept.getAntonyms());
        dto.setLanguages(concept.getLanguages());
        dto.setDescription(concept.getDescription());
        dto.setCreatedAt(concept.getCreatedAt());
        dto.setUpdatedAt(concept.getUpdatedAt());
        return dto;
    }
}