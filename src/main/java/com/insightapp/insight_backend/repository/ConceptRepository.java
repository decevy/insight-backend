package com.insightapp.insight_backend.repository;

import com.insightapp.insight_backend.entity.Concept;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ConceptRepository extends JpaRepository<Concept, UUID> {

    Optional<Concept> findByCanonicalName(String canonicalName);

    @Query(value = "SELECT c.* FROM concepts c WHERE " +
            "c.canonical_name = :term OR " +
            "c.synonyms @> ARRAY[:term] OR " +
            "c.related_terms @> ARRAY[:term]", nativeQuery = true)
    List<Concept> findBySynonymOrRelatedTerm(@Param("term") String term);

    @Query("SELECT c FROM Concept c WHERE " +
            "c.canonicalName ILIKE %:searchTerm% OR " +
            "array_to_string(c.synonyms, ',') ILIKE %:searchTerm% OR " +
            "array_to_string(c.relatedTerms, ',') ILIKE %:searchTerm%")
    List<Concept> searchByTerm(@Param("searchTerm") String searchTerm);
}