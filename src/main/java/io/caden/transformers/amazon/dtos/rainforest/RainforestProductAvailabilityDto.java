package io.caden.transformers.amazon.dtos.rainforest;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RainforestProductAvailabilityDto {
  private String type;
  private String raw;
  @JsonProperty("dispatch_days")
  private Integer dispatchDays;
  @JsonProperty("stock_level")
  private Integer stockLevel;

  public String getType() {
    return this.type;
  }

  public void setType(final String type) {
    this.type = type;
  }

  public String getRaw() {
    return this.raw;
  }

  public void setRaw(final String raw) {
    this.raw = raw;
  }

  public Integer getDispatchDays() {
    return this.dispatchDays;
  }

  public void setDispatchDays(final Integer dispatchDays) {
    this.dispatchDays = dispatchDays;
  }

  public Integer getStockLevel() {
    return this.stockLevel;
  }

  public void setStockLevel(final Integer stockLevel) {
    this.stockLevel = stockLevel;
  }
}
