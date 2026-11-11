package com.example.demo1_conference_talks_api.service;

import com.example.demo1_conference_talks_api.dto.TalkRequestDto;
import com.example.demo1_conference_talks_api.dto.TalkResponseDto;
import com.example.demo1_conference_talks_api.entity.EntitySpeaker;
import com.example.demo1_conference_talks_api.entity.EntityTalk;
import com.example.demo1_conference_talks_api.entity.TalkStatus;
import com.example.demo1_conference_talks_api.exception.BusinessRuleException;
import com.example.demo1_conference_talks_api.exception.ResourceNotFoundException;
import com.example.demo1_conference_talks_api.mapper.TalkMapper;
import com.example.demo1_conference_talks_api.repository.SpeakerRepository;
import com.example.demo1_conference_talks_api.repository.TalkRepository;
import com.example.demo1_conference_talks_api.specification.TalkSpecifications;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Slf4j
@Service
public class TalkService {

    private static final List<String> ALLOWED_SORT_FIELDS = List.of("title", "room", "durationMinutes");

    private final TalkRepository talkRepository;
    private final TalkMapper talkMapper;
    private final SpeakerRepository speakerRepository;

    @Transactional(readOnly = true)
    public Page<TalkResponseDto> getAll(int page, int size, String sortBy, String direction) {
        log.debug("Fetching talks: page={}, size={}, sortBy={}, direction={}", page, size, sortBy, direction);
        Pageable pageable = buildAndValidatePageable(page, size, sortBy, direction);
        return talkRepository.findAll(pageable).map(talkMapper::toTalkResponseDto);
    }

    @Transactional(readOnly = true)
    public Page<TalkResponseDto> search(String title, String room,
                                        Integer minDuration, Integer maxDuration,
                                        int page, int size, String sortBy, String direction) {
        log.debug("Searching talks: title={}, room={}, minDuration={}, maxDuration={}, page={}, size={}, sortBy={}, direction={}",
                title, room, minDuration, maxDuration, page, size, sortBy, direction);

        if (minDuration != null && maxDuration != null && minDuration > maxDuration) {
            throw new BusinessRuleException("minDuration cannot be greater than maxDuration");
        }

        Pageable pageable = buildAndValidatePageable(page, size, sortBy, direction);

        Specification<EntityTalk> spec = (root, query, cb) -> cb.conjunction();
        Specification<EntityTalk> titleSpec = TalkSpecifications.titleContains(title);
        if (titleSpec != null) {
            spec = spec.and(titleSpec);
        }
        Specification<EntityTalk> roomSpec = TalkSpecifications.inRoom(room);
        if (roomSpec != null) {
            spec = spec.and(roomSpec);
        }
        Specification<EntityTalk> minSpec = TalkSpecifications.minDuration(minDuration);
        if (minSpec != null) {
            spec = spec.and(minSpec);
        }
        Specification<EntityTalk> maxSpec = TalkSpecifications.maxDuration(maxDuration);
        if (maxSpec != null) {
            spec = spec.and(maxSpec);
        }

        return talkRepository.findAll(spec, pageable).map(talkMapper::toTalkResponseDto);
    }

    @Transactional(readOnly = true)
    public TalkResponseDto findById(Long id) {
        log.debug("Fetching talk {}", id);
        return talkRepository.findById(id)
                .map(talkMapper::toTalkResponseDto)
                .orElseThrow(() -> new ResourceNotFoundException("Talk with id " + id + " not found"));
    }

    @Transactional
    public TalkResponseDto create(TalkRequestDto dto) {
        EntitySpeaker speaker = speakerRepository.findById(dto.getSpeakerId())
                .orElseThrow(() -> new ResourceNotFoundException("Speaker with id " + dto.getSpeakerId() + " not found"));

        validateTalkDuration(dto.getDurationMinutes());

        long currentTalkCount = talkRepository.countBySpeakerId(speaker.getId());
        if (currentTalkCount >= 3) {
            throw new BusinessRuleException("Speaker " + speaker.getId() + " already has " + currentTalkCount + " talks (maximum is 3)");
        }

        EntityTalk entityTalk = talkMapper.toEntityTalk(dto);
        entityTalk.setSpeaker(speaker);
        entityTalk.setStatus(TalkStatus.SCHEDULED);
        if (entityTalk.getScheduledAt() == null) {
            entityTalk.setScheduledAt(LocalDateTime.now().plusHours(1));
        }

        EntityTalk saved = talkRepository.save(entityTalk);
        log.info("Talk created with id {} for speaker {}", saved.getId(), speaker.getId());
        return talkMapper.toTalkResponseDto(saved);
    }

    @Transactional
    public TalkResponseDto update(Long id, TalkRequestDto dto) {
        EntityTalk existingTalk = talkRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Talk with id " + id + " not found"));

        validateTalkDuration(dto.getDurationMinutes());

        if (!existingTalk.getSpeaker().getId().equals(dto.getSpeakerId())) {
            EntitySpeaker newSpeaker = speakerRepository.findById(dto.getSpeakerId())
                    .orElseThrow(() -> new ResourceNotFoundException("Speaker with id " + dto.getSpeakerId() + " not found"));
            long count = talkRepository.countBySpeakerId(newSpeaker.getId());
            if (count >= 3) {
                throw new BusinessRuleException("Speaker " + newSpeaker.getId() + " already has " + count + " talks (maximum is 3)");
            }
            existingTalk.setSpeaker(newSpeaker);
        }

        talkMapper.updateEntityFromDto(dto, existingTalk);
        EntityTalk updated = talkRepository.save(existingTalk);
        log.info("Talk updated with id {}", updated.getId());
        return talkMapper.toTalkResponseDto(updated);
    }

    @Transactional
    public void deleteById(Long id) {
        if (!talkRepository.existsById(id)) {
            throw new ResourceNotFoundException("Talk with id " + id + " not found");
        }
        talkRepository.deleteById(id);
        log.info("Talk deleted with id {}", id);
    }

    @Transactional(readOnly = true)
    public List<TalkResponseDto> getTalksOfSpeaker(Long speakerId) {
        log.debug("Fetching talks for speaker {}", speakerId);
        if (!speakerRepository.existsById(speakerId)) {
            throw new ResourceNotFoundException("Speaker with id " + speakerId + " not found");
        }
        List<EntityTalk> talks = talkRepository.findBySpeakerId(speakerId);
        return talkMapper.toDtoList(talks);
    }

    private void validateTalkDuration(Integer durationMinutes) {
        if (durationMinutes == null || durationMinutes < 15 || durationMinutes > 90) {
            throw new BusinessRuleException("Talk duration must be between 15 and 90 minutes");
        }
    }

    private Pageable buildAndValidatePageable(int page, int size, String sortBy, String direction) {
        if (page < 0 || size < 1 || size > 50) {
            throw new BusinessRuleException("Size must be between 1 and 50, page must be 0 or more");
        }
        if (!ALLOWED_SORT_FIELDS.contains(sortBy)) {
            throw new BusinessRuleException("SortBy is allowed only for title, room, durationMinutes");
        }
        if (!"asc".equalsIgnoreCase(direction) && !"desc".equalsIgnoreCase(direction)) {
            throw new BusinessRuleException("Direction must be asc or desc");
        }
        Sort.Direction dir = Sort.Direction.fromString(direction);
        return PageRequest.of(page, size, Sort.by(dir, sortBy));
    }
}
