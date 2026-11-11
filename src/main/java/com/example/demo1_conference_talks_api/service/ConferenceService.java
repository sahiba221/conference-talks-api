package com.example.demo1_conference_talks_api.service;

import com.example.demo1_conference_talks_api.dto.SpeakerResponseDto;
import com.example.demo1_conference_talks_api.dto.SpeakerWithTalksRequestDto;
import com.example.demo1_conference_talks_api.dto.TalkRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class ConferenceService {

    private final SpeakerService speakerService;
    private final TalkService talkService;

    @Transactional
    public SpeakerResponseDto registerSpeakerWithTalks(SpeakerWithTalksRequestDto dto) {
        log.info("Registering speaker with {} talks in a single transaction", dto.getTalks().size());
        SpeakerResponseDto createdSpeaker = speakerService.create(dto.getSpeaker());

        for (TalkRequestDto talkDto : dto.getTalks()) {
            talkDto.setSpeakerId(createdSpeaker.getId());
            if (talkDto.getScheduledAt() == null) {
                talkDto.setScheduledAt(LocalDateTime.now().plusHours(1));
            }
            talkService.create(talkDto);
        }

        log.info("Successfully registered speaker {} with all talks", createdSpeaker.getId());
        return createdSpeaker;
    }
}
