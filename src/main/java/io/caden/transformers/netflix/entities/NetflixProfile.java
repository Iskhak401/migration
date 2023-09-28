package io.caden.transformers.netflix.entities;

import io.caden.transformers.shared.entities.RDFAbstractEntity;

public class NetflixProfile extends RDFAbstractEntity {
  private String cadenAlias;

  private String name;

  private String guid;

  public String getCadenAlias() {
    return this.cadenAlias;
  }

  public void setCadenAlias(final String cadenAlias) {
    this.cadenAlias = cadenAlias;
  }

  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getGuid() {
    return this.guid;
  }

  public void setGuid(String guid) {
    this.guid = guid;
  }
}
