package io.caden.transformers.spotify.schedulers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import io.caden.transformers.spotify.services.SpotifyAccountTransformation;

@Configuration
@EnableScheduling
public class SpotifyTransformerJobScheduler {
  
  private static final Logger LOGGER = LoggerFactory.getLogger(SpotifyTransformerJobScheduler.class);


  private final SpotifyAccountTransformation spotifyAccountTransformation;

  public SpotifyTransformerJobScheduler(
    @Autowired final SpotifyAccountTransformation spotifyAccountTransformation
  ) {
    this.spotifyAccountTransformation = spotifyAccountTransformation;
  }

  @Scheduled(cron = "*/5 * * * * ?")
  public void spotifyAccountTransformationRunner() {
    try {
      this.spotifyAccountTransformation.execute();
    } catch (Exception e) {
      LOGGER.error("Error spotifyAccountTransformationRunner.", e);
    }
  }
}
