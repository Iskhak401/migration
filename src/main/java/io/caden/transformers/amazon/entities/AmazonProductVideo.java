package io.caden.transformers.amazon.entities;

import io.caden.transformers.shared.entities.RDFAbstractEntity;
import lombok.Data;

@Data
public class AmazonProductVideo extends RDFAbstractEntity {

  private String title;
  private String groupId;
  private String groupType;
  private String link;
  private Integer durationSeconds;
  private Integer width;
  private Integer height;
  private String thumbnail;
  private Boolean heroVideo;
  private String variant;

}
