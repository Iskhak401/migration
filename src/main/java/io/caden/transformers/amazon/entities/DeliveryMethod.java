package io.caden.transformers.amazon.entities;

public enum DeliveryMethod {
  LOCKER_DELIVERY("LockerDelivery"),
  ON_SITE_PICKUP("OnSitePickup"),
  PARCEL_SERVICE("ParcelService");

  public final String label;

  private DeliveryMethod(final String label) {
    this.label = label;
  }
}
