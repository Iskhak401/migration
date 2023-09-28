package io.caden.transformers.amazon.dtos.rainforest;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RainforestProductProtectionPlanDto {
  private String title;
  private String asin;
  @JsonProperty("price")
  private RainforestProductPriceDto rainforestProductPrice;

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

  public RainforestProductPriceDto getRainforestProductPrice() {
    return this.rainforestProductPrice;
  }

  public void setRainforestProductPrice(final RainforestProductPriceDto rainforestProductPrice) {
    this.rainforestProductPrice = rainforestProductPrice;
  }
}
