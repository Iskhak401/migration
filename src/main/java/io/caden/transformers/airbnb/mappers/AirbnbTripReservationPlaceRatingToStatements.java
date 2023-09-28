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

import io.caden.transformers.airbnb.entities.AirbnbTripReservationPlaceRating;
import io.caden.transformers.airbnb.utils.AirbnbConstants;
import io.caden.transformers.config.RDFConfiguration;
import io.caden.transformers.shared.mappers.Mapper;
import io.caden.transformers.shared.utils.OwlConstants;
import io.caden.transformers.shared.utils.RDFNamingConventionUtil;
import io.caden.transformers.shared.utils.SchemaBaseConstants;

@Component
public class AirbnbTripReservationPlaceRatingToStatements extends Mapper<AirbnbTripReservationPlaceRating, Collection<Statement>> {

  private final RDFConfiguration config;
  private final SimpleValueFactory svf = SimpleValueFactory.getInstance();

  public AirbnbTripReservationPlaceRatingToStatements(@Autowired final RDFConfiguration config) {
    this.config = config;
  }

  @Override
  public Collection<Statement> map(final AirbnbTripReservationPlaceRating value) {
    String instance = getInstance(value);

    List<Statement> statements =  new ArrayList<>(Arrays.asList(
      svf.createStatement(config.getCadenReferenceDataIRI(instance), RDF.TYPE, config.getSchemaIRI(SchemaBaseConstants.Class.AGGREGATE_RATING)),
      svf.createStatement(config.getCadenReferenceDataIRI(instance), RDF.TYPE, config.getOwlIRI(OwlConstants.NAMED_INDIVIDUAL)),
      svf.createStatement(config.getCadenReferenceDataIRI(instance), RDFS.LABEL, Values.literal(instance)),
      svf.createStatement(
        config.getCadenReferenceDataIRI(AirbnbTripReservationPlaceToStatements.getInstance(value.getAirbnbTripReservationPlace())),
        config.getSchemaIRI(SchemaBaseConstants.Relationship.AGGREGATE_RATING),
        config.getCadenReferenceDataIRI(instance)
      )
    ));

    if (value.getRatingCount() != null) {
      statements.add(
        svf.createStatement(config.getCadenReferenceDataIRI(instance), config.getSchemaIRI(SchemaBaseConstants.Property.RATING_COUNT), Values.literal(value.getRatingCount()))
      );
    }

    if (value.getRatingValue() != null) {
      statements.add(
        svf.createStatement(config.getCadenReferenceDataIRI(instance), config.getSchemaIRI(SchemaBaseConstants.Property.RATING_VALUE), Values.literal(value.getRatingValue()))
      );
    }

    return statements;
  }

  public static String getInstance(final AirbnbTripReservationPlaceRating value) {
    return RDFNamingConventionUtil.generateAuxiliaryName(
      SchemaBaseConstants.Class.AGGREGATE_RATING,
      AirbnbConstants.LODGING_BUSINESS,
      value.getAirbnbTripReservationPlace().getAirbnbTripReservation().getCheckinTime()
    );
  }

  @Override
  public AirbnbTripReservationPlaceRating reverseMap(final Collection<Statement> value) {
    return null;
  }
}
