package io.caden.transformers.mx.entities;

import io.caden.transformers.shared.entities.RDFAbstractEntity;

public class MxMerchant extends RDFAbstractEntity {
  private String name;
  private String identifier;
  private String url;
  private String logo;

  public String getName() {
    return this.name;
  }
  public void setName(String name) {
    this.name = name;
  }
  public String getIdentifier() {
    return this.identifier;
  }
  public void setIdentifier(String identifier) {
    this.identifier = identifier;
  }
  public String getUrl() {
    return this.url;
  }
  public void setUrl(String url) {
    this.url = url;
  }
  public String getLogo() {
    return this.logo;
  }
  public void setLogo(String logo) {
    this.logo = logo;
  }
}
