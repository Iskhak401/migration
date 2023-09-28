package io.caden.transformers.airbnb.repositories;

import java.util.Collection;

import io.caden.transformers.airbnb.entities.AirbnbTripReservationPlaceRating;
import io.caden.transformers.shared.repositories.Repository;
import org.eclipse.rdf4j.model.Statement;

public interface AirbnbTripReservationPlaceRatingRepository extends Repository<AirbnbTripReservationPlaceRating> {
  void update(AirbnbTripReservationPlaceRating airbnbTripReservationPlaceRating, Collection<Statement> statements) throws Exception;
  void update(AirbnbTripReservationPlaceRating airbnbTripReservationPlaceRating) throws Exception;
}
