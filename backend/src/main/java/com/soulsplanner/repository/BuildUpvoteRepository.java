package com.soulsplanner.repository;

import com.soulsplanner.entity.BuildUpvote;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface BuildUpvoteRepository extends JpaRepository<BuildUpvote, Long> {
    boolean existsByUserIdAndBuildId(UUID userId, UUID buildId);
    Optional<BuildUpvote> findByUserIdAndBuildId(UUID userId, UUID buildId);
    int countByBuildId(UUID buildId);
}
