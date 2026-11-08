package com.example.demo1_conference_talks_api.controller;

import com.example.demo1_conference_talks_api.dto.TalkResponseDto;
import com.example.demo1_conference_talks_api.entity.EntityTalk;
import com.example.demo1_conference_talks_api.service.TalkService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/talks")
@Tag(name = "Talks Controller", description = "Endpoints for managing conference talks")
public class TalkController {
    private final TalkService talkService;

//    @Operation(summary = "Get all talks",
//            description = "Retrieves a list of all talk responses")
//    @GetMapping
//    public List<TalkResponseDto> getAll(){
//        return talkService.getAll();
//    }

    @Operation(summary = "Get all required talks",
            description = "Retrieves a required list of all talk responses")
    @GetMapping()
    public Page<TalkResponseDto> getAll(@RequestParam int size, @RequestParam int page,
                                        @RequestParam String direction,
                                        @RequestParam Pageable pageable){
        return talkService.getAll(page, size, "", direction, pageable);
    }

    @Operation(summary = "Get talk by ID",
            description = "Finds and returns a specific talk response by its ID")
    @GetMapping("/{id}")
    public TalkResponseDto findById(
            @Parameter(description = "Unique identifier of the talk", example = "1")
            @PathVariable int id){
        return talkService.findById(id);
    }

    @Operation(summary = "Create a talk",
            description = "Creates a new talk and adds it to the schedule")
    @PostMapping
    public TalkResponseDto create(int id, EntityTalk entityTalk){
        return talkService.create(id, entityTalk);
    }

    @Operation(summary = "Delete a talk",
            description = "Deletes a talk from the schedule by its ID")
    @DeleteMapping("/{id}")
    public void deleteById(
            @Parameter(description = "Unique identifier of the talk to delete",
                    example = "1")
            @PathVariable int id){
        talkService.deleteById(id);
    }

    @Operation(summary = "Get talks by speaker ID",
            description = "Retrieves a list of talks associated with a specific speaker")
    @GetMapping("/speaker/{id}")
    public List<TalkResponseDto> getTalksOfSpeaker(
            @Parameter(description = "Unique identifier of the speaker", example = "1")
            @PathVariable int id){
        return talkService.getTalksOfSpeaker(id);
    }
}