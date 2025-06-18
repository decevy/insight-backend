package com.insightapp.insight_backend.dto;

import java.math.BigDecimal;
import java.util.UUID;

public class ConceptAssociationDTO {
    private UUID conceptId;
    private String canonicalName;
    private String[] synonyms;
    private BigDecimal weight;
    private String source;

    // Constructors
    public ConceptAssociationDTO() {
    }

    public ConceptAssociationDTO(UUID conceptId, String canonicalName, String[] synonyms,
            BigDecimal weight, String source) {
        this.conceptId = conceptId;
        this.canonicalName = canonicalName;
        this.synonyms = synonyms;
        this.weight = weight;
        this.source = source;
    }

    // Getters and Setters
    public UUID getConceptId() {
        return conceptId;
    }

    public void setConceptId(UUID conceptId) {
        this.conceptId = conceptId;
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
}