package com.example.demo1_conference_talks_api.service;

import com.example.demo1_conference_talks_api.dto.SpeakerResponseDto;
import com.example.demo1_conference_talks_api.entity.EntitySpeaker;
import com.example.demo1_conference_talks_api.exception.BusinessRuleException;
import com.example.demo1_conference_talks_api.exception.ResourceNotFoundException;
import com.example.demo1_conference_talks_api.mapper.SpeakerMapper;
import com.example.demo1_conference_talks_api.repository.SpeakerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
@Slf4j
public class SpeakerService {
    private final SpeakerRepository speakerRepository;
    private final SpeakerMapper speakerMapper;




//    public Page<SpeakerResponseDto> getAll(Pageable pageable){
//        Page<SpeakerResponseDto> speakerResponseDtosPage = speakerRepository
//                .findAll(pageable).map(speakerMapper::toSpeakerResponse);
//        return speakerResponseDtosPage;
//    }
//
//
//    public Page<SpeakerResponseDto> getAll(Pageable pageable){
//        log.info("Request to get all required speakers ");
//        Page<EntitySpeaker>  entitySpeakersPage = speakerRepository.findAll(pageable);
//        log.info("All required speakers were successfully founded");
//        return entitySpeakersPage.map(speakerMapper::toSpeakerResponse);
//    }

    public List<SpeakerResponseDto> getAll(){
        return speakerRepository.findAll().stream()
                .map(speakerMapper::toSpeakerResponse).toList();
    }

   public SpeakerResponseDto create(Long id, EntitySpeaker entitySpeaker){
       if(speakerRepository.existsById(id)){
           log.error("Speaker with Id {} already exists ", id);
           throw new BusinessRuleException("Speaker with ID : "
                   + id + " was not found");
       }
       EntitySpeaker newSpeaker = speakerRepository.save(entitySpeaker);
       return speakerMapper.toSpeakerResponse(newSpeaker);
   }

    public SpeakerResponseDto findById(Long id){
       return speakerRepository.findById(id).map(speakerMapper::toSpeakerResponse)
               .orElseThrow(() -> new ResourceNotFoundException
                       ("Speaker with id" + id + " was not found"));
    }


   }



