package io.caden.transformers.uber.entities;

import java.util.Date;

import io.caden.transformers.shared.entities.RDFAbstractEntity;

public class UberTrip extends RDFAbstractEntity {
  private String cadenAlias;
  private Date requestTime;
  private PostalAddress pickupLocation;
  private Date pickupTime;
  private String priceCurrency;
  private Double totalPrice;
  private PostalAddress dropOffLocation;
  private Date dropOffTime;
  private Double duration;
  private Double distance;
  private String city;
  private String uberType;
  private Date transformationDate;
  private String identifier;
  private String paymentIdLastFour;
  private Double tax;
  private Double serviceFee;

  //Implementing all getter and setter methods

  public String getCadenAlias() {
    return this.cadenAlias;
  }

  public void setCadenAlias(final String cadenAlias) {
    this.cadenAlias = cadenAlias;
  }

  public Date getRequestTime() {
    return this.requestTime;
  }

  public void setRequestTime(final Date requestTime) {
    this.requestTime = requestTime;
  }

  public PostalAddress getPickupLocation() {
    return this.pickupLocation;
  }

  public void setPickupLocation(final PostalAddress pickupLocation) {
    this.pickupLocation = pickupLocation;
  }

  public Date getPickupTime() {
    return this.pickupTime;
  }

  public void setPickupTime(final Date pickupTime) {
    this.pickupTime = pickupTime;
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

  public PostalAddress getDropOffLocation() {
    return this.dropOffLocation;
  }

  public void setDropOffLocation(final PostalAddress dropOffLocation) {
    this.dropOffLocation = dropOffLocation;
  }

  public Date getDropOffTime() {
    return this.dropOffTime;
  }

  public void setDropOffTime(final Date dropOffTime) {
    this.dropOffTime = dropOffTime;
  }

  public Double getDuration() {
    return this.duration;
  }

  public void setDuration(final Double duration) {
    this.duration = duration;
  }

  public Double getDistance() {
    return this.distance;
  }

  public void setDistance(final Double distance) {
    this.distance = distance;
  }

  public String getCity() {
    return this.city;
  }

  public void setCity(final String city) {
    this.city = city;
  }

  public String getUberType() {
    return this.uberType;
  }

  public void setUberType(final String uberType) {
    this.uberType = uberType;
  }

  public Date getTransformationDate() {
    return this.transformationDate;
  }

  public void setTransformationDate(final Date transformationDate) {
    this.transformationDate = transformationDate;
  }

  public String getIdentifier() {
    return identifier;
  }

  public void setIdentifier(String identifier) {
    this.identifier = identifier;
  }

  public String getPaymentIdLastFour() {
    return paymentIdLastFour;
  }

  public void setPaymentIdLastFour(String paymentIdLastFour) {
    this.paymentIdLastFour = paymentIdLastFour;
  }

  public Double getTax() {
    return tax;
  }

  public void setTax(Double tax) {
    this.tax = tax;
  }

  public Double getServiceFee() {
    return serviceFee;
  }

  public void setServiceFee(Double serviceFee) {
    this.serviceFee = serviceFee;
  }
}
