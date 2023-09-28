package io.caden.transformers.amazon.entities;

import io.caden.transformers.shared.entities.RDFAbstractEntity;

public class AmazonSeller extends RDFAbstractEntity {
  private String name;
  private String identifier;
  private String sameAs;

  public String getName() {
    return this.name;
  }

  public void setName(final String name) {
    this.name = name;
  }

  public String getIdentifier() {
    return this.identifier;
  }

  public void setIdentifier(final String identifier) {
    this.identifier = identifier;
  }

  public String getSameAs() {
    return this.sameAs;
  }

  public void setSameAs(final String sameAs) {
    this.sameAs = sameAs;
  }
}
