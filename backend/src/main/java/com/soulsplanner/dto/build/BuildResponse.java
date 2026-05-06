package com.soulsplanner.dto.build;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.soulsplanner.entity.Build;

import java.util.UUID;

public record BuildResponse(
    UUID id,
    String title,
    String slug,
    String description,
    int level,
    int vigor,
    int mind,
    int endurance,
    int strength,
    int dexterity,
    int intelligence,
    int faith,
    int arcane,
    @JsonProperty("isPublic") boolean isPublic,
    int upvoteCount,
    GameSummary game,
    ClassSummary gameClass,
    UserSummary user,
    String createdAt
) {
    public record GameSummary(Long id, String name, String slug) {}
    public record ClassSummary(Long id, String name) {}
    public record UserSummary(String username) {}

    public static BuildResponse from(Build b) {
        return new BuildResponse(
            b.getId(),
            b.getTitle(),
            b.getSlug(),
            b.getDescription(),
            b.getLevel(),
            b.getVigor(),
            b.getMind(),
            b.getEndurance(),
            b.getStrength(),
            b.getDexterity(),
            b.getIntelligence(),
            b.getFaith(),
            b.getArcane(),
            b.isPublic(),
            b.getUpvoteCount(),
            new GameSummary(b.getGame().getId(), b.getGame().getName(), b.getGame().getSlug()),
            new ClassSummary(b.getGameClass().getId(), b.getGameClass().getName()),
            new UserSummary(b.getUser().getUsername()),
            b.getCreatedAt().toString()
        );
    }
}
