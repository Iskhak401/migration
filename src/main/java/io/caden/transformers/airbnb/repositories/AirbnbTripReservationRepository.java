package io.caden.transformers.airbnb.repositories;

import java.util.Collection;

import io.caden.transformers.airbnb.entities.AirbnbTripReservation;
import io.caden.transformers.shared.repositories.Repository;
import org.eclipse.rdf4j.model.Statement;

public interface AirbnbTripReservationRepository extends Repository<AirbnbTripReservation> {
  void update(AirbnbTripReservation airbnbTripReservation, Collection<Statement> statements) throws Exception;

  void update(AirbnbTripReservation airbnbTripReservation) throws Exception;

  AirbnbTripReservation find(final String reservationId, String cadenAlias) throws Exception;
}
