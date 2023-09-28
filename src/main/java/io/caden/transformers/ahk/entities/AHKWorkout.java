package io.caden.transformers.ahk.entities;

import java.util.Date;
import java.util.UUID;

import io.caden.transformers.shared.entities.RDFAbstractEntity;

public class AHKWorkout extends RDFAbstractEntity {
  private UUID cadenAlias;
  private Double distance;
  private String exerciseType;
  private Long duration;
  private Date startDate;
  private Date endDate;
  private Double totalEnergyBurned;
  private Double totalFlightsClimbed;
  private Double totalSwimmingStrokeCount;

  public UUID getCadenAlias() {
    return this.cadenAlias;
  }

  public void setCadenAlias(final UUID cadenAlias) {
    this.cadenAlias = cadenAlias;
  }

  public Double getDistance() {
    return distance;
  }

  public void setDistance(final Double distance) {
    this.distance = distance;
  }

  public String getExerciseType() {
    return exerciseType;
  }

  public void setExerciseType(final String exerciseType) {
    this.exerciseType = exerciseType;
  }

  public Long getDuration() {
    return duration;
  }

  public void setDuration(final Long duration) {
    this.duration = duration;
  }

  public Date getStartDate() {
    return this.startDate;
  }

  public void setStartDate(final Date startDate) {
    this.startDate = startDate;
  }

  public Date getEndDate() {
    return endDate;
  }

  public void setEndDate(final Date endTime) {
    this.endDate = endTime;
  }

  public Double getTotalEnergyBurned() {
    return totalEnergyBurned;
  }

  public void setTotalEnergyBurned(final Double totalEnergyBurned) {
    this.totalEnergyBurned = totalEnergyBurned;
  }

  public Double getTotalFlightsClimbed() {
    return totalFlightsClimbed;
  }

  public void setTotalFlightsClimbed(final Double totalFlightsClimbed) {
    this.totalFlightsClimbed = totalFlightsClimbed;
  }

  public Double getTotalSwimmingStrokeCount() {
    return totalSwimmingStrokeCount;
  }

  public void setTotalSwimmingStrokeCount(final Double totalSwimmingStrokeCount) {
    this.totalSwimmingStrokeCount = totalSwimmingStrokeCount;
  }
}
