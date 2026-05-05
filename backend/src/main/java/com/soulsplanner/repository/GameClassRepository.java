package com.soulsplanner.repository;

import com.soulsplanner.entity.GameClass;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GameClassRepository extends JpaRepository<GameClass, Long> {
    List<GameClass> findByGameId(Long gameId);
}
