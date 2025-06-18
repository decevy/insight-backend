package com.insightapp.insight_backend.entity;

import com.insightapp.insight_backend.entity.id.InsightConceptId;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "insight_concepts")
@IdClass(InsightConceptId.class)
public class InsightConcept {

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "insight_id")
    private Insight insight;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "concept_id")
    private Concept concept;

    @Column(precision = 3, scale = 2)
    private BigDecimal weight = BigDecimal.valueOf(0.5);

    @Column(length = 20)
    private String source = "user";

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    // Constructors
    public InsightConcept() {
    }

    public InsightConcept(Insight insight, Concept concept, BigDecimal weight, String source) {
        this.insight = insight;
        this.concept = concept;
        this.weight = weight;
        this.source = source;
    }

    // Getters and Setters
    public Insight getInsight() {
        return insight;
    }

    public void setInsight(Insight insight) {
        this.insight = insight;
    }

    public Concept getConcept() {
        return concept;
    }

    public void setConcept(Concept concept) {
        this.concept = concept;
    }

    public BigDecimal getWeight() {
        return weight;
    }

    public void setWeight(BigDecimal weight) {
        this.weight = weight;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}