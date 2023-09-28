package io.caden.transformers.amazon.dtos.rainforest;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RainforestProductEnergyEfficiencyDto {
  private String rating;
  @JsonProperty("rating_image")
  private String ratingImage;
  @JsonProperty("product_fiche_image")
  private String productFicheImage;

  public String getRating() {
    return this.rating;
  }

  public void setRating(final String rating) {
    this.rating = rating;
  }

  public String getRatingImage() {
    return this.ratingImage;
  }

  public void setRatingImage(final String ratingImage) {
    this.ratingImage = ratingImage;
  }

  public String getProductFicheImage() {
    return this.productFicheImage;
  }

  public void setProductFicheImage(final String productFicheImage) {
    this.productFicheImage = productFicheImage;
  }
}
