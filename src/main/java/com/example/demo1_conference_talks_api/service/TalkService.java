package com.example.demo1_conference_talks_api.service;

import com.example.demo1_conference_talks_api.dto.TalkResponseDto;
import com.example.demo1_conference_talks_api.entity.EntitySpeaker;
import com.example.demo1_conference_talks_api.entity.EntityTalk;
import com.example.demo1_conference_talks_api.exception.BusinessRuleException;
import com.example.demo1_conference_talks_api.exception.ResourceNotFoundException;
import com.example.demo1_conference_talks_api.mapper.TalkMapper;
import com.example.demo1_conference_talks_api.repository.SpeakerRepository;
import com.example.demo1_conference_talks_api.repository.TalkRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import java.util.List;


@RequiredArgsConstructor
@Slf4j
@Service
public class TalkService {
    private final TalkRepository talkRepository;
    private final TalkMapper talkMapper;
    private final SpeakerRepository speakerRepository;


//    public List<TalkResponseDto> getAll(){
//        return talkRepository.findAll().stream().map(talkMapper::toTalkResponseDto)
//                .toList();
//    }

    public Page<TalkResponseDto> getAll(int page, int size, String sortBy,
                                        String direction, Pageable pageable){
        log.info("Request to find all required talks ");
        if(page <= 0 || size <1 || size >50){
            log.warn("Size must be between 1 and 50, page must be 0 or more");
            throw  new BusinessRuleException("Size must be between 1 and 50," +
                    " page must be 0 or more");

        }
        if(!List.of( "title", "room", "durationMinutes").contains(sortBy)){
            log.warn("SortBy is allowed only for title, room, durationMinutes");
            throw new BusinessRuleException("SortBy is allowed only for title, " +
                    "room, durationMinutes");
        }

        if(!direction.equalsIgnoreCase("asc") &&
                !direction.equalsIgnoreCase("desc")){
                    log.warn("Validation failed: direction must be asc or " +
                            "desc. Provided: {}", direction);
            throw new BusinessRuleException("Direction must be asc or desc");
        }
        Sort.Direction dir = Sort.Direction.fromString(direction);
        Pageable pageable1 = PageRequest.of(page, size, Sort.by(dir, sortBy));
        Page<EntityTalk> entityTalksPage = talkRepository.findAll(pageable);
        log.info("All required talks were successfully found");
        return entityTalksPage.map(talkMapper::toTalkResponseDto);

    }

    public TalkResponseDto findById(int id){
        return talkRepository.findById(id).map(talkMapper::toTalkResponseDto)
                .orElseThrow(() -> new ResourceNotFoundException("Talk with id "
                        + id + " was not found"));
    }

    public void deleteById(int id){
        if(!talkRepository.existsById(id)){
            throw new ResourceNotFoundException("Talk with id "+ id + " was not found");
        }
        talkRepository.deleteById(id);
    }

    public TalkResponseDto create(int id, EntityTalk entityTalk) {
        if (talkRepository.existsById(id)) {
            throw new BusinessRuleException("Talk with id " + id + " already exists");
        }
        if (!speakerRepository.existsById((long) id)) {
            throw new ResourceNotFoundException("Speaker with id "+ id + "was not found");
        }
            EntityTalk newTalk = talkRepository.save(entityTalk);
            return talkMapper.toTalkResponseDto(newTalk);
    }

    public List<TalkResponseDto> getTalksOfSpeaker(int id){
        if(!speakerRepository.existsById((long) id)){
            throw new ResourceNotFoundException("Speaker with id "+ id + "was not found");
        }
        EntitySpeaker speaker = speakerRepository.findById((long) id).orElseThrow(()
                -> new ResourceNotFoundException("Speaker with id "+ id + "was not found"));

        List<EntityTalk> talks = speaker.getTalks();
        return talks.stream().map(talkMapper::toTalkResponseDto).toList();
    }


}

