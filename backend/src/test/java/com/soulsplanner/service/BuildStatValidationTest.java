package com.soulsplanner.service;

import com.soulsplanner.entity.Build;
import com.soulsplanner.entity.GameClass;
import com.soulsplanner.service.BuildStatValidationService.ValidationResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class BuildStatValidationTest {

    @InjectMocks
    private BuildStatValidationService validationService;

    // Vagabond base stats (Elden Ring)
    private GameClass vagabond;

    @BeforeEach
    void setUp() {
        vagabond = new GameClass();
        vagabond.setName("Vagabond");
        vagabond.setBaseVigor(15);
        vagabond.setBaseMind(10);
        vagabond.setBaseEndurance(11);
        vagabond.setBaseStrength(14);
        vagabond.setBaseDexterity(13);
        vagabond.setBaseIntelligence(9);
        vagabond.setBaseFaith(9);
        vagabond.setBaseArcane(7);
        vagabond.setBaseLevel(9);
    }

    private Build buildWith(int vig, int mnd, int end, int str,
                            int dex, int intel, int fth, int arc, int lvl) {
        Build b = new Build();
        b.setGameClass(vagabond);
        b.setLevel(lvl);
        b.setVigor(vig);
        b.setMind(mnd);
        b.setEndurance(end);
        b.setStrength(str);
        b.setDexterity(dex);
        b.setIntelligence(intel);
        b.setFaith(fth);
        b.setArcane(arc);
        return b;
    }

    @Test
    void validate_allStatsAtBase_isValid() {
        Build build = buildWith(15, 10, 11, 14, 13, 9, 9, 7, 9);
        ValidationResult result = validationService.validate(build);
        assertTrue(result.valid());
        assertThat(result.errors()).isEmpty();
    }

    @Test
    void validate_allStatsAboveBase_isValid() {
        Build build = buildWith(40, 20, 25, 30, 25, 15, 20, 12, 150);
        ValidationResult result = validationService.validate(build);
        assertTrue(result.valid());
    }

    @Test
    void validate_vigorBelowBase_isInvalid() {
        Build build = buildWith(5, 10, 11, 14, 13, 9, 9, 7, 9);
        ValidationResult result = validationService.validate(build);
        assertFalse(result.valid());
        assertThat(result.errors()).hasSize(1);
        assertThat(result.errors().get(0)).contains("Vigor");
    }

    @Test
    void validate_multipleStatsBelowBase_reportsAllErrors() {
        Build build = buildWith(5, 3, 11, 14, 13, 9, 9, 7, 9);  // Vigor + Mind below base
        ValidationResult result = validationService.validate(build);
        assertFalse(result.valid());
        assertThat(result.errors()).hasSize(2);
        assertThat(result.errors()).anyMatch(e -> e.contains("Vigor"));
        assertThat(result.errors()).anyMatch(e -> e.contains("Mind"));
    }

    @Test
    void validate_levelBelowBaseLevel_isInvalid() {
        Build build = buildWith(15, 10, 11, 14, 13, 9, 9, 7, 1);  // level 1 < base 9
        ValidationResult result = validationService.validate(build);
        assertFalse(result.valid());
        assertThat(result.errors()).anyMatch(e -> e.contains("Level"));
    }

    @Test
    void validate_noClassSelected_isInvalid() {
        Build build = new Build();  // gameClass is null
        ValidationResult result = validationService.validate(build);
        assertFalse(result.valid());
        assertThat(result.errors()).containsExactly("Build must have a class selected");
    }

    @Test
    void validate_allStatsBelowBase_reportsNineErrors() {
        Build build = buildWith(1, 1, 1, 1, 1, 1, 1, 1, 1);  // everything below base + level below
        ValidationResult result = validationService.validate(build);
        assertFalse(result.valid());
        assertThat(result.errors()).hasSize(9);  // 8 stats + level
    }
}
