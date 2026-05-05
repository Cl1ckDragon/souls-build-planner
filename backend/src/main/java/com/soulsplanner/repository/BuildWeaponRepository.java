package com.soulsplanner.repository;

import com.soulsplanner.entity.BuildWeapon;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface BuildWeaponRepository extends JpaRepository<BuildWeapon, Long> {
    List<BuildWeapon> findByBuildId(UUID buildId);
    void deleteByBuildId(UUID buildId);
}
