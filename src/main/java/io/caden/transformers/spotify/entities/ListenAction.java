package io.caden.transformers.spotify.entities;

import java.util.Date;

import io.caden.transformers.shared.entities.RDFAbstractEntity;

public class ListenAction extends RDFAbstractEntity {
  private Date startTime;

  private String cadenAlias;

  public Date getStartTime() {
    return this.startTime;
  }

  public void setStartTime(final Date startTime) {
    this.startTime = startTime;
  }

  public String getCadenAlias() {
    return this.cadenAlias;
  }

  public void setCadenAlias(final String cadenAlias) {
    this.cadenAlias = cadenAlias;
  }
}
