package com.insightapp.insight_backend.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.util.List;

public class CreateInsightRequest {

    @NotBlank(message = "Content cannot be empty")
    @Size(max = 2000, message = "Content cannot exceed 2000 characters")
    private String content;

    private Boolean isPublic = true;

    private List<ConceptAssociationRequest> concepts;

    // Constructors
    public CreateInsightRequest() {
    }

    public CreateInsightRequest(String content) {
        this.content = content;
    }

    // Getters and Setters
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Boolean getIsPublic() {
        return isPublic;
    }

    public void setIsPublic(Boolean isPublic) {
        this.isPublic = isPublic;
    }

    public List<ConceptAssociationRequest> getConcepts() {
        return concepts;
    }

    public void setConcepts(List<ConceptAssociationRequest> concepts) {
        this.concepts = concepts;
    }

    public static class ConceptAssociationRequest {
        private String conceptName;
        private Double weight = 0.5;
        private String source = "user";

        // Constructors
        public ConceptAssociationRequest() {
        }

        public ConceptAssociationRequest(String conceptName, Double weight, String source) {
            this.conceptName = conceptName;
            this.weight = weight;
            this.source = source;
        }

        // Getters and Setters
        public String getConceptName() {
            return conceptName;
        }

        public void setConceptName(String conceptName) {
            this.conceptName = conceptName;
        }

        public Double getWeight() {
            return weight;
        }

        public void setWeight(Double weight) {
            this.weight = weight;
        }

        public String getSource() {
            return source;
        }

        public void setSource(String source) {
            this.source = source;
        }
    }
}