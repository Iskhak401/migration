package io.caden.transformers.airbnb.repositories.impl;

import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.eclipse.rdf4j.model.Statement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import io.caden.transformers.airbnb.entities.AirbnbTripReservationPlaceRating;
import io.caden.transformers.airbnb.mappers.AirbnbTripReservationPlaceRatingToStatements;
import io.caden.transformers.airbnb.repositories.AirbnbTripReservationPlaceRatingRepository;
import io.caden.transformers.config.RDFConfiguration;
import io.caden.transformers.shared.repositories.BaseRepository;
import io.caden.transformers.shared.utils.SchemaBaseConstants;

@Repository("airbnbTripReservationPlaceRatingRepo")
public class AirbnbTripReservationPlaceRatingRepositoryImpl extends BaseRepository<AirbnbTripReservationPlaceRating> implements AirbnbTripReservationPlaceRatingRepository {

  private final RDFConfiguration config;
  private final AirbnbTripReservationPlaceRatingToStatements airbnbTripReservationPlaceRatingToStatements;

  public AirbnbTripReservationPlaceRatingRepositoryImpl(
    @Autowired final RDFConfiguration config,
    @Autowired final AirbnbTripReservationPlaceRatingToStatements airbnbTripReservationPlaceRatingToStatements
  ) {
    super(config);
    this.config = config;
    this.airbnbTripReservationPlaceRatingToStatements = airbnbTripReservationPlaceRatingToStatements;
  }

  @Override
  public AirbnbTripReservationPlaceRating find(final String filter) throws Exception {
    return null;
  }

  @Override
  public void update(final AirbnbTripReservationPlaceRating airbnbTripReservationPlaceRating, final Collection<Statement> statements) throws Exception {
    List<String> predicateWithNamespaces = Arrays.asList(
      config.getSchemaBase(SchemaBaseConstants.Property.RATING_COUNT),
      config.getSchemaBase(SchemaBaseConstants.Property.RATING_VALUE)
    );

    String instance = AirbnbTripReservationPlaceRatingToStatements.getInstance(airbnbTripReservationPlaceRating);

    super.delete(config.getCadenReferenceDataBase(instance), predicateWithNamespaces, config.getCadenReferenceDataIRI(""));
    super.save(statements, config.getCadenReferenceDataIRI(""));
  }

  @Override
  public void update(final AirbnbTripReservationPlaceRating airbnbTripReservationPlaceRating) throws Exception {
    if (airbnbTripReservationPlaceRating.getCreatedAt() == null) {
      airbnbTripReservationPlaceRating.setCreatedAt(new Date());
    }

    airbnbTripReservationPlaceRating.setUpdatedAt(new Date());
    this.update(airbnbTripReservationPlaceRating, this.airbnbTripReservationPlaceRatingToStatements.map(airbnbTripReservationPlaceRating));
  }
}
