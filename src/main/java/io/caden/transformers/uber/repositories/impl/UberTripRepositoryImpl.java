package io.caden.transformers.uber.repositories.impl;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.rdf4j.model.Statement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import io.caden.transformers.config.RDFConfiguration;
import io.caden.transformers.shared.repositories.BaseRepository;
import io.caden.transformers.shared.utils.RDFNamingConventionUtil;
import io.caden.transformers.uber.entities.UberTrip;
import io.caden.transformers.uber.mappers.BindingSetToUberTrip;
import io.caden.transformers.uber.mappers.UberTripToStatements;
import io.caden.transformers.uber.repositories.UberTripRepository;

@Repository("uberTripRepo")
public class UberTripRepositoryImpl extends BaseRepository<UberTrip> implements UberTripRepository {

  private static final Logger LOGGER = LoggerFactory.getLogger(UberTripRepositoryImpl.class);

  private final RDFConfiguration config;
  private final UberTripToStatements uberTripToStatements;
  private final BindingSetToUberTrip bindingSetToUberTrip;

  public UberTripRepositoryImpl(
    @Autowired final RDFConfiguration config,
    @Autowired final UberTripToStatements uberTripToStatements,
    @Autowired final BindingSetToUberTrip bindingSetToUberTrip
  ) {
    super(config);
    this.config = config;
    this.uberTripToStatements = uberTripToStatements;
    this.bindingSetToUberTrip = bindingSetToUberTrip;
  }

  @Override
  public UberTrip find(final String reservationId) throws Exception {
    throw new UnsupportedOperationException("Not implemented exception");
  }

  @Override
  public UberTrip findByIdentifier(String identifier, String cadenAlias) throws Exception {
    String queryString =
        "PREFIX cdn: <" + config.getCadenBaseNamespace() + "> " +
        "PREFIX sch: <" + config.getSchemaNamespace() + "> " +
        "SELECT * { " +
            "  GRAPH ?g { " +
            "    ?s a cdn:TaxiReservation . " +
            "    ?s sch:identifier ?identifier . " +
            "    OPTIONAL {?s sch:pickupLocation ?pickupLocation .} " +
            "    OPTIONAL {?s cdn:dropoffLocation ?dropoffLocation .} " +
            "    OPTIONAL {?s sch:pickupTime ?pickupTime .} " +
            "    OPTIONAL {?s cdn:dropOffTime ?dropOffTime .} " +
            "    OPTIONAL {?s cdn:distance ?distance .} " +
            "    OPTIONAL {?s cdn:rideType ?rideType .} " +
            "    OPTIONAL {?s sch:priceCurrency ?priceCurrency .} " +
            "    OPTIONAL {?s sch:totalPrice ?totalPrice .} " +
            "    OPTIONAL {?s cdn:paymentIdLastFour ?paymentIDLastFour .} " +
            "    OPTIONAL { ?s cdn:serviceFee ?serviceFee } . " +
            "    OPTIONAL { ?s cdn:tax ?tax } . " +
            "  } " +
            "}";

    Map<String, Object> params = new HashMap<>();
    params.put("identifier", identifier);
    params.put("g", config.getCadenBaseDataIRI(RDFNamingConventionUtil.generateUserGraphName(cadenAlias)));

    return this.bindingSetToUberTrip.map(this.executeQuery(queryString, params));
  }

  @Override
  public void update(final UberTrip uberTrip, final Collection<Statement> statements) throws Exception {
    super.save(statements,
            config.getCadenBaseDataIRI(RDFNamingConventionUtil.generateUserGraphName(uberTrip.getCadenAlias())));
  }

  @Override
  public void update(final UberTrip uberTrip) throws Exception {
    this.update(uberTrip, this.uberTripToStatements.map(uberTrip));
  }
}
