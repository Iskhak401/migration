package io.caden.transformers.amazon.dtos.rainforest;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;

public class RainforestRequestProductResultDto {
  @JsonProperty("product")
  private RainforestProductDto rainforestProduct;
  @JsonIgnore
  private JsonNode jsonData;

  public RainforestProductDto getRainforestProduct() {
    return this.rainforestProduct;
  }

  public void setRainforestProduct(final RainforestProductDto rainforestProduct) {
    this.rainforestProduct = rainforestProduct;
  }

  public JsonNode getJsonData() {
    return jsonData;
  }

  public void setJsonData(final JsonNode jsonData) {
    this.jsonData = jsonData;
  }
}
