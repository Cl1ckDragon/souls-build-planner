package com.soulsplanner.repository;

import com.soulsplanner.entity.Weapon;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WeaponRepository extends JpaRepository<Weapon, Long> {
    List<Weapon> findByGameId(Long gameId);
    List<Weapon> findByGameIdAndWeaponType(Long gameId, String weaponType);
}
