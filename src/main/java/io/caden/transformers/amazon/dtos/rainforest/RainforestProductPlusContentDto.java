package io.caden.transformers.amazon.dtos.rainforest;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RainforestProductPlusContentDto {
  @JsonProperty("has_a_plus_content")
  private Boolean plusContent;
  @JsonProperty("has_brand_story")
  private Boolean brandStory;
  @JsonProperty("third_party")
  private Boolean thirdParty;
  @JsonProperty("company_logo")
  private String companyLogo;
  @JsonProperty("company_description_text")
  private String companyDescriptionText;

  public Boolean isPlusContent() {
    return this.plusContent;
  }

  public void setPlusContent(final Boolean plusContent) {
    this.plusContent = plusContent;
  }

  public Boolean isBrandStory() {
    return this.brandStory;
  }

  public void setBrandStory(final Boolean brandStory) {
    this.brandStory = brandStory;
  }

  public Boolean isThirdParty() {
    return this.thirdParty;
  }

  public void setThirdParty(final Boolean thirdParty) {
    this.thirdParty = thirdParty;
  }

  public String getCompanyLogo() {
    return this.companyLogo;
  }

  public void setCompanyLogo(final String companyLogo) {
    this.companyLogo = companyLogo;
  }

  public String getCompanyDescriptionText() {
    return this.companyDescriptionText;
  }

  public void setCompanyDescriptionText(final String companyDescriptionText) {
    this.companyDescriptionText = companyDescriptionText;
  }
}
