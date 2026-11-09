package com.example.demo1_conference_talks_api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "DTO for creating or updating a speaker profile")
public class SpeakerRequestDto {

    @NotBlank(message = "Full name is required")
    @Size(min = 2, max = 100, message = "Full name must be 2-100 characters")
    @Schema(description = "Full name of the speaker", example = "Aysel Mammadova")
    private String fullName;

    @NotBlank(message = "Email is required")
    @Email(message = "Email format is invalid")
    @Size(max = 150, message = "Email must be at most 150 characters")
    @Schema(description = "Email address of the speaker", example = "aysel@conference.az")
    private String email;

    @Size(max = 100, message = "Company must be at most 100 characters")
    @Schema(description = "Company or organization the speaker represents", example = "Tech Solutions Inc.")
    private String company;
}
