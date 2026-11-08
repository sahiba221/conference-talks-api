package com.example.demo1_conference_talks_api.repository;

import com.example.demo1_conference_talks_api.entity.EntityTalk;
import com.example.demo1_conference_talks_api.entity.TalkStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TalkRepository extends JpaRepository<EntityTalk, Long>, JpaSpecificationExecutor<EntityTalk> {

    List<EntityTalk> findBySpeakerId(Long speakerId);

    long countBySpeakerId(Long speakerId);

    List<EntityTalk> findByStatus(TalkStatus status);

    @Query("SELECT t.room, COUNT(t) FROM EntityTalk t GROUP BY t.room")
    List<Object[]> countTalksPerRoom();
}
