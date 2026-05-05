package com.soulsplanner.repository;

import com.soulsplanner.entity.Game;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GameRepository extends JpaRepository<Game, Long> {
    Optional<Game> findBySlug(String slug);
}
