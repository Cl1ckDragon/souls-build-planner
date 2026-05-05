package com.soulsplanner.controller;

import com.soulsplanner.entity.Build;
import com.soulsplanner.service.BuildService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/builds")
@Tag(name = "Builds", description = "Browse and manage character builds")
@RequiredArgsConstructor
public class BuildController {

    private final BuildService buildService;

    @GetMapping
    @Operation(summary = "List public builds, sorted by upvotes descending")
    public ResponseEntity<Page<Build>> getPublicBuilds(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "20") int size
    ) {
        return ResponseEntity.ok(
            buildService.getPublicBuilds(PageRequest.of(page, size, Sort.by("upvoteCount").descending()))
        );
    }

    @GetMapping("/{slug}")
    @Operation(summary = "Get a single build by its URL slug")
    public ResponseEntity<Build> getBuildBySlug(@PathVariable String slug) {
        return buildService.getBySlug(slug)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Create a new build", security = @SecurityRequirement(name = "Bearer Auth"))
    public ResponseEntity<Build> createBuild(@RequestBody Build build) {
        return ResponseEntity.ok(buildService.createBuild(build));
    }
}
