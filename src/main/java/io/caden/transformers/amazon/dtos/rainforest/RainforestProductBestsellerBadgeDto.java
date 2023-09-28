package io.caden.transformers.amazon.dtos.rainforest;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RainforestProductBestsellerBadgeDto {
  private String category;
  @JsonProperty("badge_text")
  private String badgeText;
  private String link;

  public String getCategory() {
    return this.category;
  }

  public void setCategory(final String category) {
    this.category = category;
  }

  public String getBadgeText() {
    return this.badgeText;
  }

  public void setBadgeText(final String badgeText) {
    this.badgeText = badgeText;
  }

  public String getLink() {
    return this.link;
  }

  public void setLink(final String link) {
    this.link = link;
  }
}
