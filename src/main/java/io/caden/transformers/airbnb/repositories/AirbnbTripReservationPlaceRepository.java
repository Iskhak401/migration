package io.caden.transformers.airbnb.repositories;

import java.util.Collection;

import io.caden.transformers.airbnb.entities.AirbnbTripReservationPlace;
import io.caden.transformers.shared.repositories.Repository;
import org.eclipse.rdf4j.model.Statement;

public interface AirbnbTripReservationPlaceRepository extends Repository<AirbnbTripReservationPlace> {
  void update(AirbnbTripReservationPlace airbnbTripReservationPlace, Collection<Statement> statements) throws Exception;
  void update(AirbnbTripReservationPlace airbnbTripReservationPlace) throws Exception;
}
