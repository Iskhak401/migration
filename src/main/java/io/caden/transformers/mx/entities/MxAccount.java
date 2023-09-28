package io.caden.transformers.mx.entities;

import io.caden.transformers.shared.entities.RDFAbstractEntity;

public class MxAccount extends RDFAbstractEntity {
  private String paymentId;

  public String getPaymentId() {
    return this.paymentId;
  }

  public void setPaymentId(final String paymentId) {
    this.paymentId = paymentId;
  }
}
