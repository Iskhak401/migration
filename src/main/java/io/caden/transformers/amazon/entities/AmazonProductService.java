package io.caden.transformers.amazon.entities;

import io.caden.transformers.shared.entities.RDFAbstractEntity;
import lombok.Data;

@Data
public class AmazonProductService extends RDFAbstractEntity {

  private String title;
  private Double price;
  private String whatsIncluded;

}
