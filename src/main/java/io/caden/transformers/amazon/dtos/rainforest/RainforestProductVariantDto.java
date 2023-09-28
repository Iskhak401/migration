package io.caden.transformers.amazon.dtos.rainforest;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RainforestProductVariantDto {
  private String title;
  private String asin;
  private String link;
  @JsonProperty("dimensions")
  private List<RainforestProductDimensionDto> rainforestProductDimensions;
  private String format;
  @JsonProperty("is_current_product")
  private Boolean currentProduct;
  @JsonProperty("price_only_available_in_cart")
  private Boolean priceOnlyAvailableInCart;
  @JsonProperty("price")
  private RainforestProductPriceDto rainforestProductPrice;
  @JsonProperty("main_image")
  private String mainImage;

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

  public String getLink() {
    return this.link;
  }

  public void setLink(final String link) {
    this.link = link;
  }

  public List<RainforestProductDimensionDto> getRainforestProductDimensions() {
    return this.rainforestProductDimensions;
  }

  public void setRainforestProductDimensions(final List<RainforestProductDimensionDto> rainforestProductDimensions) {
    this.rainforestProductDimensions = rainforestProductDimensions;
  }

  public String getFormat() {
    return this.format;
  }

  public void setFormat(final String format) {
    this.format = format;
  }

  public Boolean isCurrentProduct() {
    return this.currentProduct;
  }

  public void setCurrentProduct(final Boolean currentProduct) {
    this.currentProduct = currentProduct;
  }

  public Boolean isPriceOnlyAvailableInCart() {
    return this.priceOnlyAvailableInCart;
  }

  public void setPriceOnlyAvailableInCart(final Boolean priceOnlyAvailableInCart) {
    this.priceOnlyAvailableInCart = priceOnlyAvailableInCart;
  }

  public RainforestProductPriceDto getRainforestProductPrice() {
    return this.rainforestProductPrice;
  }

  public void setRainforestProductPrice(final RainforestProductPriceDto rainforestProductPrice) {
    this.rainforestProductPrice = rainforestProductPrice;
  }

  public String getMainImage() {
    return this.mainImage;
  }

  public void setMainImage(final String mainImage) {
    this.mainImage = mainImage;
  }
}
