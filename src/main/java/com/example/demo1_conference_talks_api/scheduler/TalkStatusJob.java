package com.example.demo1_conference_talks_api.scheduler;

import com.example.demo1_conference_talks_api.entity.EntityTalk;
import com.example.demo1_conference_talks_api.entity.TalkStatus;
import com.example.demo1_conference_talks_api.repository.TalkRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
public class TalkStatusJob {

    private final TalkRepository talkRepository;

    @Scheduled(cron = "0 * * * * *", zone = "Asia/Baku")
    @Transactional
    public void finishCompletedTalks() {
        LocalDateTime now = LocalDateTime.now();
        List<EntityTalk> scheduledTalks = talkRepository.findByStatus(TalkStatus.SCHEDULED);

        int count = 0;
        for (EntityTalk talk : scheduledTalks) {
            if (talk.getScheduledAt() != null && talk.getDurationMinutes() != null) {
                LocalDateTime endTime = talk.getScheduledAt().plusMinutes(talk.getDurationMinutes());
                if (endTime.isBefore(now)) {
                    talk.setStatus(TalkStatus.FINISHED);
                    count++;
                }
            }
        }

        if (count > 0) {
            log.info("{} talks marked as FINISHED", count);
        } else {
            log.debug("No scheduled talks needed to be marked as FINISHED");
        }
    }
}
