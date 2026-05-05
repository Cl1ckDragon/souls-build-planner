package com.soulsplanner.service;

import com.soulsplanner.entity.Build;
import com.soulsplanner.repository.BuildRepository;
import com.soulsplanner.service.BuildStatValidationService.ValidationResult;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BuildService {

    private final BuildRepository buildRepository;
    private final BuildStatValidationService validationService;

    public Build createBuild(Build build) {
        ValidationResult result = validationService.validate(build);
        if (!result.valid()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                String.join("; ", result.errors()));
        }
        return buildRepository.save(build);
    }

    public Page<Build> getPublicBuilds(Pageable pageable) {
        return buildRepository.findByIsPublicTrue(pageable);
    }

    public Optional<Build> getBySlug(String slug) {
        return buildRepository.findBySlug(slug);
    }
}
