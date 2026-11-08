package com.example.demo1_conference_talks_api.repository;

import com.example.demo1_conference_talks_api.entity.EntitySpeaker;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpeakerRepository extends JpaRepository<EntitySpeaker, Long> {
    boolean existsByEmail(String email);
}
