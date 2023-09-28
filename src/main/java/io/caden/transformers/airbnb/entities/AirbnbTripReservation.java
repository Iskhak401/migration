package io.caden.transformers.airbnb.entities;

import java.util.Date;

import io.caden.transformers.shared.entities.RDFAbstractEntity;

public class AirbnbTripReservation extends RDFAbstractEntity {
  private Date checkinTime;

  private Date checkoutTime;

  private Integer numAdults;

  private String priceCurrency;

  private Double totalPrice;

  private String reservationFor;

  private String reservationId;

  private AirbnbTripReservationStatus reservationStatus;

  private String paymentIdLastFour;

  private Date transformationDate;

  private Double tax;

  private Double serviceFee;

  private String cadenAlias;

  public Date getCheckinTime() {
    return this.checkinTime;
  }

  public void setCheckinTime(final Date checkinTime) {
    this.checkinTime = checkinTime;
  }

  public Date getCheckoutTime() {
    return this.checkoutTime;
  }

  public void setCheckoutTime(final Date checkoutTime) {
    this.checkoutTime = checkoutTime;
  }

  public Integer getNumAdults() {
    return this.numAdults;
  }

  public void setNumAdults(final Integer numAdults) {
    this.numAdults = numAdults;
  }

  public String getPriceCurrency() {
    return this.priceCurrency;
  }

  public void setPriceCurrency(final String priceCurrency) {
    this.priceCurrency = priceCurrency;
  }

  public Double getTotalPrice() {
    return this.totalPrice;
  }

  public void setTotalPrice(final Double totalPrice) {
    this.totalPrice = totalPrice;
  }

  public String getReservationFor() {
    return this.reservationFor;
  }

  public void setReservationFor(final String reservationFor) {
    this.reservationFor = reservationFor;
  }

  public String getReservationId() {
    return this.reservationId;
  }

  public void setReservationId(final String reservationId) {
    this.reservationId = reservationId;
  }

  public AirbnbTripReservationStatus getReservationStatus() {
    return this.reservationStatus;
  }

  public void setReservationStatus(final AirbnbTripReservationStatus reservationStatus) {
    this.reservationStatus = reservationStatus;
  }

  public Date getTransformationDate() {
    return this.transformationDate;
  }

  public void setTransformationDate(final Date transformationDate) {
    this.transformationDate = transformationDate;
  }

  public String getPaymentIdLastFour() {
    return this.paymentIdLastFour;
  }

  public void setPaymentIdLastFour(final String paymentIdLastFour) {
    this.paymentIdLastFour = paymentIdLastFour;
  }

  public Double getTax() {
    return this.tax;
  }

  public void setTax(final Double tax) {
    this.tax = tax;
  }

  public Double getServiceFee() {
    return this.serviceFee;
  }

  public void setServiceFee(final Double serviceFee) {
    this.serviceFee = serviceFee;
  }

  public String getCadenAlias() {
    return this.cadenAlias;
  }

  public void setCadenAlias(final String cadenAlias) {
    this.cadenAlias = cadenAlias;
  }
}
