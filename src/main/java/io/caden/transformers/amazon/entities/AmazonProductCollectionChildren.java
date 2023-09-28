package io.caden.transformers.amazon.entities;

import io.caden.transformers.shared.entities.RDFAbstractEntity;
import lombok.Data;

@Data
public class AmazonProductCollectionChildren extends RDFAbstractEntity {

  private String title;
  private String asin;
  private String link;

}
