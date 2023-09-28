package io.caden.transformers.netflix.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class NetflixApiViewedItem {
  private Long date;
  private Long movieID;
  private Long series;
  private String title;
  private String seriesTitle;
  private String episodeTitle;
}
