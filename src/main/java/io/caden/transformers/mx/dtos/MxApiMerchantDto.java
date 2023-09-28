package io.caden.transformers.mx.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class MxApiMerchantDto {
  private String guid;
  private String logoUrl;
  private String name;
  private String websiteUrl;
  private String createdAt;
  private String updatedAt;

  public String getGuid() {
    return this.guid;
  }
  public void setGuid(String guid) {
    this.guid = guid;
  }

  public String getLogoUrl() {
    return this.logoUrl;
  }
  public void setLogoUrl(String logoUrl) {
    this.logoUrl = logoUrl;
  }

  public String getName() {
    return this.name;
  }
  public void setName(String name) {
    this.name = name;
  }

  public String getWebsiteUrl() {
    return this.websiteUrl;
  }
  public void setWebsiteUrl(String websiteUrl) {
    this.websiteUrl = websiteUrl;
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
