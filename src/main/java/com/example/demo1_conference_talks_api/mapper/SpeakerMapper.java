package com.example.demo1_conference_talks_api.mapper;

import com.example.demo1_conference_talks_api.dto.SpeakerRequestDto;
import com.example.demo1_conference_talks_api.dto.SpeakerResponseDto;
import com.example.demo1_conference_talks_api.entity.EntitySpeaker;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SpeakerMapper {

    SpeakerResponseDto toSpeakerResponse(EntitySpeaker entitySpeaker);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "talks", ignore = true)
    EntitySpeaker toEntitySpeaker(SpeakerRequestDto speakerRequestDto);

    List<SpeakerResponseDto> toDtoList(List<EntitySpeaker> entities);
}
