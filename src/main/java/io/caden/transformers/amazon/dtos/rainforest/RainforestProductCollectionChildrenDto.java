package io.caden.transformers.amazon.dtos.rainforest;

public class RainforestProductCollectionChildrenDto {
  private String title;
  private String asin;
  private String link;

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
}
