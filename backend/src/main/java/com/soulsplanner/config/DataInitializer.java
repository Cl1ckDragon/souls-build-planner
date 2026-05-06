package com.soulsplanner.config;

import com.soulsplanner.entity.Game;
import com.soulsplanner.entity.GameClass;
import com.soulsplanner.repository.GameClassRepository;
import com.soulsplanner.repository.GameRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class DataInitializer implements ApplicationRunner {

    private final GameRepository gameRepository;
    private final GameClassRepository gameClassRepository;

    @Override
    @Transactional
    public void run(ApplicationArguments args) {
        if (gameRepository.count() > 0) return;

        Game er  = game("Elden Ring",    "elden-ring");
        Game ds3 = game("Dark Souls III", "dark-souls-3");
        Game ds2 = game("Dark Souls II",  "dark-souls-2");
        Game ds1 = game("Dark Souls",     "dark-souls");

        // Elden Ring — all 10 starting classes
        // columns: name, baseLevel, vig, mnd, end, str, dex, int, fai, arc
        cls(er, "Vagabond",    9,  15, 10, 11, 14, 13,  9,  9,  7);
        cls(er, "Warrior",     8,  11, 12, 11, 10, 16, 10,  8,  9);
        cls(er, "Hero",        7,  14,  9, 12, 16,  9,  7,  8, 11);
        cls(er, "Bandit",      5,  10, 11, 10,  9, 13,  9,  8, 14);
        cls(er, "Astrologer",  6,   9, 15,  9,  8, 12, 16,  7,  9);
        cls(er, "Prophet",     7,  10, 14,  8, 11, 10,  7, 16, 10);
        cls(er, "Samurai",     9,  12, 11, 13, 12, 15,  9,  8,  8);
        cls(er, "Prisoner",    9,  11, 12, 11, 11, 14, 14,  6,  9);
        cls(er, "Confessor",  10,  10, 13, 10, 12, 12,  9, 14,  9);
        cls(er, "Wretch",      1,  10, 10, 10, 10, 10, 10, 10, 10);

        // Dark Souls III starting classes
        // Mapping: vig/att→mnd/end/str/dex/int/fai/lck→arc  (Vitality omitted — no schema column)
        cls(ds3, "Knight",      9, 12, 10, 11, 13, 12,  9,  9,  7);
        cls(ds3, "Mercenary",   8, 11, 12, 11, 10, 16, 10,  8,  9);
        cls(ds3, "Herald",      8, 12,  8, 12,  9,  9,  7, 13, 11);
        cls(ds3, "Assassin",   10, 10, 14, 11, 10, 14, 11,  9, 10);
        cls(ds3, "Sorcerer",    6,  8, 15,  8,  9, 11, 16,  7, 12);
        cls(ds3, "Pyromancer",  8, 11, 12,  9, 12,  9, 14, 14,  7);
        cls(ds3, "Cleric",      7, 10, 14,  9,  9,  8,  7, 16, 13);
        cls(ds3, "Deprived",   10, 10, 10, 10, 10, 10, 10, 10, 10);

        // Dark Souls I & II — game stubs only; classes to be added in a future pass
        // (Different stat systems require schema discussion)
        gameRepository.save(ds2);
        gameRepository.save(ds1);
    }

    private Game game(String name, String slug) {
        Game g = new Game();
        g.setName(name);
        g.setSlug(slug);
        return gameRepository.save(g);
    }

    private void cls(Game game, String name, int lvl,
                     int vig, int mnd, int end, int str,
                     int dex, int intel, int fai, int arc) {
        GameClass c = new GameClass();
        c.setGame(game);
        c.setName(name);
        c.setBaseLevel(lvl);
        c.setBaseVigor(vig);
        c.setBaseMind(mnd);
        c.setBaseEndurance(end);
        c.setBaseStrength(str);
        c.setBaseDexterity(dex);
        c.setBaseIntelligence(intel);
        c.setBaseFaith(fai);
        c.setBaseArcane(arc);
        gameClassRepository.save(c);
    }
}
