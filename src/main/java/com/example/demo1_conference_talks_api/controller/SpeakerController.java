package com.example.demo1_conference_talks_api.controller;

import com.example.demo1_conference_talks_api.dto.SpeakerRequestDto;
import com.example.demo1_conference_talks_api.dto.SpeakerResponseDto;
import com.example.demo1_conference_talks_api.dto.SpeakerWithTalksRequestDto;
import com.example.demo1_conference_talks_api.dto.TalkResponseDto;
import com.example.demo1_conference_talks_api.service.ConferenceService;
import com.example.demo1_conference_talks_api.service.SpeakerService;
import com.example.demo1_conference_talks_api.service.TalkService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Speakers", description = "API for managing conference speakers")
@RequestMapping({"/api/speakers", "/speakers"})
@RestController
@RequiredArgsConstructor
@Validated
public class SpeakerController {

    private final SpeakerService speakerService;
    private final TalkService talkService;
    private final ConferenceService conferenceService;

    @Operation(summary = "Get all speakers",
            description = "Retrieves a list of all available speakers")
    @GetMapping
    public List<SpeakerResponseDto> getAll() {
        return speakerService.getAll();
    }

    @Operation(summary = "Create a speaker",
            description = "Creates a new speaker profile")
    @PostMapping
    public ResponseEntity<SpeakerResponseDto> create(@Valid @RequestBody SpeakerRequestDto speakerRequestDto) {
        SpeakerResponseDto created = speakerService.create(speakerRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @Operation(summary = "Find speaker by ID",
            description = "Returns information about a specific speaker by their ID")
    @GetMapping("/{id}")
    public SpeakerResponseDto findById(
            @Parameter(description = "Unique identifier of the speaker", example = "1")
            @PathVariable @Positive(message = "Id must be a positive number") Long id) {
        return speakerService.findById(id);
    }

    @Operation(summary = "Get all talks of a speaker",
            description = "Returns all talks given by a specific speaker")
    @GetMapping("/{id}/talks")
    public List<TalkResponseDto> getSpeakerTalks(
            @Parameter(description = "Unique identifier of the speaker", example = "1")
            @PathVariable @Positive(message = "Id must be a positive number") Long id) {
        return talkService.getTalksOfSpeaker(id);
    }

    @Operation(summary = "Register speaker with talks",
            description = "Registers a new speaker and their talks atomically in a single transaction")
    @PostMapping("/with-talks")
    public ResponseEntity<SpeakerResponseDto> registerSpeakerWithTalks(
            @Valid @RequestBody SpeakerWithTalksRequestDto dto) {
        SpeakerResponseDto created = conferenceService.registerSpeakerWithTalks(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }
}
