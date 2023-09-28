package io.caden.transformers.netflix.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.javacrumbs.shedlock.spring.annotation.SchedulerLock;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableScheduling
@Slf4j
@RequiredArgsConstructor
public class NetflixViewingActivitiesFlusher {

    private final NetflixViewingActivitiesTransformation netflixViewingActivitiesTransformation;

    @Scheduled(cron = "0 0/1 * 1/1 * ?")
    @SchedulerLock(name = "netflixViewingActivitiesFlusher", lockAtMostFor = "30m")
    public void viewingActivitiesFlusher() {
        try{
            netflixViewingActivitiesTransformation.flush();
        }catch (Exception e) {
            log.error("An exception has occurred while flushing Netflix viewing actions", e);
        }
    }

}
