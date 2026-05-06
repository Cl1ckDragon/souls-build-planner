package com.soulsplanner.controller;

import com.soulsplanner.dto.game.GameClassResponse;
import com.soulsplanner.entity.Game;
import com.soulsplanner.repository.GameClassRepository;
import com.soulsplanner.repository.GameRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/games")
@Tag(name = "Games", description = "Games and starting classes")
@RequiredArgsConstructor
public class GameController {

    private final GameRepository gameRepository;
    private final GameClassRepository gameClassRepository;

    @GetMapping
    @Operation(summary = "List all supported games")
    public ResponseEntity<List<Game>> getGames() {
        return ResponseEntity.ok(gameRepository.findAll());
    }

    @GetMapping("/{gameId}/classes")
    @Operation(summary = "List starting classes for a game")
    public ResponseEntity<List<GameClassResponse>> getClasses(@PathVariable Long gameId) {
        List<GameClassResponse> classes = gameClassRepository
            .findByGameId(gameId)
            .stream()
            .map(GameClassResponse::from)
            .toList();
        return ResponseEntity.ok(classes);
    }
}
