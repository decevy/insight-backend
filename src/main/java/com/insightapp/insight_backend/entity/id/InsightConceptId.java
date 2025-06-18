package com.insightapp.insight_backend.entity.id;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

public class InsightConceptId implements Serializable {
    private UUID insight;
    private UUID concept;

    public InsightConceptId() {
    }

    public InsightConceptId(UUID insight, UUID concept) {
        this.insight = insight;
        this.concept = concept;
    }

    // Getters and Setters
    public UUID getInsight() {
        return insight;
    }

    public void setInsight(UUID insight) {
        this.insight = insight;
    }

    public UUID getConcept() {
        return concept;
    }

    public void setConcept(UUID concept) {
        this.concept = concept;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        InsightConceptId that = (InsightConceptId) o;
        return Objects.equals(insight, that.insight) && Objects.equals(concept, that.concept);
    }

    @Override
    public int hashCode() {
        return Objects.hash(insight, concept);
    }
}