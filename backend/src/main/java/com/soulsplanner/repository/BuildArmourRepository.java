package com.soulsplanner.repository;

import com.soulsplanner.entity.BuildArmour;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface BuildArmourRepository extends JpaRepository<BuildArmour, Long> {
    List<BuildArmour> findByBuildId(UUID buildId);
    void deleteByBuildId(UUID buildId);
}
