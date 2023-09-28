package io.caden.transformers.airbnb.mappers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import io.caden.transformers.airbnb.entities.AirbnbTripReservation;
import io.caden.transformers.airbnb.utils.AirbnbConstants;
import io.caden.transformers.config.RDFConfiguration;
import io.caden.transformers.shared.mappers.Mapper;
import io.caden.transformers.shared.utils.CadenBaseConstants;
import io.caden.transformers.shared.utils.OwlConstants;
import io.caden.transformers.shared.utils.RDFNamingConventionUtil;
import io.caden.transformers.shared.utils.SchemaBaseConstants;

import org.eclipse.rdf4j.model.Statement;
import org.eclipse.rdf4j.model.impl.SimpleValueFactory;
import org.eclipse.rdf4j.model.util.Values;
import org.eclipse.rdf4j.model.vocabulary.RDF;
import org.eclipse.rdf4j.model.vocabulary.RDFS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AirbnbTripReservationToStatements extends Mapper<AirbnbTripReservation, Collection<Statement>> {

  private final RDFConfiguration config;
  private final SimpleValueFactory svf = SimpleValueFactory.getInstance();

  public AirbnbTripReservationToStatements(@Autowired final RDFConfiguration config) {
    this.config = config;
  }

  @Override
  public Collection<Statement> map(final AirbnbTripReservation airbnbTripReservation) {
    String airbnbTripReservationInstance = getInstance(airbnbTripReservation);
    String userInstance = RDFNamingConventionUtil.generateUserGraphName(
      airbnbTripReservation.getCadenAlias()
    );

    List<Statement> statements =  new ArrayList<>(Arrays.asList(
      svf.createStatement(config.getCadenBaseDataIRI(airbnbTripReservationInstance), RDF.TYPE, config.getCadenBaseIRI(AirbnbConstants.LODGING_RESERVATION)),
      svf.createStatement(config.getCadenBaseDataIRI(airbnbTripReservationInstance), RDF.TYPE, config.getOwlIRI(OwlConstants.NAMED_INDIVIDUAL)),
      svf.createStatement(config.getCadenBaseDataIRI(airbnbTripReservationInstance), RDFS.LABEL, Values.literal(airbnbTripReservationInstance)),
      svf.createStatement(config.getCadenBaseDataIRI(airbnbTripReservationInstance), config.getSchemaIRI(SchemaBaseConstants.Property.RESERVATION_ID), Values.literal(airbnbTripReservation.getReservationId())),
      svf.createStatement(config.getCadenBaseDataIRI(airbnbTripReservationInstance), config.getSchemaIRI(SchemaBaseConstants.Relationship.UNDER_NAME), config.getCadenBaseDataIRI(userInstance)),
      svf.createStatement(config.getCadenBaseDataIRI(airbnbTripReservationInstance), config.getCadenBaseIRI(CadenBaseConstants.SOURCE_INTAKE), config.getCadenBaseIRI(CadenBaseConstants.Intake.AIRBNB))
    ));

    if (airbnbTripReservation.getCheckinTime() != null) {
      statements.add(
        svf.createStatement(config.getCadenBaseDataIRI(airbnbTripReservationInstance), config.getSchemaIRI(SchemaBaseConstants.Property.CHECKIN_TIME), Values.literal(airbnbTripReservation.getCheckinTime()))
      );
    }

    if (airbnbTripReservation.getCheckoutTime() != null) {
      statements.add(
        svf.createStatement(config.getCadenBaseDataIRI(airbnbTripReservationInstance), config.getSchemaIRI(SchemaBaseConstants.Property.CHECKOUT_TIME), Values.literal(airbnbTripReservation.getCheckoutTime()))
      );
    }

    if (airbnbTripReservation.getNumAdults() != null) {
      statements.add(
        svf.createStatement(config.getCadenBaseDataIRI(airbnbTripReservationInstance), config.getSchemaIRI(SchemaBaseConstants.Property.NUM_ADULTS), Values.literal(airbnbTripReservation.getNumAdults()))
      );
    }

    if (airbnbTripReservation.getPriceCurrency() != null) {
      statements.add(
        svf.createStatement(config.getCadenBaseDataIRI(airbnbTripReservationInstance), config.getSchemaIRI(SchemaBaseConstants.Property.PRICE_CURRENCY), Values.literal(airbnbTripReservation.getPriceCurrency()))
      );
    }

    if (airbnbTripReservation.getTotalPrice() != null) {
      statements.add(
        svf.createStatement(config.getCadenBaseDataIRI(airbnbTripReservationInstance), config.getSchemaIRI(SchemaBaseConstants.Property.TOTAL_PRICE), Values.literal(airbnbTripReservation.getTotalPrice()))
      );
    }

    if (airbnbTripReservation.getReservationStatus() != null) {
      statements.add(
        svf.createStatement(
          config.getCadenBaseDataIRI(airbnbTripReservationInstance),
          config.getSchemaIRI(SchemaBaseConstants.Relationship.RESERVATION_STATUS),
          config.getSchemaIRI(airbnbTripReservation.getReservationStatus().label)
        )
      );
    }

    if (airbnbTripReservation.getPaymentIdLastFour() != null) {
      statements.add(
        svf.createStatement(config.getCadenBaseDataIRI(airbnbTripReservationInstance), config.getCadenBaseIRI(CadenBaseConstants.PAYMENT_ID_LAST_FOUR), Values.literal(airbnbTripReservation.getPaymentIdLastFour()))
      );
    }

    if (airbnbTripReservation.getServiceFee() != null && airbnbTripReservation.getServiceFee().intValue() > 0) {
      statements.add(
        svf.createStatement(
          config.getCadenBaseDataIRI(airbnbTripReservationInstance),
          config.getCadenBaseIRI(AirbnbConstants.SERVICE_FEE),
          Values.literal(Math.round(airbnbTripReservation.getServiceFee() * 100d) / 100d)
        )
      );
    }

    if (airbnbTripReservation.getTax() != null && airbnbTripReservation.getTax().intValue() > 0) {
      statements.add(
        svf.createStatement(
          config.getCadenBaseDataIRI(airbnbTripReservationInstance),
          config.getCadenBaseIRI(AirbnbConstants.TAX),
          Values.literal(Math.round(airbnbTripReservation.getTax() * 100d) / 100d)
        )
      );
    }

    return statements;
  }

  public static String getInstance(final AirbnbTripReservation airbnbTripReservation) {
    return RDFNamingConventionUtil.generateUserActionName(
      airbnbTripReservation.getCadenAlias(),
      AirbnbConstants.LODGING_RESERVATION,
      airbnbTripReservation.getCheckinTime(),
      airbnbTripReservation.getCreatedAt()
    );
  }

  @Override
  public AirbnbTripReservation reverseMap(final Collection<Statement> value) {
    throw new UnsupportedOperationException("Not implemented exception");
  }
}
