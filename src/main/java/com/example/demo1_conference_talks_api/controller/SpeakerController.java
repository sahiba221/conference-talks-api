package com.example.demo1_conference_talks_api.controller;

import com.example.demo1_conference_talks_api.dto.SpeakerResponseDto;
import com.example.demo1_conference_talks_api.entity.EntitySpeaker;
import com.example.demo1_conference_talks_api.service.SpeakerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Tag(name = "Speakers", description = "API for managing conference speakers")
@RequestMapping("/speakers")
@RestController
@RequiredArgsConstructor
public class SpeakerController {

    private final SpeakerService speakerService;

    @Operation(summary = "Get all speakers",
            description = "Retrieves a list of all available speakers")
    @GetMapping()

    public List<SpeakerResponseDto> getAll() {
        return speakerService.getAll();
    }

//    @Operation(summary = "Get all required speakers",
//            description = "Retrieves a list of all required and available speakers")
//    @GetMapping
//    public Page<SpeakerResponseDto> getAll(Pageable pageable){
//        return speakerService.getAll(pageable);
//    }


    @Operation(summary = "Create a speaker",
            description = "Creates a new speaker with the specified ID")
    @PostMapping("/{id}")
    public SpeakerResponseDto create(
            @Parameter(description = "Unique identifier of the speaker", example = "1")
            @PathVariable Long id,
            @RequestBody EntitySpeaker entitySpeaker) {
        return speakerService.create(id, entitySpeaker);
    }

    @Operation(summary = "Find speaker by ID",
            description = "Returns information about a specific speaker by their ID")
    @GetMapping("/{id}")
    public SpeakerResponseDto findById(
            @Parameter(description = "Unique identifier of the speaker", example = "1")
            @PathVariable Long id) {
        return speakerService.findById(id);
    }
}