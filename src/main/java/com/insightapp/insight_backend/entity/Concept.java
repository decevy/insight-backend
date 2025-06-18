package com.insightapp.insight_backend.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "concepts")
public class Concept {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotBlank
    @Size(max = 100)
    @Column(name = "canonical_name", nullable = false)
    private String canonicalName;

    @JdbcTypeCode(SqlTypes.ARRAY)
    @Column(name = "synonyms", columnDefinition = "text[]")
    private String[] synonyms;

    @JdbcTypeCode(SqlTypes.ARRAY)
    @Column(name = "related_terms", columnDefinition = "text[]")
    private String[] relatedTerms;

    @JdbcTypeCode(SqlTypes.ARRAY)
    @Column(name = "antonyms", columnDefinition = "text[]")
    private String[] antonyms;

    @JdbcTypeCode(SqlTypes.ARRAY)
    @Column(name = "languages", columnDefinition = "varchar(10)[]")
    private String[] languages;

    @Column(columnDefinition = "TEXT")
    private String description;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "concept", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<InsightConcept> insightConcepts;

    // Constructors
    public Concept() {
    }

    public Concept(String canonicalName) {
        this.canonicalName = canonicalName;
    }

    // Getters and Setters
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getCanonicalName() {
        return canonicalName;
    }

    public void setCanonicalName(String canonicalName) {
        this.canonicalName = canonicalName;
    }

    public String[] getSynonyms() {
        return synonyms;
    }

    public void setSynonyms(String[] synonyms) {
        this.synonyms = synonyms;
    }

    public String[] getRelatedTerms() {
        return relatedTerms;
    }

    public void setRelatedTerms(String[] relatedTerms) {
        this.relatedTerms = relatedTerms;
    }

    public String[] getAntonyms() {
        return antonyms;
    }

    public void setAntonyms(String[] antonyms) {
        this.antonyms = antonyms;
    }

    public String[] getLanguages() {
        return languages;
    }

    public void setLanguages(String[] languages) {
        this.languages = languages;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public List<InsightConcept> getInsightConcepts() {
        return insightConcepts;
    }

    public void setInsightConcepts(List<InsightConcept> insightConcepts) {
        this.insightConcepts = insightConcepts;
    }
}