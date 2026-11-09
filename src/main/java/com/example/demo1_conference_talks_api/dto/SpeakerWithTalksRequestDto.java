package com.example.demo1_conference_talks_api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "DTO for registering a speaker along with their talks in a single transaction")
public class SpeakerWithTalksRequestDto {

    @NotNull(message = "Speaker details are required")
    @Valid
    private SpeakerRequestDto speaker;

    @NotEmpty(message = "At least one talk is required")
    private List<TalkRequestDto> talks;
}
