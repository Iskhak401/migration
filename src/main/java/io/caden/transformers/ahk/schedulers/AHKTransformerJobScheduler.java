package io.caden.transformers.ahk.schedulers;

import io.caden.transformers.ahk.services.AHKTransformation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableScheduling
public class AHKTransformerJobScheduler {
  private static final Logger LOGGER = LoggerFactory.getLogger(AHKTransformerJobScheduler.class);

  private final AHKTransformation ahkTransformation;

  public AHKTransformerJobScheduler(
    @Autowired final AHKTransformation ahkTransformation
  ) {
    this.ahkTransformation = ahkTransformation;
  }

  @Scheduled(cron = "* */5 * * * ?")
  public void ahkTransformationRunner() {
    try {
      this.ahkTransformation.execute();
    } catch (Exception e) {
      LOGGER.error("Error ahkTransformationRunner.", e);
    }
  }
}
