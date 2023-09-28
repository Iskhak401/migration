package io.caden.transformers.airbnb.repositories.impl;

import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import io.caden.transformers.airbnb.entities.AirbnbTripReservation;
import io.caden.transformers.airbnb.mappers.AirbnbTripReservationToStatements;
import io.caden.transformers.airbnb.mappers.BindingSetToAirbnbTripReservation;
import io.caden.transformers.airbnb.repositories.AirbnbTripReservationRepository;
import io.caden.transformers.airbnb.utils.AirbnbConstants;
import io.caden.transformers.config.RDFConfiguration;
import io.caden.transformers.shared.repositories.BaseRepository;
import io.caden.transformers.shared.utils.CadenBaseConstants;
import io.caden.transformers.shared.utils.RDFNamingConventionUtil;
import io.caden.transformers.shared.utils.SchemaBaseConstants;

import org.eclipse.rdf4j.model.IRI;
import org.eclipse.rdf4j.model.Statement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository("airbnbTripReservationRepo")
public class AirbnbTripReservationRepositoryImpl extends BaseRepository<AirbnbTripReservation> implements AirbnbTripReservationRepository {
  private final RDFConfiguration config;
  private final AirbnbTripReservationToStatements airbnbTripReservationToStatements;
  private final BindingSetToAirbnbTripReservation bindingSetToAirbnbTripReservation;

  public AirbnbTripReservationRepositoryImpl(
    @Autowired final RDFConfiguration config,
    @Autowired final AirbnbTripReservationToStatements airbnbTripReservationToStatements,
    @Autowired final BindingSetToAirbnbTripReservation bindingSetToAirbnbTripReservation
  ) {
    super(config);
    this.config = config;
    this.airbnbTripReservationToStatements = airbnbTripReservationToStatements;
    this.bindingSetToAirbnbTripReservation = bindingSetToAirbnbTripReservation;
  }

  @Override
  public AirbnbTripReservation find(String filter) throws Exception {
    throw new UnsupportedOperationException("Not implemented exception");
  }

  @Override
  public AirbnbTripReservation find(final String reservationId, String cadenAlias) throws Exception {
    String queryString =
      "PREFIX cdn: <" + config.getCadenBaseNamespace() + "> " +
      "PREFIX sch: <" + config.getSchemaNamespace() + "> " +
      "SELECT * { "
        + "GRAPH ?g { "
          + "?s a cdn:LodgingReservation . "
          + "?s sch:reservationId ?reservationId . "
          + "OPTIONAL { ?s sch:checkinTime ?checkinTime } "
          + "OPTIONAL { ?s sch:checkoutTime ?checkoutTime } "
          + "OPTIONAL { ?s sch:numAdults ?numAdults } "
          + "OPTIONAL { ?s sch:priceCurrency ?priceCurrency } "
          + "OPTIONAL { ?s sch:totalPrice ?totalPrice } "
          + "OPTIONAL { ?s sch:reservationStatus ?reservationStatus } "
          + "OPTIONAL { ?s cdn:paymentIdLastFour ?paymentIdLastFour } "
          + "OPTIONAL { ?s cdn:serviceFee ?serviceFee } "
          + "OPTIONAL { ?s cdn:tax ?tax } "
        + "} "
        + "FILTER (?reservationId = ?filter) "
      + "}";

    Map<String, Object> params = new HashMap<>();
    params.put("filter", reservationId);
    params.put("g", config.getCadenBaseDataIRI(RDFNamingConventionUtil.generateUserGraphName(cadenAlias)));

    return this.bindingSetToAirbnbTripReservation.map(this.executeQuery(queryString, params));
  }

  @Override
  public void update(final AirbnbTripReservation airbnbTripReservation, final Collection<Statement> statements) throws Exception {
    List<String> predicateWithNamespaces = Arrays.asList(
      config.getSchemaBase(SchemaBaseConstants.Property.CHECKIN_TIME),
      config.getSchemaBase(SchemaBaseConstants.Property.CHECKOUT_TIME),
      config.getSchemaBase(SchemaBaseConstants.Property.NUM_ADULTS),
      config.getSchemaBase(SchemaBaseConstants.Property.PRICE_CURRENCY),
      config.getSchemaBase(SchemaBaseConstants.Property.TOTAL_PRICE),
      config.getSchemaBase(SchemaBaseConstants.Relationship.RESERVATION_STATUS),
      config.getSchemaBase(SchemaBaseConstants.Relationship.RESERVATION_FOR),
      config.getCadenBase(CadenBaseConstants.PAYMENT_ID_LAST_FOUR),
      config.getCadenBase(AirbnbConstants.SERVICE_FEE),
      config.getCadenBase(AirbnbConstants.TAX)
    );

    String instance = AirbnbTripReservationToStatements.getInstance(airbnbTripReservation);
    IRI userInstance = config.getCadenBaseDataIRI(RDFNamingConventionUtil.generateUserGraphName(
      airbnbTripReservation.getCadenAlias()
    ));

    super.delete(config.getCadenBaseDataBase(instance), predicateWithNamespaces, userInstance);
    super.save(statements, userInstance);
  }

  @Override
  public void update(final AirbnbTripReservation airbnbTripReservation) throws Exception {
    if (airbnbTripReservation.getCreatedAt() == null) {
      airbnbTripReservation.setCreatedAt(new Date());
    }

    if (airbnbTripReservation.getUuid() == null) {
      airbnbTripReservation.setUuid(UUID.randomUUID().toString());
    }

    airbnbTripReservation.setUpdatedAt(new Date());
    this.update(airbnbTripReservation, this.airbnbTripReservationToStatements.map(airbnbTripReservation));
  }
}
