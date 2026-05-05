package com.soulsplanner.repository;

import com.soulsplanner.entity.SavedBuild;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface SavedBuildRepository extends JpaRepository<SavedBuild, Long> {
    boolean existsByUserIdAndBuildId(UUID userId, UUID buildId);
    List<SavedBuild> findByUserId(UUID userId);
    void deleteByUserIdAndBuildId(UUID userId, UUID buildId);
}
