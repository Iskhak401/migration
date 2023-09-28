package io.caden.transformers.amazon.entities;

import io.caden.transformers.shared.entities.RDFAbstractEntity;
import lombok.Data;

@Data
public class AmazonProductEnergyEfficiency extends RDFAbstractEntity {

  private String rating;
  private String ratingImage;
  private String productFicheImage;

}
