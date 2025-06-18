package com.insightapp.insight_backend.controller;

import com.insightapp.insight_backend.dto.ConceptDTO;
import com.insightapp.insight_backend.service.ConceptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/concepts")
@CrossOrigin(origins = "*")
public class ConceptController {

    @Autowired
    private ConceptService conceptService;

    @GetMapping
    public ResponseEntity<List<ConceptDTO>> getAllConcepts() {
        List<ConceptDTO> concepts = conceptService.getAllConcepts();
        return ResponseEntity.ok(concepts);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ConceptDTO> getConceptById(@PathVariable UUID id) {
        return conceptService.getConceptById(id)
                .map(concept -> ResponseEntity.ok(concept))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/search")
    public ResponseEntity<List<ConceptDTO>> searchConcepts(@RequestParam String q) {
        List<ConceptDTO> concepts = conceptService.searchConcepts(q);
        return ResponseEntity.ok(concepts);
    }

    @GetMapping("/suggest")
    public ResponseEntity<List<String>> suggestConcepts(@RequestParam String q) {
        List<String> suggestions = conceptService.suggestConcepts(q);
        return ResponseEntity.ok(suggestions);
    }
}