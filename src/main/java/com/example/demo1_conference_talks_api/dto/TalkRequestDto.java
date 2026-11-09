package com.example.demo1_conference_talks_api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "DTO for creating or updating a conference talk")
public class TalkRequestDto {

    @NotBlank(message = "Title is required")
    @Size(min = 5, max = 150, message = "Title must be 5-150 characters")
    @Schema(description = "Title of the talk", example = "Advanced Spring Boot Architecture")
    private String title;

    @NotBlank(message = "Room is required")
    @Schema(description = "Room where the talk will take place", example = "Room B")
    private String room;

    @NotNull(message = "Duration is required")
    @Min(value = 15, message = "Duration must be between 15 and 90 minutes")
    @Max(value = 90, message = "Duration must be between 15 and 90 minutes")
    @Schema(description = "Duration of the talk in minutes", example = "45")
    private Integer durationMinutes;

    @NotNull(message = "Speaker id is required")
    @Positive(message = "Speaker id must be a positive number")
    @Schema(description = "Unique identifier of the speaker", example = "1")
    private Long speakerId;

    @NotNull(message = "Talk must be scheduled in the future")
    @Future(message = "Talk must be scheduled in the future")
    @Schema(description = "Scheduled date and time of the talk", example = "2026-12-01T14:00:00")
    private LocalDateTime scheduledAt;
}
