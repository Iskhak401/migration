package io.caden.transformers.uber.mappers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.eclipse.rdf4j.model.Statement;
import org.eclipse.rdf4j.model.impl.SimpleValueFactory;
import org.eclipse.rdf4j.model.util.Values;
import org.eclipse.rdf4j.model.vocabulary.RDF;
import org.eclipse.rdf4j.model.vocabulary.RDFS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import io.caden.transformers.config.RDFConfiguration;
import io.caden.transformers.shared.mappers.Mapper;
import io.caden.transformers.shared.utils.CadenBaseConstants;
import io.caden.transformers.shared.utils.OwlConstants;
import io.caden.transformers.shared.utils.RDFNamingConventionUtil;
import io.caden.transformers.shared.utils.SchemaBaseConstants;
import io.caden.transformers.uber.entities.PostalAddress;
import io.caden.transformers.uber.entities.UberTrip;
import io.caden.transformers.uber.utils.UberConstants;

@Component
public class UberTripToStatements extends Mapper<UberTrip, Collection<Statement>> {

  private final RDFConfiguration config;
  private final SimpleValueFactory svf = SimpleValueFactory.getInstance();

  public UberTripToStatements(@Autowired final RDFConfiguration config) {
    this.config = config;
  }

  @Override
  public Collection<Statement> map(final UberTrip uberTrip) {
    String userInstance = RDFNamingConventionUtil.generateUserGraphName(uberTrip.getCadenAlias());
    String taxiReservationInstance = RDFNamingConventionUtil.generateUserActionName(
      uberTrip.getCadenAlias(),
      UberConstants.TAXI_RESERVATION,
      uberTrip.getPickupTime(),
      uberTrip.getCreatedAt()
    );

    List<Statement> statements =  new ArrayList<>(Arrays.asList(
      svf.createStatement(config.getCadenBaseDataIRI(taxiReservationInstance), RDF.TYPE, config.getCadenBaseIRI(SchemaBaseConstants.Class.TAXI_RESERVATION)),
      svf.createStatement(config.getCadenBaseDataIRI(taxiReservationInstance), RDF.TYPE, config.getOwlIRI(OwlConstants.NAMED_INDIVIDUAL)),
      svf.createStatement(config.getCadenBaseDataIRI(taxiReservationInstance), RDFS.LABEL, Values.literal(taxiReservationInstance)),
      svf.createStatement(config.getCadenBaseDataIRI(taxiReservationInstance), config.getSchemaIRI(UberConstants.RESERVATION_ID), Values.literal(uberTrip.getUuid())),
      svf.createStatement(config.getCadenBaseDataIRI(taxiReservationInstance), config.getCadenBaseIRI(CadenBaseConstants.SOURCE_INTAKE), config.getCadenBaseIRI(CadenBaseConstants.Intake.UBER)),
      svf.createStatement(config.getCadenBaseDataIRI(taxiReservationInstance), config.getSchemaIRI(SchemaBaseConstants.Relationship.UNDER_NAME), config.getCadenBaseDataIRI(userInstance))
    ));

    if (uberTrip.getPickupTime() != null) {
      statements.add(
        svf.createStatement(config.getCadenBaseDataIRI(taxiReservationInstance), config.getSchemaIRI(UberConstants.PICKUP_TIME), Values.literal(uberTrip.getPickupTime()))
      );
    }

    if (uberTrip.getPriceCurrency() != null) {
      statements.add(
        svf.createStatement(config.getCadenBaseDataIRI(taxiReservationInstance), config.getSchemaIRI(UberConstants.PRICE_CURRENCY), Values.literal(uberTrip.getPriceCurrency()))
      );
    }

    if (uberTrip.getTotalPrice() != null) {
      statements.add(
        svf.createStatement(config.getCadenBaseDataIRI(taxiReservationInstance), config.getSchemaIRI(UberConstants.TOTAL_PRICE), Values.literal(uberTrip.getTotalPrice()))
      );
    }

    if (uberTrip.getIdentifier() != null) {
      statements.add(
              svf.createStatement(config.getCadenBaseDataIRI(taxiReservationInstance), config.getSchemaIRI(UberConstants.IDENTIFIER), Values.literal(uberTrip.getIdentifier()))
      );
    }

    if (uberTrip.getDuration() != null) {
      statements.add(
              svf.createStatement(config.getCadenBaseDataIRI(taxiReservationInstance), config.getCadenBaseIRI(UberConstants.DURATION), Values.literal(uberTrip.getDuration()))
      );
    }

    if (uberTrip.getDropOffTime() != null) {
      statements.add(
        svf.createStatement(config.getCadenBaseDataIRI(taxiReservationInstance), config.getCadenBaseIRI(UberConstants.DROP_OFF_TIME), Values.literal(uberTrip.getDropOffTime()))
      );
    }

    if (uberTrip.getDuration() != null) {
      statements.add(
              svf.createStatement(config.getCadenBaseDataIRI(taxiReservationInstance), config.getCadenBaseIRI(UberConstants.DURATION), Values.literal(uberTrip.getDuration()))
      );
    }

    if (uberTrip.getDistance() != null) {
      statements.add(
        svf.createStatement(config.getCadenBaseDataIRI(taxiReservationInstance), config.getCadenBaseIRI(UberConstants.DISTANCE), Values.literal(uberTrip.getDistance()))
      );
    }

    if (uberTrip.getUberType() != null) {
      statements.add(
        svf.createStatement(config.getCadenBaseDataIRI(taxiReservationInstance), config.getCadenBaseIRI(UberConstants.RIDE_TYPE), Values.literal(uberTrip.getUberType()))
      );
    }

    if (uberTrip.getPaymentIdLastFour() != null) {
      statements.add(
              svf.createStatement(config.getCadenBaseDataIRI(taxiReservationInstance), config.getCadenBaseIRI(UberConstants.PAYMENT_ID_LAST_FOUR), Values.literal(uberTrip.getPaymentIdLastFour()))
      );
    }

    if (uberTrip.getServiceFee() != null) {
      statements.add(
              svf.createStatement(config.getCadenBaseDataIRI(taxiReservationInstance), config.getCadenBaseIRI(UberConstants.SERVICE_FEE), Values.literal(uberTrip.getServiceFee()))
      );
    }

    if (uberTrip.getTax() != null) {
      statements.add(
              svf.createStatement(config.getCadenBaseDataIRI(taxiReservationInstance), config.getCadenBaseIRI(UberConstants.TAX), Values.literal(uberTrip.getTax()))
      );
    }

    if (uberTrip.getPickupLocation() != null) {
      uberTrip.getPickupLocation()
              .setCreatedAt(new Date(uberTrip.getPickupTime().getTime() + new Date().getTime()));
      String pickupInstance = getPostalAddressInstance(uberTrip.getPickupTime());
      statements.add(svf.createStatement(
              config.getCadenBaseDataIRI(taxiReservationInstance),
              config.getSchemaIRI(SchemaBaseConstants.Relationship.PICKUP_LOCATION),
              config.getCadenReferenceDataIRI(pickupInstance)
      ));
      statements.addAll(mapToPostalAddress(uberTrip.getPickupLocation(), uberTrip.getPickupTime()));
    }

    if (uberTrip.getDropOffLocation() != null) {
      uberTrip.getDropOffLocation()
              .setCreatedAt(new Date(uberTrip.getDropOffTime().getTime() + new Date().getTime()));
      String dropoffInstance = getPostalAddressInstance(uberTrip.getPickupTime());
      statements.add(svf.createStatement(
              config.getCadenBaseDataIRI(taxiReservationInstance),
              config.getCadenBaseIRI(SchemaBaseConstants.Relationship.DROPOFF_LOCATION),
              config.getCadenReferenceDataIRI(dropoffInstance)
      ));
      statements.addAll(mapToPostalAddress(uberTrip.getDropOffLocation(), uberTrip.getDropOffTime()));
    }

    return statements;
  }

  public Collection<Statement> mapToPostalAddress(PostalAddress postalAddress, Date actionTimestamp) {
    String postalAddressInstance = getPostalAddressInstance(actionTimestamp);
    List<Statement> statements =  new ArrayList<>(Arrays.asList(
            svf.createStatement(config.getCadenReferenceDataIRI(postalAddressInstance), RDF.TYPE, config.getSchemaIRI(SchemaBaseConstants.Class.POSTAL_ADDRESS)),
            svf.createStatement(config.getCadenReferenceDataIRI(postalAddressInstance), RDF.TYPE, config.getOwlIRI(OwlConstants.NAMED_INDIVIDUAL))
    ));
    if (postalAddress.getAddressCountry() != null) {
      statements.add(
              svf.createStatement(config.getCadenReferenceDataIRI(postalAddressInstance),
                      config.getSchemaIRI(SchemaBaseConstants.Property.ADDRESS_COUNTRY),
                      Values.literal(postalAddress.getAddressCountry()))
      );
    }

    if (postalAddress.getAddressLocality() != null) {
      statements.add(
              svf.createStatement(config.getCadenReferenceDataIRI(postalAddressInstance),
                      config.getSchemaIRI(SchemaBaseConstants.Property.ADDRESS_LOCALITY),
                      Values.literal(postalAddress.getAddressLocality()))
      );
    }

    if (postalAddress.getAddressRegion() != null) {
      statements.add(
              svf.createStatement(config.getCadenReferenceDataIRI(postalAddressInstance),
                      config.getSchemaIRI(SchemaBaseConstants.Property.ADDRESS_REGION),
                      Values.literal(postalAddress.getAddressRegion()))
      );
    }

    if (postalAddress.getPostalCode() != null) {
      statements.add(
              svf.createStatement(config.getCadenReferenceDataIRI(postalAddressInstance),
                      config.getSchemaIRI(SchemaBaseConstants.Property.POSTAL_CODE),
                      Values.literal(postalAddress.getPostalCode()))
      );
    }

    if (postalAddress.getStreetAddress() != null) {
      statements.add(
              svf.createStatement(config.getCadenReferenceDataIRI(postalAddressInstance),
                      config.getSchemaIRI(SchemaBaseConstants.Property.STREET_ADDRESS),
                      Values.literal(postalAddress.getStreetAddress()))
      );
    }

    return statements;
  }

  public String getPostalAddressInstance(Date date) {
    return RDFNamingConventionUtil.generateAuxiliaryName(SchemaBaseConstants.Class.POSTAL_ADDRESS, SchemaBaseConstants.Class.TAXI_RESERVATION, date);
  }


  @Override
  public UberTrip reverseMap(final Collection<Statement> value) {
    throw new UnsupportedOperationException("Not implemented exception");
  }
}
