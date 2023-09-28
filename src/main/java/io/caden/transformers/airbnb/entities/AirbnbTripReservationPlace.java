package io.caden.transformers.airbnb.entities;

import java.util.Date;
import java.util.Set;

import io.caden.transformers.shared.entities.RDFAbstractEntity;

public class AirbnbTripReservationPlace extends RDFAbstractEntity {
  private AirbnbTripReservation airbnbTripReservation;
  private String amenityFeature;
  private Date checkinTime;
  private Date checkoutTime;
  private Integer numberOfRooms;
  private Boolean petsAllowed;
  private String address;
  private String name;
  private String identifier;
  private String sameAs;
  private Boolean certifiedHost;
  private Integer numberOfAllowedGuests;
  private Boolean smokingAllowed;
  private Boolean partiesAllowed;
  private Boolean childrenAllowed;
  private Set<AirbnbTripReservationPlaceRating> airbnbTripReservationPlaceRatings;

  public AirbnbTripReservation getAirbnbTripReservation() {
    return this.airbnbTripReservation;
  }

  public void setAirbnbTripReservation(final AirbnbTripReservation airbnbTripReservation) {
    this.airbnbTripReservation = airbnbTripReservation;
  }

  public String getAmenityFeature() {
    return this.amenityFeature;
  }

  public void setAmenityFeature(final String amenityFeature) {
    this.amenityFeature = amenityFeature;
  }

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

  public Integer getNumberOfRooms() {
    return this.numberOfRooms;
  }

  public void setNumberOfRooms(final Integer numberOfRooms) {
    this.numberOfRooms = numberOfRooms;
  }

  public Boolean arePetsAllowed() {
    return this.petsAllowed;
  }

  public void setPetsAllowed(final Boolean petsAllowed) {
    this.petsAllowed = petsAllowed;
  }

  public String getAddress() {
    return this.address;
  }

  public void setAddress(final String address) {
    this.address = address;
  }

  public String getName() {
    return this.name;
  }

  public void setName(final String name) {
    this.name = name;
  }

  public String getIdentifier() {
    return this.identifier;
  }

  public void setIdentifier(final String identifier) {
    this.identifier = identifier;
  }

  public String getSameAs() {
    return this.sameAs;
  }

  public void setSameAs(final String sameAs) {
    this.sameAs = sameAs;
  }

  public Boolean isCertifiedHost() {
    return this.certifiedHost;
  }

  public void setCertifiedHost(final Boolean certifiedHost) {
    this.certifiedHost = certifiedHost;
  }

  public Integer getNumberOfAllowedGuests() {
    return this.numberOfAllowedGuests;
  }

  public void setNumberOfAllowedGuests(final Integer numberOfAllowedGuests) {
    this.numberOfAllowedGuests = numberOfAllowedGuests;
  }

  public Boolean isSmokingAllowed() {
    return this.smokingAllowed;
  }

  public void setSmokingAllowed(final Boolean smokingAllowed) {
    this.smokingAllowed = smokingAllowed;
  }

  public Boolean arePartiesAllowed() {
    return this.partiesAllowed;
  }

  public void setPartiesAllowed(final Boolean partiesAllowed) {
    this.partiesAllowed = partiesAllowed;
  }

  public Boolean areChildrenAllowed() {
    return this.childrenAllowed;
  }

  public void setChildrenAllowed(final Boolean childrenAllowed) {
    this.childrenAllowed = childrenAllowed;
  }

  public Set<AirbnbTripReservationPlaceRating> getAirbnbTripReservationPlaceRatings() {
    return this.airbnbTripReservationPlaceRatings;
  }

  public void setAirbnbTripReservationPlaceRatings(final Set<AirbnbTripReservationPlaceRating> airbnbTripReservationPlaceRatings) {
    this.airbnbTripReservationPlaceRatings = airbnbTripReservationPlaceRatings;
  }
}
