package io.caden.transformers.amazon.dtos.rainforest;

public class RainforestProductBestsellersRankDto {
  private Integer rank;
  private String category;
  private String link;

  public Integer getRank() {
    return this.rank;
  }

  public void setRank(final Integer rank) {
    this.rank = rank;
  }

  public String getCategory() {
    return this.category;
  }

  public void setCategory(final String category) {
    this.category = category;
  }

  public String getLink() {
    return this.link;
  }

  public void setLink(final String link) {
    this.link = link;
  }
}
