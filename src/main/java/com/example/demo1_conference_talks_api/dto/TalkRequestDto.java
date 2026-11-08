package com.example.demo1_conference_talks_api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@Schema(description = "DTO for creating or updating a conference talk")
public class TalkRequestDto {

    @Schema(description = "Title of the talk", example = "Advanced Spring Boot Architecture")
    private String title;
    @Schema(description = "Room where the talk will take place", example = "Room B")
    private String room;
    @Schema(description = "Duration of the talk in minutes", example = "45")
    private Long durationMinutes;
    @Schema(description = "Unique identifier of the speaker", example = "10")
    private Long speakerId;
}