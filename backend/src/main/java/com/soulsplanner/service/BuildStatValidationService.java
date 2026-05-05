package com.soulsplanner.service;

import com.soulsplanner.entity.Build;
import com.soulsplanner.entity.GameClass;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BuildStatValidationService {

    public record ValidationResult(boolean valid, List<String> errors) {
        public static ValidationResult success() {
            return new ValidationResult(true, List.of());
        }
        public static ValidationResult failure(List<String> errors) {
            return new ValidationResult(false, List.copyOf(errors));
        }
    }

    public ValidationResult validate(Build build) {
        GameClass cls = build.getGameClass();
        if (cls == null) {
            return ValidationResult.failure(List.of("Build must have a class selected"));
        }

        List<String> errors = new ArrayList<>();
        checkStat("Vigor",        build.getVigor(),        cls.getBaseVigor(),        errors);
        checkStat("Mind",         build.getMind(),         cls.getBaseMind(),         errors);
        checkStat("Endurance",    build.getEndurance(),    cls.getBaseEndurance(),    errors);
        checkStat("Strength",     build.getStrength(),     cls.getBaseStrength(),     errors);
        checkStat("Dexterity",    build.getDexterity(),    cls.getBaseDexterity(),    errors);
        checkStat("Intelligence", build.getIntelligence(), cls.getBaseIntelligence(), errors);
        checkStat("Faith",        build.getFaith(),        cls.getBaseFaith(),        errors);
        checkStat("Arcane",       build.getArcane(),       cls.getBaseArcane(),       errors);

        if (build.getLevel() < cls.getBaseLevel()) {
            errors.add("Level (" + build.getLevel() + ") cannot be less than class base level of " + cls.getBaseLevel());
        }

        return errors.isEmpty() ? ValidationResult.success() : ValidationResult.failure(errors);
    }

    private void checkStat(String name, int value, int base, List<String> errors) {
        if (value < base) {
            errors.add(name + " (" + value + ") cannot be less than class base of " + base);
        }
    }
}
