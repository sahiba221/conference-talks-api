package com.example.demo1_conference_talks_api.dto;

import com.example.demo1_conference_talks_api.entity.TalkStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Response DTO representing a conference talk")
public class TalkResponseDto {

    @Schema(description = "Unique identifier of the talk", example = "1")
    private Long id;

    @Schema(description = "Title of the talk", example = "Advanced Spring Boot Architecture")
    private String title;

    @Schema(description = "Room where the talk will take place", example = "Room B")
    private String room;

    @Schema(description = "Duration of the talk in minutes", example = "45")
    private Integer durationMinutes;

    @Schema(description = "Unique identifier of the speaker", example = "1")
    private Long speakerId;

    @Schema(description = "Full name of the speaker", example = "Aysel Mammadova")
    private String speakerName;

    @Schema(description = "Scheduled date and time of the talk", example = "2026-12-01T14:00:00")
    private LocalDateTime scheduledAt;

    @Schema(description = "Current status of the talk", example = "SCHEDULED")
    private TalkStatus status;
}
