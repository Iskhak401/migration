package io.caden.transformers.amazon.dtos.rainforest;

public class RainforestProductPriceDto {
  private String symbol;
  private Double value;
  private String currency;
  private String raw;

  public String getSymbol() {
    return this.symbol;
  }

  public void setSymbol(final String symbol) {
    this.symbol = symbol;
  }

  public Double getValue() {
    return this.value;
  }

  public void setValue(final Double value) {
    this.value = value;
  }

  public String getCurrency() {
    return this.currency;
  }

  public void setCurrency(final String currency) {
    this.currency = currency;
  }

  public String getRaw() {
    return this.raw;
  }

  public void setRaw(final String raw) {
    this.raw = raw;
  }
}
