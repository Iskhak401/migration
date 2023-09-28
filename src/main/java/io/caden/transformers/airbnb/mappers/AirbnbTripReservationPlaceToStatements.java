package io.caden.transformers.airbnb.mappers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.eclipse.rdf4j.model.Statement;
import org.eclipse.rdf4j.model.impl.SimpleValueFactory;
import org.eclipse.rdf4j.model.util.Values;
import org.eclipse.rdf4j.model.vocabulary.RDF;
import org.eclipse.rdf4j.model.vocabulary.RDFS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import io.caden.transformers.airbnb.entities.AirbnbTripReservationPlace;
import io.caden.transformers.airbnb.utils.AirbnbConstants;
import io.caden.transformers.config.RDFConfiguration;
import io.caden.transformers.shared.mappers.Mapper;
import io.caden.transformers.shared.utils.OwlConstants;
import io.caden.transformers.shared.utils.RDFNamingConventionUtil;
import io.caden.transformers.shared.utils.SchemaBaseConstants;

@Component
public class AirbnbTripReservationPlaceToStatements extends Mapper<AirbnbTripReservationPlace, Collection<Statement>> {

  private final RDFConfiguration config;
  private final SimpleValueFactory svf = SimpleValueFactory.getInstance();

  public AirbnbTripReservationPlaceToStatements(@Autowired final RDFConfiguration config) {
    this.config = config;
  }

  @Override
  public Collection<Statement> map(final AirbnbTripReservationPlace value) {
    String instance = getInstance(value);

    List<Statement> statements = new ArrayList<>(Arrays.asList(
      svf.createStatement(config.getCadenReferenceDataIRI(instance), RDF.TYPE, config.getCadenBaseIRI(AirbnbConstants.LODGING_BUSINESS)),
      svf.createStatement(config.getCadenReferenceDataIRI(instance), RDF.TYPE, config.getOwlIRI(OwlConstants.NAMED_INDIVIDUAL)),
      svf.createStatement(config.getCadenReferenceDataIRI(instance), RDFS.LABEL, Values.literal(instance))
    ));

    if (value.getAmenityFeature() != null) {
      statements.add(
        svf.createStatement(config.getCadenReferenceDataIRI(instance), config.getSchemaIRI(SchemaBaseConstants.Property.AMENITY_FEATURE), Values.literal(value.getAmenityFeature()))
      );
    }

    if (value.getCheckinTime() != null) {
      statements.add(
        svf.createStatement(config.getCadenReferenceDataIRI(instance), config.getSchemaIRI(SchemaBaseConstants.Property.CHECKIN_TIME), Values.literal(value.getCheckinTime()))
      );
    }

    if (value.getCheckoutTime() != null) {
      statements.add(
        svf.createStatement(config.getCadenReferenceDataIRI(instance), config.getSchemaIRI(SchemaBaseConstants.Property.CHECKOUT_TIME), Values.literal(value.getCheckoutTime()))
      );
    }

    if (value.arePetsAllowed() != null) {
      statements.add(
        svf.createStatement(config.getCadenReferenceDataIRI(instance), config.getSchemaIRI(SchemaBaseConstants.Property.PETS_ALLOWED), Values.literal(value.arePetsAllowed()))
      );
    }

    if (value.getName() != null) {
      statements.add(
        svf.createStatement(config.getCadenReferenceDataIRI(instance), config.getSchemaIRI(SchemaBaseConstants.Property.NAME), Values.literal(value.getName()))
      );
    }

    if (value.getIdentifier() != null) {
      statements.add(
        svf.createStatement(config.getCadenReferenceDataIRI(instance), config.getSchemaIRI(SchemaBaseConstants.Property.IDENTIFIER), Values.literal(value.getIdentifier()))
      );
    }

    if (value.getAddress() != null) {
      statements.add(
        svf.createStatement(config.getCadenReferenceDataIRI(instance), config.getSchemaIRI(SchemaBaseConstants.Property.ADDRESS), Values.literal(value.getAddress()))
      );
    }

    if (value.getNumberOfRooms() != null) {
      statements.add(
        svf.createStatement(config.getCadenReferenceDataIRI(instance), config.getSchemaIRI(SchemaBaseConstants.Property.NUMBER_OF_ROOMS), Values.literal(value.getNumberOfRooms()))
      );
    }

    if (value.getSameAs() != null) {
      statements.add(
        svf.createStatement(config.getCadenReferenceDataIRI(instance), config.getSchemaIRI(SchemaBaseConstants.Property.SAME_AS), Values.iri(value.getSameAs()))
      );
    }

    if (value.isCertifiedHost() != null) {
      statements.add(
        svf.createStatement(config.getCadenReferenceDataIRI(instance), config.getCadenBaseIRI(AirbnbConstants.IS_CERTIFIED_HOST), Values.literal(value.isCertifiedHost()))
      );
    }

    if (value.getNumberOfAllowedGuests() != null) {
      statements.add(
        svf.createStatement(config.getCadenReferenceDataIRI(instance), config.getCadenBaseIRI(AirbnbConstants.NUM_GUESTS_ALLOWED), Values.literal(value.getNumberOfAllowedGuests()))
      );
    }

    if (value.isSmokingAllowed() != null) {
      statements.add(
        svf.createStatement(config.getCadenReferenceDataIRI(instance), config.getCadenBaseIRI(AirbnbConstants.SMOKING_ALLOWED), Values.literal(value.isSmokingAllowed()))
      );
    }

    if (value.arePartiesAllowed() != null) {
      statements.add(
        svf.createStatement(config.getCadenReferenceDataIRI(instance), config.getCadenBaseIRI(AirbnbConstants.PARTIES_ALLOWED), Values.literal(value.arePartiesAllowed()))
      );
    }

    if (value.areChildrenAllowed() != null) {
      statements.add(
        svf.createStatement(config.getCadenReferenceDataIRI(instance), config.getCadenBaseIRI(AirbnbConstants.CHILDREN_ALLOWED), Values.literal(value.areChildrenAllowed()))
      );
    }

    return statements;
  }

  public static String getInstance(final AirbnbTripReservationPlace value) {
    return RDFNamingConventionUtil.generateReferenceName(
      AirbnbConstants.LODGING_BUSINESS,
      value.getIdentifier()
    );
  }

  @Override
  public AirbnbTripReservationPlace reverseMap(final Collection<Statement> value) {
    return null;
  }
}
