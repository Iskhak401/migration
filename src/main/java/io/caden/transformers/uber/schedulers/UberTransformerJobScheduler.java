package io.caden.transformers.uber.schedulers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import io.caden.transformers.uber.services.UberTripsTransformation;

@Configuration
@EnableScheduling
public class UberTransformerJobScheduler {

  private static final Logger LOGGER = LoggerFactory.getLogger(UberTransformerJobScheduler.class);


  private final UberTripsTransformation uberTripsTransformation;

  public UberTransformerJobScheduler(
    @Autowired final UberTripsTransformation uberTripsTransformation
  ) {
    this.uberTripsTransformation = uberTripsTransformation;
  }

  @Scheduled(cron = "*/5 * * * * ?")
  public void uberTripsTransformationRunner() {
    try {
      this.uberTripsTransformation.execute();
    } catch (Exception e) {
      LOGGER.error("Error uberTripsTransformationRunner.", e);
    }
  }
}
