package io.caden.transformers.amazon.entities;

import io.caden.transformers.shared.entities.RDFAbstractEntity;

public class AmazonOrderItem extends RDFAbstractEntity {

  private AmazonProduct amazonProduct;

  private String name;

  private String link;

  private Integer quantity;

  private AmazonParcelDelivery parcelDelivery;

  private AmazonSeller seller;

  private Double price;

  private String priceCurrency;

  private Boolean returned;

  private String cadenAlias;

  public AmazonProduct getAmazonProduct() {
    return this.amazonProduct;
  }

  public void setAmazonProduct(final AmazonProduct amazonProduct) {
    this.amazonProduct = amazonProduct;
  }

  public String getName() {
    return this.name;
  }

  public void setName(final String name) {
    this.name = name;
  }

  public String getLink() {
    return this.link;
  }

  public void setLink(final String link) {
    this.link = link;
  }

  public Integer getQuantity() {
    return this.quantity;
  }

  public void setQuantity(final Integer quantity) {
    this.quantity = quantity;
  }

  public Double getPrice() {
    return this.price;
  }

  public void setPrice(final Double price) {
    this.price = price;
  }

  public Boolean isReturned() {
    return this.returned;
  }

  public void setReturned(final Boolean returned) {
    this.returned = returned;
  }

  public String getPriceCurrency() {
    return priceCurrency;
  }

  public void setPriceCurrency(final String priceCurrency) {
    this.priceCurrency = priceCurrency;
  }

  public AmazonParcelDelivery getParcelDelivery() {
    return parcelDelivery;
  }

  public void setParcelDelivery(final AmazonParcelDelivery parcelDelivery) {
    this.parcelDelivery = parcelDelivery;
  }

  public String getCadenAlias() {
    return cadenAlias;
  }

  public void setCadenAlias(String cadenAlias) {
    this.cadenAlias = cadenAlias;
  }

  public AmazonSeller getSeller() {
    return seller;
  }

  public void setSeller(final AmazonSeller seller) {
    this.seller = seller;
  }
}
