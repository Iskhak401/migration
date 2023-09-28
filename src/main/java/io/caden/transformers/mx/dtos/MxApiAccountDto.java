package io.caden.transformers.mx.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class MxApiAccountDto {
  private String guid;
  private String id;
  private String accountNumber;
  private String name;
  private String type;

  public String getGuid() {
    return this.guid;
  }
  public void setGuid(String guid) {
    this.guid = guid;
  }

  public String getId() {
    return this.id;
  }
  public void setId(String id) {
    this.id = id;
  }

  public String getAccountNumber() {
    return this.accountNumber;
  }
  public void setAccountNumber(String accountNumber) {
    this.accountNumber = accountNumber;
  }

  public String getName() {
    return this.name;
  }
  public void setName(String name) {
    this.name = name;
  }

  public String getType() {
    return this.type;
  }
  public void setType(String type) {
    this.type = type;
  }
}
