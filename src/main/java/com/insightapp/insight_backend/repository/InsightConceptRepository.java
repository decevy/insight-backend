package com.insightapp.insight_backend.repository;

import com.insightapp.insight_backend.entity.InsightConcept;
import com.insightapp.insight_backend.entity.id.InsightConceptId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface InsightConceptRepository extends JpaRepository<InsightConcept, InsightConceptId> {

    List<InsightConcept> findByInsightIdOrderByWeightDesc(UUID insightId);

    @Query("SELECT ic FROM InsightConcept ic " +
            "WHERE ic.insight.id = :insightId " +
            "ORDER BY ic.weight DESC")
    List<InsightConcept> findConceptsByInsightId(@Param("insightId") UUID insightId);
}