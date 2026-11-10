package com.example.demo1_conference_talks_api.scheduler;

import com.example.demo1_conference_talks_api.repository.SpeakerRepository;
import com.example.demo1_conference_talks_api.repository.TalkRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@Slf4j
@RequiredArgsConstructor
public class TalkStatisticsJob {

    private final SpeakerRepository speakerRepository;
    private final TalkRepository talkRepository;

    @Scheduled(fixedRateString = "${conference.report.rate-ms}", initialDelay = 10000)
    public void reportStatistics() {
        try {
            log.debug("Statistics job started");

            long speakerCount = speakerRepository.count();
            long talkCount = talkRepository.count();
            log.info("Conference summary: total speakers = {}, total talks = {}", speakerCount, talkCount);

            List<Object[]> roomCounts = talkRepository.countTalksPerRoom();
            String perRoomReport = roomCounts.isEmpty()
                    ? "no talks scheduled yet"
                    : roomCounts.stream()
                            .map(row -> row[0] + "=" + row[1])
                            .collect(Collectors.joining(", "));
            log.info("Talks per room: {}", perRoomReport);
        } catch (Exception ex) {
            log.error("Statistics job failed", ex);
        }
    }
}
