package io.caden.transformers.amazon.entities;

import io.caden.transformers.shared.entities.RDFAbstractEntity;
import lombok.Data;

@Data
public class AmazonProductBestsellersRank extends RDFAbstractEntity {

  private Integer rank;
  private String category;
  private String link;

}
