package com.example.demo1_conference_talks_api.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "talks")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = "speaker")
public class EntityTalk {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 150)
    private String title;

    @Column(nullable = false)
    private String room;

    @Column(nullable = false)
    private Integer durationMinutes;

    private LocalDateTime scheduledAt;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TalkStatus status = TalkStatus.SCHEDULED;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "speaker_id", nullable = false)
    private EntitySpeaker speaker;
}
