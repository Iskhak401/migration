package io.caden.transformers.ahk.entities;

import java.util.Date;

import io.caden.transformers.shared.entities.RDFAbstractEntity;

public class AHKStep extends RDFAbstractEntity {
  private Date startDate;
  private Date endDate;
  private Integer count;

  public Date getStartDate() {
    return this.startDate;
  }

  public void setStartDate(final Date startDate) {
    this.startDate = startDate;
  }

  public Date getEndDate() {
    return this.endDate;
  }

  public void setEndDate(final Date endDate) {
    this.endDate = endDate;
  }

  public Integer getCount() {
    return this.count;
  }

  public void setCount(final Integer count) {
    this.count = count;
  }
}
