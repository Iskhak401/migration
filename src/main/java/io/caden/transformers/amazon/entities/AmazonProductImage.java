package io.caden.transformers.amazon.entities;

import io.caden.transformers.shared.entities.RDFAbstractEntity;
import lombok.Data;

@Data
public class AmazonProductImage extends RDFAbstractEntity {

  private String variant;
  private String link;

}
