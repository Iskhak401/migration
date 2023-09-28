package io.caden.transformers.amazon.dtos.rainforest;

public class RainforestProductRatingBreakdownDto {
  private String name;
  private Double percentage;
  private Integer count;

  public String getName() {
    return this.name;
  }

  public void setName(final String name) {
    this.name = name;
  }

  public Double getPercentage() {
    return this.percentage;
  }

  public void setPercentage(final Double percentage) {
    this.percentage = percentage;
  }

  public Integer getCount() {
    return this.count;
  }

  public void setCount(final Integer count) {
    this.count = count;
  }
}
