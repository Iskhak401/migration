package io.caden.transformers.amazon.dtos.rainforest;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RainforestProductCategoryDto {
  private String name;
  private String link;
  @JsonProperty("category_id")
  private String categoryId;

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

  public String getCategoryId() {
    return this.categoryId;
  }

  public void setCategoryId(final String categoryId) {
    this.categoryId = categoryId;
  }
}
