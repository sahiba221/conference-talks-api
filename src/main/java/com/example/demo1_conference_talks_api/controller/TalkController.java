package com.example.demo1_conference_talks_api.controller;

import com.example.demo1_conference_talks_api.dto.TalkRequestDto;
import com.example.demo1_conference_talks_api.dto.TalkResponseDto;
import com.example.demo1_conference_talks_api.service.TalkService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@Validated
@RequestMapping({"/api/talks", "/talks"})
@Tag(name = "Talks Controller", description = "Endpoints for managing conference talks")
public class TalkController {

    private final TalkService talkService;

    @Operation(summary = "Get paginated talks",
            description = "Retrieves a paginated and sorted page of conference talks")
    @GetMapping
    public Page<TalkResponseDto> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "title") String sortBy,
            @RequestParam(defaultValue = "asc") String direction) {
        return talkService.getAll(page, size, sortBy, direction);
    }

    @Operation(summary = "Search talks with dynamic filters",
            description = "Searches talks by optional title, room, minDuration, and maxDuration filters with pagination")
    @GetMapping("/search")
    public Page<TalkResponseDto> search(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String room,
            @RequestParam(required = false) Integer minDuration,
            @RequestParam(required = false) Integer maxDuration,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "title") String sortBy,
            @RequestParam(defaultValue = "asc") String direction) {
        return talkService.search(title, room, minDuration, maxDuration, page, size, sortBy, direction);
    }

    @Operation(summary = "Get talk by ID",
            description = "Finds and returns a specific talk by its ID")
    @GetMapping("/{id}")
    public TalkResponseDto findById(
            @Parameter(description = "Unique identifier of the talk", example = "1")
            @PathVariable @Positive(message = "Id must be a positive number") Long id) {
        return talkService.findById(id);
    }

    @Operation(summary = "Create a talk",
            description = "Creates a new talk and adds it to the conference schedule")
    @PostMapping
    public ResponseEntity<TalkResponseDto> create(@Valid @RequestBody TalkRequestDto talkRequestDto) {
        TalkResponseDto created = talkService.create(talkRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @Operation(summary = "Update an existing talk",
            description = "Updates an existing talk by ID using MapStruct @MappingTarget")
    @PutMapping("/{id}")
    public TalkResponseDto update(
            @Parameter(description = "Unique identifier of the talk to update", example = "1")
            @PathVariable @Positive(message = "Id must be a positive number") Long id,
            @Valid @RequestBody TalkRequestDto talkRequestDto) {
        return talkService.update(id, talkRequestDto);
    }

    @Operation(summary = "Delete a talk",
            description = "Deletes a talk from the schedule by its ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(
            @Parameter(description = "Unique identifier of the talk to delete", example = "1")
            @PathVariable @Positive(message = "Id must be a positive number") Long id) {
        talkService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Get talks by speaker ID",
            description = "Retrieves a list of talks associated with a specific speaker")
    @GetMapping("/speaker/{id}")
    public List<TalkResponseDto> getTalksOfSpeaker(
            @Parameter(description = "Unique identifier of the speaker", example = "1")
            @PathVariable @Positive(message = "Id must be a positive number") Long id) {
        return talkService.getTalksOfSpeaker(id);
    }
}
