package com.soulsplanner.service;

import com.soulsplanner.dto.build.BuildRequest;
import com.soulsplanner.dto.build.BuildResponse;
import com.soulsplanner.entity.Build;
import com.soulsplanner.entity.Game;
import com.soulsplanner.entity.GameClass;
import com.soulsplanner.entity.User;
import com.soulsplanner.repository.BuildRepository;
import com.soulsplanner.repository.GameClassRepository;
import com.soulsplanner.repository.GameRepository;
import com.soulsplanner.repository.UserRepository;
import com.soulsplanner.service.BuildStatValidationService.ValidationResult;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BuildService {

    private final BuildRepository buildRepository;
    private final UserRepository userRepository;
    private final GameRepository gameRepository;
    private final GameClassRepository gameClassRepository;
    private final BuildStatValidationService validationService;
    private final SlugService slugService;

    @Transactional
    public BuildResponse createBuild(BuildRequest request, String username) {
        User user = userRepository.findByUsername(username)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED));

        Game game = gameRepository.findById(request.gameId())
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Game not found"));

        GameClass cls = gameClassRepository.findById(request.classId())
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Class not found"));

        if (!cls.getGame().getId().equals(game.getId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Class does not belong to selected game");
        }

        Build build = new Build();
        build.setUser(user);
        build.setGame(game);
        build.setGameClass(cls);
        build.setTitle(request.title());
        build.setSlug(slugService.generateUniqueSlug(request.title()));
        build.setDescription(request.description());
        build.setVigor(request.vigor());
        build.setMind(request.mind());
        build.setEndurance(request.endurance());
        build.setStrength(request.strength());
        build.setDexterity(request.dexterity());
        build.setIntelligence(request.intelligence());
        build.setFaith(request.faith());
        build.setArcane(request.arcane());
        build.setPublic(request.isPublic());

        int spent = (request.vigor()        - cls.getBaseVigor())
                  + (request.mind()         - cls.getBaseMind())
                  + (request.endurance()    - cls.getBaseEndurance())
                  + (request.strength()     - cls.getBaseStrength())
                  + (request.dexterity()    - cls.getBaseDexterity())
                  + (request.intelligence() - cls.getBaseIntelligence())
                  + (request.faith()        - cls.getBaseFaith())
                  + (request.arcane()       - cls.getBaseArcane());
        build.setLevel(cls.getBaseLevel() + spent);

        ValidationResult validation = validationService.validate(build);
        if (!validation.valid()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                String.join("; ", validation.errors()));
        }

        return BuildResponse.from(buildRepository.save(build));
    }

    public Page<BuildResponse> getPublicBuilds(Pageable pageable) {
        return buildRepository.findByIsPublicTrue(pageable).map(BuildResponse::from);
    }

    public Optional<BuildResponse> getBySlug(String slug) {
        return buildRepository.findBySlug(slug).map(BuildResponse::from);
    }
}
