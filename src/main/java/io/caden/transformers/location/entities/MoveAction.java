package io.caden.transformers.location.entities;

import java.util.Date;
import java.util.UUID;

import io.caden.transformers.shared.entities.RDFAbstractEntity;

public class MoveAction extends RDFAbstractEntity {
  private UUID cadenAlias;
  private String fromLocation;
  private Double latitude;
  private Double longitude;
  private Date startTime;
  private Date endTime;

  public UUID getCadenAlias() {
    return this.cadenAlias;
  }

  public void setCadenAlias(final UUID cadenAlias) {
    this.cadenAlias = cadenAlias;
  }

  public String getFromLocation() {
    return this.fromLocation;
  }

  public void setFromLocation(final String fromLocation) {
    this.fromLocation = fromLocation;
  }

  public Double getLatitude() {
    return this.latitude;
  }

  public void setLatitude(final Double latitude) {
    this.latitude = latitude;
  }

  public Double getLongitude() {
    return this.longitude;
  }

  public void setLongitude(final Double longitude) {
    this.longitude = longitude;
  }

  public Date getStartTime() {
    return this.startTime;
  }

  public void setStartTime(final Date startTime) {
    this.startTime = startTime;
  }

  public Date getEndTime() {
    return this.endTime;
  }

  public void setEndTime(final Date endTime) {
    this.endTime = endTime;
  }
}
