package com.example.demo1_conference_talks_api.service;

import com.example.demo1_conference_talks_api.dto.SpeakerRequestDto;
import com.example.demo1_conference_talks_api.dto.SpeakerResponseDto;
import com.example.demo1_conference_talks_api.entity.EntitySpeaker;
import com.example.demo1_conference_talks_api.exception.DuplicateResourceException;
import com.example.demo1_conference_talks_api.exception.ResourceNotFoundException;
import com.example.demo1_conference_talks_api.mapper.SpeakerMapper;
import com.example.demo1_conference_talks_api.repository.SpeakerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class SpeakerService {

    private final SpeakerRepository speakerRepository;
    private final SpeakerMapper speakerMapper;

    @Transactional(readOnly = true)
    public List<SpeakerResponseDto> getAll() {
        log.debug("Fetching all speakers");
        return speakerMapper.toDtoList(speakerRepository.findAll());
    }

    @Transactional
    public SpeakerResponseDto create(SpeakerRequestDto dto) {
        if (speakerRepository.existsByEmail(dto.getEmail())) {
            throw new DuplicateResourceException("Speaker with this email already exists");
        }
        EntitySpeaker entitySpeaker = speakerMapper.toEntitySpeaker(dto);
        EntitySpeaker saved = speakerRepository.save(entitySpeaker);
        log.info("Speaker created with id {}", saved.getId());
        return speakerMapper.toSpeakerResponse(saved);
    }

    @Transactional(readOnly = true)
    public SpeakerResponseDto findById(Long id) {
        log.debug("Fetching speaker {}", id);
        return speakerRepository.findById(id)
                .map(speakerMapper::toSpeakerResponse)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Speaker with id " + id + " not found"));
    }
}
