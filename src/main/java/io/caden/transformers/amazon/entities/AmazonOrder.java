package io.caden.transformers.amazon.entities;

import io.caden.transformers.shared.entities.RDFAbstractEntity;

import java.util.Date;
import java.util.Set;

public class AmazonOrder extends RDFAbstractEntity {
  private String cadenAlias;

  private String customer;

  private String billingAddress;

  private Date orderDate;

  private String orderNumber;

  private AmazonOrderStatus orderStatus;

  private String paymentIdLastFour;

  private Double price;

  private String priceCurrency;

  private Double deliveryFee;

  private Double tax;

  private String shippingAddress;

  private String previousOrderId;

  private Set<AmazonOrderItem> amazonOrderItems;

  public String getCadenAlias() {
    return this.cadenAlias;
  }

  public void setCadenAlias(final String cadenAlias) {
    this.cadenAlias = cadenAlias;
  }

  public String getCustomer() {
    return this.customer;
  }

  public void setCustomer(final String customer) {
    this.customer = customer;
  }

  public Date getOrderDate() {
    return this.orderDate;
  }

  public void setOrderDate(final Date orderDate) {
    this.orderDate = orderDate;
  }

  public String getOrderNumber() {
    return this.orderNumber;
  }

  public void setOrderNumber(final String orderNumber) {
    this.orderNumber = orderNumber;
  }

  public Double getPrice() {
    return this.price;
  }

  public void setPrice(final Double price) {
    this.price = price;
  }

  public String getShippingAddress() {
    return this.shippingAddress;
  }

  public void setShippingAddress(final String shippingAddress) {
    this.shippingAddress = shippingAddress;
  }

  public Set<AmazonOrderItem> getAmazonOrderItems() {
    return this.amazonOrderItems;
  }

  public void setAmazonOrderItems(final Set<AmazonOrderItem> amazonOrderItems) {
    this.amazonOrderItems = amazonOrderItems;
  }

  public String getBillingAddress() {
    return billingAddress;
  }

  public void setBillingAddress(final String billingAddress) {
    this.billingAddress = billingAddress;
  }

  public AmazonOrderStatus getOrderStatus() {
    return orderStatus;
  }

  public void setOrderStatus(final AmazonOrderStatus orderStatus) {
    this.orderStatus = orderStatus;
  }

  public String getPaymentIdLastFour() {
    return paymentIdLastFour;
  }

  public void setPaymentIdLastFour(final String paymentIdLastFour) {
    this.paymentIdLastFour = paymentIdLastFour;
  }

  public String getPriceCurrency() {
    return priceCurrency;
  }

  public void setPriceCurrency(final String priceCurrency) {
    this.priceCurrency = priceCurrency;
  }

  public Double getDeliveryFee() {
    return deliveryFee;
  }

  public void setDeliveryFee(final Double deliveryFee) {
    this.deliveryFee = deliveryFee;
  }

  public Double getTax() {
    return tax;
  }

  public void setTax(final Double tax) {
    this.tax = tax;
  }

  public String getPreviousOrderId() {
    return previousOrderId;
  }

  public void setPreviousOrderId(String previousOrderId) {
    this.previousOrderId = previousOrderId;
  }
}
