package io.caden.transformers.ahk.entities;

import java.util.Date;
import java.util.UUID;

import io.caden.transformers.shared.entities.RDFAbstractEntity;

public class AHKActivitySummary extends RDFAbstractEntity {
  private UUID cadenAlias;
  private Double activeEnergyBurned;
  private Double activeEnergyBurnedGoal;
  private Long moveTime;
  private Long moveTimeGoal;
  private Long exerciseTime;
  private Long exerciseTimeGoal;
  private Double standHours;
  private Double standHoursGoal;
  private Integer stepCount;
  private Date startDate;
  private Date endDate;

  public UUID getCadenAlias() {
    return this.cadenAlias;
  }

  public void setCadenAlias(final UUID cadenAlias) {
    this.cadenAlias = cadenAlias;
  }

  public Double getActiveEnergyBurned() {
    return activeEnergyBurned;
  }

  public void setActiveEnergyBurned(final Double activeEnergyBurned) {
    this.activeEnergyBurned = activeEnergyBurned;
  }

  public Double getActiveEnergyBurnedGoal() {
    return activeEnergyBurnedGoal;
  }

  public void setActiveEnergyBurnedGoal(final Double activeEnergyBurnedGoal) {
    this.activeEnergyBurnedGoal = activeEnergyBurnedGoal;
  }

  public Long getMoveTime() {
    return moveTime;
  }

  public void setMoveTime(final Long moveTime) {
    this.moveTime = moveTime;
  }

  public Long getMoveTimeGoal() {
    return moveTimeGoal;
  }

  public void setMoveTimeGoal(final Long moveTimeGoal) {
    this.moveTimeGoal = moveTimeGoal;
  }

  public Long getExerciseTime() {
    return exerciseTime;
  }

  public void setExerciseTime(final Long exerciseTime) {
    this.exerciseTime = exerciseTime;
  }

  public Long getExerciseTimeGoal() {
    return exerciseTimeGoal;
  }

  public void setExerciseTimeGoal(final Long exerciseTimeGoal) {
    this.exerciseTimeGoal = exerciseTimeGoal;
  }

  public Double getStandHours() {
    return standHours;
  }

  public void setStandHours(final Double standHours) {
    this.standHours = standHours;
  }

  public Double getStandHoursGoal() {
    return standHoursGoal;
  }

  public void setStandHoursGoal(final Double standHoursGoal) {
    this.standHoursGoal = standHoursGoal;
  }

  public Integer getStepCount() {
    return this.stepCount;
  }

  public void setStepCount(final Integer stepCount) {
    this.stepCount = stepCount;
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
}
