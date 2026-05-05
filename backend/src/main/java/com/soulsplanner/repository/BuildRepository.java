package com.soulsplanner.repository;

import com.soulsplanner.entity.Build;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BuildRepository extends JpaRepository<Build, UUID> {
    Optional<Build> findBySlug(String slug);
    boolean existsBySlug(String slug);
    Page<Build> findByIsPublicTrue(Pageable pageable);
    List<Build> findByUserIdAndIsPublicTrue(UUID userId);
    List<Build> findByUserId(UUID userId);
}
