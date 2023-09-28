package io.caden.transformers.amazon.entities;

import io.caden.transformers.shared.entities.RDFAbstractEntity;

public class AmazonParcelDelivery extends RDFAbstractEntity {

  private String deliveryAddress;
  private String trackingNumber;
  private DeliveryMethod deliveryMethod;

  public String getDeliveryAddress() {
    return this.deliveryAddress;
  }

  public void setDeliveryAddress(final String deliveryAddress) {
    this.deliveryAddress = deliveryAddress;
  }

  public String getTrackingNumber() {
    return this.trackingNumber;
  }

  public void setTrackingNumber(final String trackingNumber) {
    this.trackingNumber = trackingNumber;
  }

  public DeliveryMethod getDeliveryMethod() {
    return this.deliveryMethod;
  }

  public void setDeliveryMethod(final DeliveryMethod deliveryMethod) {
    this.deliveryMethod = deliveryMethod;
  }
}
