package io.caden.transformers.amazon.dtos.rainforest;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RainforestProductBuyboxWinnerDto {
  @JsonProperty("is_prime")
  private Boolean prime;
  @JsonProperty("price_only_available_in_cart")
  private Boolean priceOnlyAvailableInCart;
  @JsonProperty("is_amazon_fresh")
  private Boolean amazonFresh;
  @JsonProperty("offer_id")
  private String offerId;
  @JsonProperty("new_offers_count")
  private Integer newOffersCount;
  @JsonProperty("new_offers_from")
  private RainforestProductPriceDto newOffersFrom;
  @JsonProperty("used_offers_count")
  private Integer usedOffersCount;
  @JsonProperty("used_offers_from")
  private RainforestProductPriceDto usedOffersFrom;
  @JsonProperty("mixed_offers_count")
  private Integer mixedOffersCount;
  @JsonProperty("mixed_offers_from")
  private RainforestProductPriceDto mixedOffersFrom;
  @JsonProperty("availability")
  private RainforestProductAvailabilityDto rainforestProductAvailability;
  @JsonProperty("price")
  private RainforestProductPriceDto rainforestProductPrice;
  @JsonProperty("rrp")
  private RainforestProductPriceDto recommendedRetailPrice;
  @JsonProperty("unit_price")
  private RainforestProductPriceDto unitPrice;
  @JsonProperty("shipping")
  private RainforestProductPriceDto shippingPrice;
  @JsonProperty("free_shipping_minimum_spend")
  private RainforestProductPriceDto freeShippingMinimumSpend;
  @JsonProperty("save")
  private RainforestProductPriceDto savePrice;
  @JsonProperty("unqualified_buy_box")
  private Boolean unqualifiedBuyBox;

  public Boolean isPrime() {
    return this.prime;
  }

  public void setPrime(final Boolean prime) {
    this.prime = prime;
  }

  public Boolean isPriceOnlyAvailableInCart() {
    return this.priceOnlyAvailableInCart;
  }

  public void setPriceOnlyAvailableInCart(final Boolean priceOnlyAvailableInCart) {
    this.priceOnlyAvailableInCart = priceOnlyAvailableInCart;
  }

  public Boolean isAmazonFresh() {
    return this.amazonFresh;
  }

  public void setAmazonFresh(final Boolean amazonFresh) {
    this.amazonFresh = amazonFresh;
  }

  public String getOfferId() {
    return this.offerId;
  }

  public void setOfferId(final String offerId) {
    this.offerId = offerId;
  }

  public Integer getNewOffersCount() {
    return this.newOffersCount;
  }

  public void setNewOffersCount(final Integer newOffersCount) {
    this.newOffersCount = newOffersCount;
  }

  public RainforestProductPriceDto getNewOffersFrom() {
    return this.newOffersFrom;
  }

  public void setNewOffersFrom(final RainforestProductPriceDto newOffersFrom) {
    this.newOffersFrom = newOffersFrom;
  }

  public Integer getUsedOffersCount() {
    return this.usedOffersCount;
  }

  public void setUsedOffersCount(final Integer usedOffersCount) {
    this.usedOffersCount = usedOffersCount;
  }

  public RainforestProductPriceDto getUsedOffersFrom() {
    return this.usedOffersFrom;
  }

  public void setUsedOffersFrom(final RainforestProductPriceDto usedOffersFrom) {
    this.usedOffersFrom = usedOffersFrom;
  }

  public Integer getMixedOffersCount() {
    return this.mixedOffersCount;
  }

  public void setMixedOffersCount(final Integer mixedOffersCount) {
    this.mixedOffersCount = mixedOffersCount;
  }

  public RainforestProductPriceDto getMixedOffersFrom() {
    return this.mixedOffersFrom;
  }

  public void setMixedOffersFrom(final RainforestProductPriceDto mixedOffersFrom) {
    this.mixedOffersFrom = mixedOffersFrom;
  }

  public RainforestProductAvailabilityDto getRainforestProductAvailability() {
    return this.rainforestProductAvailability;
  }

  public void setRainforestProductAvailability(final RainforestProductAvailabilityDto rainforestProductAvailability) {
    this.rainforestProductAvailability = rainforestProductAvailability;
  }

  public RainforestProductPriceDto getRainforestProductPrice() {
    return this.rainforestProductPrice;
  }

  public void setRainforestProductPrice(final RainforestProductPriceDto rainforestProductPrice) {
    this.rainforestProductPrice = rainforestProductPrice;
  }

  public RainforestProductPriceDto getRecommendedRetailPrice() {
    return this.recommendedRetailPrice;
  }

  public void setRecommendedRetailPrice(final RainforestProductPriceDto recommendedRetailPrice) {
    this.recommendedRetailPrice = recommendedRetailPrice;
  }

  public RainforestProductPriceDto getUnitPrice() {
    return this.unitPrice;
  }

  public void setUnitPrice(final RainforestProductPriceDto unitPrice) {
    this.unitPrice = unitPrice;
  }

  public RainforestProductPriceDto getShippingPrice() {
    return this.shippingPrice;
  }

  public void setShippingPrice(final RainforestProductPriceDto shippingPrice) {
    this.shippingPrice = shippingPrice;
  }

  public RainforestProductPriceDto getFreeShippingMinimumSpend() {
    return this.freeShippingMinimumSpend;
  }

  public void setFreeShippingMinimumSpend(final RainforestProductPriceDto freeShippingMinimumSpend) {
    this.freeShippingMinimumSpend = freeShippingMinimumSpend;
  }

  public RainforestProductPriceDto getSavePrice() {
    return this.savePrice;
  }

  public void setSavePrice(final RainforestProductPriceDto savePrice) {
    this.savePrice = savePrice;
  }

  public Boolean isUnqualifiedBuyBox() {
    return this.unqualifiedBuyBox;
  }

  public void setUnqualifiedBuyBox(final Boolean unqualifiedBuyBox) {
    this.unqualifiedBuyBox = unqualifiedBuyBox;
  }
}
