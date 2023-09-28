package io.caden.transformers.amazon.dtos.rainforest;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RainforestProductServiceDto {
  private String title;
  @JsonProperty("price")
  private RainforestProductPriceDto rainforestProductPrice;
  @JsonProperty("whats_included")
  private List<String> whatsIncluded;

  public String getTitle() {
    return this.title;
  }

  public void setTitle(final String title) {
    this.title = title;
  }

  public RainforestProductPriceDto getRainforestProductPrice() {
    return this.rainforestProductPrice;
  }

  public void setRainforestProductPrice(final RainforestProductPriceDto rainforestProductPrice) {
    this.rainforestProductPrice = rainforestProductPrice;
  }

  public List<String> getWhatsIncluded() {
    return this.whatsIncluded;
  }

  public void setWhatsIncluded(final List<String> whatsIncluded) {
    this.whatsIncluded = whatsIncluded;
  }
}
