package io.caden.transformers.megraph.schedulers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import io.caden.transformers.megraph.services.MeGraphTransformation;

@Configuration
@EnableScheduling
public class MeGraphTransformerJobScheduler {

  private static final Logger LOGGER = LoggerFactory.getLogger(MeGraphTransformerJobScheduler.class);


  private final MeGraphTransformation meGraphTransformation;

  public MeGraphTransformerJobScheduler(
    @Autowired final MeGraphTransformation meGraphTransformation
  ) {
    this.meGraphTransformation = meGraphTransformation;
  }

  @Scheduled(cron = "*/5 * * * * ?")
  public void meGraphTransformationRunner() {
    try {
      this.meGraphTransformation.execute();
    } catch (Exception e) {
      LOGGER.error("Error meGraphTransformationRunner.", e);
    }
  }
}
