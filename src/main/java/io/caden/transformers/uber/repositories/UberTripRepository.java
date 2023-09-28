package io.caden.transformers.uber.repositories;

import java.util.Collection;

import io.caden.transformers.shared.repositories.Repository;
import io.caden.transformers.uber.entities.UberTrip;
import org.eclipse.rdf4j.model.Statement;

public interface UberTripRepository extends Repository<UberTrip> {

  UberTrip findByIdentifier(String identifier, String cadenAlias) throws Exception;

  void update(UberTrip uberTrip, Collection<Statement> statements) throws Exception;

  void update(UberTrip uberTrip) throws Exception;
}
