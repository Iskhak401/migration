package io.caden.transformers.amazon.dtos.rainforest;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RainforestProductMoreBuyingChoiceDto {
  @JsonProperty("price")
  private RainforestProductPriceDto rainforestProductPrice;
  @JsonProperty("seller_name")
  private String sellerName;
  @JsonProperty("seller_link")
  private String sellerLink;
  private Integer position;
  @JsonProperty("free_shipping")
  private Boolean freeShipping;

  public RainforestProductPriceDto getRainforestProductPrice() {
    return this.rainforestProductPrice;
  }

  public void setRainforestProductPrice(final RainforestProductPriceDto rainforestProductPrice) {
    this.rainforestProductPrice = rainforestProductPrice;
  }

  public String getSellerName() {
    return this.sellerName;
  }

  public void setSellerName(final String sellerName) {
    this.sellerName = sellerName;
  }

  public String getSellerLink() {
    return this.sellerLink;
  }

  public void setSellerLink(final String sellerLink) {
    this.sellerLink = sellerLink;
  }

  public Integer getPosition() {
    return this.position;
  }

  public void setPosition(final Integer position) {
    this.position = position;
  }

  public Boolean isFreeShipping() {
    return this.freeShipping;
  }

  public void setFreeShipping(final Boolean freeShipping) {
    this.freeShipping = freeShipping;
  }
}
