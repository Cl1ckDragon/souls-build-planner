package com.soulsplanner.dto.build;

import jakarta.validation.constraints.*;

public record BuildRequest(
    @NotNull Long gameId,
    @NotNull Long classId,
    @NotBlank @Size(max = 120) String title,
    @Size(max = 2000) String description,
    @Min(1) @Max(99) int vigor,
    @Min(1) @Max(99) int mind,
    @Min(1) @Max(99) int endurance,
    @Min(1) @Max(99) int strength,
    @Min(1) @Max(99) int dexterity,
    @Min(1) @Max(99) int intelligence,
    @Min(1) @Max(99) int faith,
    @Min(1) @Max(99) int arcane,
    boolean isPublic
) {}
