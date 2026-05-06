package com.soulsplanner.dto.game;

import com.soulsplanner.entity.GameClass;

public record GameClassResponse(
    Long id,
    String name,
    int baseVigor,
    int baseMind,
    int baseEndurance,
    int baseStrength,
    int baseDexterity,
    int baseIntelligence,
    int baseFaith,
    int baseArcane,
    int baseLevel
) {
    public static GameClassResponse from(GameClass c) {
        return new GameClassResponse(
            c.getId(), c.getName(),
            c.getBaseVigor(), c.getBaseMind(), c.getBaseEndurance(),
            c.getBaseStrength(), c.getBaseDexterity(), c.getBaseIntelligence(),
            c.getBaseFaith(), c.getBaseArcane(), c.getBaseLevel()
        );
    }
}
