package io.caden.transformers.airbnb.entities;

public enum AirbnbTripReservationStatus {
  RESERVATION_CANCELLED("ReservationCancelled"),
  RESERVATION_CONFIRMED("ReservationConfirmed"),
  RESERVATION_HOLD("ReservationHold"),
  RESERVATION_PENDING("ReservationPending");

  public final String label;

  private AirbnbTripReservationStatus(final String label) {
    this.label = label;
  }
}
