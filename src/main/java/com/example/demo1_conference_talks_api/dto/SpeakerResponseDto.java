package com.example.demo1_conference_talks_api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Response DTO representing a speaker profile")
public class SpeakerResponseDto {

    @Schema(description = "Unique identifier of the speaker", example = "1")
    private Long id;

    @Schema(description = "Full name of the speaker", example = "Aysel Mammadova")
    private String fullName;

    @Schema(description = "Email address of the speaker", example = "aysel@conference.az")
    private String email;

    @Schema(description = "Company or organization the speaker represents", example = "Tech Solutions Inc.")
    private String company;
}
