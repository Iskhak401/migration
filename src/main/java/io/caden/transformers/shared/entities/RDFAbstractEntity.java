package io.caden.transformers.shared.entities;

import java.io.Serializable;
import java.util.Date;

public abstract class RDFAbstractEntity implements Serializable {
  private String uuid;
  private Date createdAt;
  private Date updatedAt;

  public String getUuid() {
    return uuid;
  }

  public void setUuid(final String uuid) {
    this.uuid = uuid;
  }

  public Date getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(final Date createdAt) {
    this.createdAt = createdAt;
  }

  public Date getUpdatedAt() {
    return updatedAt;
  }

  public void setUpdatedAt(final Date updatedAt) {
    this.updatedAt = updatedAt;
  }
}
