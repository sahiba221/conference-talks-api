package com.example.demo1_conference_talks_api.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = "talks")
@Table(name = "speakers")
public class EntitySpeaker {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String fullName;

    @Column(nullable = false, unique = true, length = 150)
    private String email;

    @Column(length = 100)
    private String company;

    @OneToMany(mappedBy = "speaker", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<EntityTalk> talks = new ArrayList<>();
}
