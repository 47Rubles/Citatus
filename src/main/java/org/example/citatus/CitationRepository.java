package org.example.citatus;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CitationRepository extends JpaRepository<Citation, Long> {
}
