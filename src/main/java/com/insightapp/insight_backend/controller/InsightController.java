package com.insightapp.insight_backend.controller;

import com.insightapp.insight_backend.dto.InsightDTO;
import com.insightapp.insight_backend.dto.request.CreateInsightRequest;
import com.insightapp.insight_backend.service.InsightService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/insights")
@CrossOrigin(origins = "*") // Configure properly for production
public class InsightController {

    @Autowired
    private InsightService insightService;

    @PostMapping
    public ResponseEntity<InsightDTO> createInsight(
            @Valid @RequestBody CreateInsightRequest request,
            @RequestHeader(value = "Username", defaultValue = "philosopher") String username) {

        try {
            InsightDTO insight = insightService.createInsight(request, username);
            return ResponseEntity.status(HttpStatus.CREATED).body(insight);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @GetMapping
    public ResponseEntity<Page<InsightDTO>> getInsights(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {

        Page<InsightDTO> insights = insightService.getPublicInsights(page, size);
        return ResponseEntity.ok(insights);
    }

    @GetMapping("/user/{username}")
    public ResponseEntity<Page<InsightDTO>> getUserInsights(
            @PathVariable String username,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {

        try {
            Page<InsightDTO> insights = insightService.getUserInsights(username, page, size);
            return ResponseEntity.ok(insights);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/search")
    public ResponseEntity<Page<InsightDTO>> searchInsights(
            @RequestParam String q,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {

        Page<InsightDTO> insights = insightService.searchInsights(q, page, size);
        return ResponseEntity.ok(insights);
    }

    @GetMapping("/concept/{conceptName}")
    public ResponseEntity<Page<InsightDTO>> getInsightsByConcept(
            @PathVariable String conceptName,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {

        Page<InsightDTO> insights = insightService.getInsightsByConcept(conceptName, page, size);
        return ResponseEntity.ok(insights);
    }

    @GetMapping("/{id}")
    public ResponseEntity<InsightDTO> getInsightById(@PathVariable UUID id) {
        return insightService.getInsightById(id)
                .map(insight -> ResponseEntity.ok(insight))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }
}