package com.soulsplanner.service;

import com.soulsplanner.repository.BuildRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SlugService {

    private final BuildRepository buildRepository;

    public String generateUniqueSlug(String title) {
        String base = title.toLowerCase()
            .replaceAll("[^a-z0-9\\s-]", "")
            .trim()
            .replaceAll("\\s+", "-");

        if (base.length() > 80) {
            base = base.substring(0, 80);
        }

        if (!buildRepository.existsBySlug(base)) {
            return base;
        }

        int suffix = 1;
        while (buildRepository.existsBySlug(base + "-" + suffix)) {
            suffix++;
        }
        return base + "-" + suffix;
    }
}
