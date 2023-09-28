package io.caden.transformers.airbnb.entities;

import io.caden.transformers.shared.entities.RDFAbstractEntity;

public class AirbnbTripReservationPlaceRating extends RDFAbstractEntity {
  private AirbnbTripReservationPlace airbnbTripReservationPlace;
  private Double ratingValue;
  private Integer ratingCount;

  public AirbnbTripReservationPlace getAirbnbTripReservationPlace() {
    return this.airbnbTripReservationPlace;
  }

  public void setAirbnbTripReservationPlace(final AirbnbTripReservationPlace airbnbTripReservationPlace) {
    this.airbnbTripReservationPlace = airbnbTripReservationPlace;
  }

  public Double getRatingValue() {
    return this.ratingValue;
  }

  public void setRatingValue(final Double ratingValue) {
    this.ratingValue = ratingValue;
  }

  public Integer getRatingCount() {
    return this.ratingCount;
  }

  public void setRatingCount(final Integer ratingCount) {
    this.ratingCount = ratingCount;
  }
}
