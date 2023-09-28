package io.caden.transformers.amazon.entities;

import io.caden.transformers.shared.entities.RDFAbstractEntity;
import lombok.Data;

@Data
public class AmazonProductRichDescription extends RDFAbstractEntity {

  private String title;
  private String description;

}
