package com.insightapp.insight_backend.repository;

import com.insightapp.insight_backend.entity.Insight;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.UUID;

@Repository
public interface InsightRepository extends JpaRepository<Insight, UUID> {

        Page<Insight> findByIsPublicTrueOrderByCreatedAtDesc(Pageable pageable);

        Page<Insight> findByUserIdOrderByCreatedAtDesc(UUID userId, Pageable pageable);

        @Query("SELECT i FROM Insight i WHERE " +
                        "i.is_public = true AND " +
                        "LOWER(i.content) LIKE LOWER(CONCAT('%', :searchTerm, '%'))")
        Page<Insight> searchByContent(@Param("searchTerm") String searchTerm, Pageable pageable);

        @Query(value = "SELECT DISTINCT i.* FROM insights i " +
                        "JOIN insight_concepts ic ON i.id = ic.insight_id " +
                        "JOIN concepts c ON ic.concept_id = c.id " +
                        "WHERE i.is_public = true AND " +
                        "(c.canonical_name = :conceptName OR " +
                        "c.synonyms @> ARRAY[:conceptName] OR " +
                        "c.related_terms @> ARRAY[:conceptName])", nativeQuery = true)
        Page<Insight> findByConceptName(@Param("conceptName") String conceptName, Pageable pageable);
}