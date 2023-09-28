package io.caden.transformers.amazon.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;

import io.caden.transformers.shared.entities.RDFAbstractEntity;
import lombok.Data;

@Data
public class AmazonProductCategory extends RDFAbstractEntity {

  @JsonBackReference("category")
  private Category category;
  private String name;
  private String link;
  private String externalId;

}
