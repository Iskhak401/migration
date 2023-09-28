package io.caden.transformers.amazon.entities;

import io.caden.transformers.shared.entities.RDFAbstractEntity;

public class AmazonProductProtectionPlan extends RDFAbstractEntity {

  private String title;
  private String asin;
  private Double price;

  public String getTitle() {
    return this.title;
  }

  public void setTitle(final String title) {
    this.title = title;
  }

  public String getAsin() {
    return this.asin;
  }

  public void setAsin(final String asin) {
    this.asin = asin;
  }

  public Double getPrice() {
    return this.price;
  }

  public void setPrice(final Double price) {
    this.price = price;
  }

}
