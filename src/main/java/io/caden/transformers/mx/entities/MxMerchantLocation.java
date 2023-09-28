package io.caden.transformers.mx.entities;

import io.caden.transformers.shared.entities.RDFAbstractEntity;

public class MxMerchantLocation extends RDFAbstractEntity {
  private String identifier;
  private Double latitude;
  private Double longitude;
  private String telephone;
  private String streetAddress;
  private String addressLocality;
  private String addressRegion;
  private String addressCountry;
  private String postalCode;

  public String getIdentifier() {
    return this.identifier;
  }
  public void setIdentifier(String identifier) {
    this.identifier = identifier;
  }

  public Double getLatitude() {
    return this.latitude;
  }
  public void setLatitude(Double latitude) {
    this.latitude = latitude;
  }

  public Double getLongitude() {
    return this.longitude;
  }
  public void setLongitude(Double longitude) {
    this.longitude = longitude;
  }

  public String getTelephone() {
    return this.telephone;
  }
  public void setTelephone(String telephone) {
    this.telephone = telephone;
  }

  public String getStreetAddress() {
    return this.streetAddress;
  }
  public void setStreetAddress(String streetAddress) {
    this.streetAddress = streetAddress;
  }

  public String getAddressLocality() {
    return this.addressLocality;
  }
  public void setAddressLocality(String addressLocality) {
    this.addressLocality = addressLocality;
  }

  public String getAddressRegion() {
    return this.addressRegion;
  }
  public void setAddressRegion(String addressRegion) {
    this.addressRegion = addressRegion;
  }

  public String getAddressCountry() {
    return this.addressCountry;
  }
  public void setAddressCountry(String addressCountry) {
    this.addressCountry = addressCountry;
  }

  public String getPostalCode() {
    return this.postalCode;
  }
  public void setPostalCode(String postalCode) {
    this.postalCode = postalCode;
  }
}
