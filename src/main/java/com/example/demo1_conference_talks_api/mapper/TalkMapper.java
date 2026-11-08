package com.example.demo1_conference_talks_api.mapper;

import com.example.demo1_conference_talks_api.dto.TalkRequestDto;
import com.example.demo1_conference_talks_api.dto.TalkResponseDto;
import com.example.demo1_conference_talks_api.entity.EntityTalk;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TalkMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "speaker", ignore = true)
    @Mapping(target = "status", ignore = true)
    EntityTalk toEntityTalk(TalkRequestDto talkRequestDto);

    @Mapping(source = "speaker.id", target = "speakerId")
    @Mapping(source = "speaker.fullName", target = "speakerName")
    TalkResponseDto toTalkResponseDto(EntityTalk entityTalk);

    List<TalkResponseDto> toDtoList(List<EntityTalk> entities);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "speaker", ignore = true)
    @Mapping(target = "status", ignore = true)
    void updateEntityFromDto(TalkRequestDto dto, @MappingTarget EntityTalk entity);
}
