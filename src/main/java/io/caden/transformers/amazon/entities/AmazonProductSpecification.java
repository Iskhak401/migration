package io.caden.transformers.amazon.entities;

import io.caden.transformers.shared.entities.RDFAbstractEntity;
import lombok.Data;

@Data
public class AmazonProductSpecification extends RDFAbstractEntity {

  private String name;
  private String value;

}
