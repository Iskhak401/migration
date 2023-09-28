package io.caden.transformers.airbnb.schedulers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import io.caden.transformers.airbnb.services.AirbnbTripReservationsTransformation;

@Configuration
@EnableScheduling
public class AirbnbTransformerJobScheduler {

  private static final Logger LOGGER = LoggerFactory.getLogger(AirbnbTransformerJobScheduler.class);


  private final AirbnbTripReservationsTransformation airbnbTripReservationsTransformation;

  public AirbnbTransformerJobScheduler(
    @Autowired final AirbnbTripReservationsTransformation airbnbTripReservationsTransformation
  ) {
    this.airbnbTripReservationsTransformation = airbnbTripReservationsTransformation;
  }

  @Scheduled(cron = "*/5 * * * * ?")
  public void airbnbTripReservationsTransformationRunner() {
    try {
      this.airbnbTripReservationsTransformation.execute();
    } catch (Exception e) {
      LOGGER.error("Error airbnbTripReservationsTransformationRunner.", e);
    }
  }
}
