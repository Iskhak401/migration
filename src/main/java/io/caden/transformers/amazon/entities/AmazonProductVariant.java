package io.caden.transformers.amazon.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import io.caden.transformers.shared.entities.RDFAbstractEntity;
import lombok.Data;

import java.util.Set;

@Data
public class AmazonProductVariant extends RDFAbstractEntity {

  private String title;
  private String asin;
  private String link;
  private String format;
  private Boolean currentProduct;
  private Boolean priceOnlyAvailableInCart;
  private Double price;
  private String mainImage;

  @JsonBackReference("amazonProductDimensions")
  private Set<AmazonProductDimension> amazonProductDimensions;

}
