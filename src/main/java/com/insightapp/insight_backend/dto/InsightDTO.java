package com.insightapp.insight_backend.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class InsightDTO {
    private UUID id;
    private String content;
    private String username;
    private Boolean isPublic;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<ConceptAssociationDTO> concepts;

    // Constructors
    public InsightDTO() {
    }

    public InsightDTO(UUID id, String content, String username, Boolean isPublic,
            LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.content = content;
        this.username = username;
        this.isPublic = isPublic;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    // Getters and Setters
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Boolean getIsPublic() {
        return isPublic;
    }

    public void setIsPublic(Boolean isPublic) {
        this.isPublic = isPublic;
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

    public List<ConceptAssociationDTO> getConcepts() {
        return concepts;
    }

    public void setConcepts(List<ConceptAssociationDTO> concepts) {
        this.concepts = concepts;
    }
}