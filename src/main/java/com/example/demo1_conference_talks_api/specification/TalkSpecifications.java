package com.example.demo1_conference_talks_api.specification;

import com.example.demo1_conference_talks_api.entity.EntityTalk;
import org.springframework.data.jpa.domain.Specification;

public final class TalkSpecifications {

    private TalkSpecifications() {
    }

    public static Specification<EntityTalk> titleContains(String title) {
        if (title == null || title.isBlank()) {
            return null;
        }
        return (root, query, cb) ->
                cb.like(cb.lower(root.get("title")), "%" + title.trim().toLowerCase() + "%");
    }

    public static Specification<EntityTalk> inRoom(String room) {
        if (room == null || room.isBlank()) {
            return null;
        }
        return (root, query, cb) ->
                cb.equal(cb.lower(root.get("room")), room.trim().toLowerCase());
    }

    public static Specification<EntityTalk> minDuration(Integer minDuration) {
        if (minDuration == null) {
            return null;
        }
        return (root, query, cb) ->
                cb.greaterThanOrEqualTo(root.get("durationMinutes"), minDuration);
    }

    public static Specification<EntityTalk> maxDuration(Integer maxDuration) {
        if (maxDuration == null) {
            return null;
        }
        return (root, query, cb) ->
                cb.lessThanOrEqualTo(root.get("durationMinutes"), maxDuration);
    }
}
