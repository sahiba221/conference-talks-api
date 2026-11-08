package com.example.demo1_conference_talks_api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "DTO for creating or updating a speaker profile")
public class SpeakerRequestDto {

    @Schema(description = "Unique identifier of the speaker (can be null when creating a new speaker)", example = "1")
    private Integer id;
    @Schema(description = "Full name of the speaker", example = "John Doe")
    private String fullName;
    @Schema(description = "Email address of the speaker", example = "john.doe@example.com")
    private String email;
    @Schema(description = "Company or organization the speaker represents", example = "Tech Solutions Inc.")
    private String company;
}