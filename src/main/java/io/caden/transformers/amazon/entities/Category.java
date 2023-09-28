package io.caden.transformers.amazon.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;

import io.caden.transformers.shared.entities.RDFAbstractEntity;

import java.util.Set;

public class Category extends RDFAbstractEntity {

  private String description;

  private Boolean deleted;

  private String externalId;

  @JsonBackReference("parentCategory")
  private Category parentCategory;

  @JsonBackReference("categoryChilds")
  private Set<Category> categoryChilds;

  @JsonBackReference("amazonProductCategories")
  private Set<AmazonProductCategory> amazonProductCategories;

  public Category() {
    super();
  }

  public Category(
    final String description,
    final Boolean deleted,
    final String externalId,
    final Category parentCategory,
    final Set<Category> categoryChilds
  ) {
    super();
    this.description = description;
    this.deleted = deleted;
    this.externalId = externalId;
    this.parentCategory = parentCategory;
    this.categoryChilds = categoryChilds;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public Boolean getDeleted() {
    return deleted;
  }

  public void setDeleted(Boolean deleted) {
    this.deleted = deleted;
  }

  public String getExternalId() {
    return this.externalId;
  }

  public void setExternalId(final String externalId) {
    this.externalId = externalId;
  }

  public Category getParentCategory() {
    return this.parentCategory;
  }

  public void setParentCategory(final Category parentCategory) {
    this.parentCategory = parentCategory;
  }

  public Set<Category> getCategoryChilds() {
    return this.categoryChilds;
  }

  public void setCategoryChilds(final Set<Category> categoryChilds) {
    this.categoryChilds = categoryChilds;
  }

  public Set<AmazonProductCategory> getAmazonProductCategories() {
    return this.amazonProductCategories;
  }

  public void setAmazonProductCategories(final Set<AmazonProductCategory> amazonProductCategories) {
    this.amazonProductCategories = amazonProductCategories;
  }
}
