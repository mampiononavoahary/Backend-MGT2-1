package com.mgt2.backendproject.repository;

import com.mgt2.backendproject.model.entity.DocumentVersion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DocumentVersionRepository extends JpaRepository<DocumentVersion, Integer> {
}
