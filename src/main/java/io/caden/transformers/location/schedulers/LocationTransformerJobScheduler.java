package io.caden.transformers.location.schedulers;

import io.caden.transformers.location.services.LocationTransformation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.javacrumbs.shedlock.core.LockAssert;
import net.javacrumbs.shedlock.spring.annotation.SchedulerLock;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableScheduling
@Slf4j
@RequiredArgsConstructor
public class LocationTransformerJobScheduler {

  private final LocationTransformation locationTransformation;

  @Scheduled(cron = "0 0/1 * 1/1 * ?")
  @SchedulerLock(name = "locationTransformationFlusher", lockAtMostFor = "30m")
  public void locationTransformationFlusher() {
    LockAssert.assertLocked();
    try {
      this.locationTransformation.flush();
    } catch (Exception e) {
      log.error("Error locationTransformationRunner.", e);
    }
  }
}
