package io.caden.transformers.mx.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class MxApiMerchantLocationDto {
  private String city;
  private String country;
  private String guid;
  private Double latitude;
  private Double longitude;
  private String merchantGuid;
  private String phoneNumber;
  private String postalCode;
  private String state;
  private String streetAddress;
  private String createdAt;
  private String updatedAt;

  public String getCity() {
    return this.city;
  }
  public void setCity(String city) {
    this.city = city;
  }

  public String getCountry() {
    return this.country;
  }
  public void setCountry(String country) {
    this.country = country;
  }

  public String getGuid() {
    return this.guid;
  }
  public void setGuid(String guid) {
    this.guid = guid;
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

  public String getMerchantGuid() {
    return this.merchantGuid;
  }
  public void setMerchantGuid(String merchantGuid) {
    this.merchantGuid = merchantGuid;
  }

  public String getPhoneNumber() {
    return this.phoneNumber;
  }
  public void setPhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
  }

  public String getPostalCode() {
    return this.postalCode;
  }
  public void setPostalCode(String postalCode) {
    this.postalCode = postalCode;
  }

  public String getState() {
    return this.state;
  }
  public void setState(String state) {
    this.state = state;
  }

  public String getStreetAddress() {
    return this.streetAddress;
  }
  public void setStreetAddress(String streetAddress) {
    this.streetAddress = streetAddress;
  }

  public String getCreatedAt() {
    return this.createdAt;
  }
  public void setCreatedAt(String createdAt) {
    this.createdAt = createdAt;
  }

  public String getUpdatedAt() {
    return this.updatedAt;
  }
  public void setUpdatedAt(String updatedAt) {
    this.updatedAt = updatedAt;
  }
}
