package io.caden.transformers.airbnb.repositories.impl;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.eclipse.rdf4j.model.Statement;
import org.eclipse.rdf4j.model.impl.SimpleValueFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import io.caden.transformers.airbnb.entities.AirbnbTripReservationPlace;
import io.caden.transformers.airbnb.mappers.AirbnbTripReservationPlaceToStatements;
import io.caden.transformers.airbnb.mappers.AirbnbTripReservationToStatements;
import io.caden.transformers.airbnb.repositories.AirbnbTripReservationPlaceRepository;
import io.caden.transformers.airbnb.utils.AirbnbConstants;
import io.caden.transformers.config.RDFConfiguration;
import io.caden.transformers.shared.repositories.BaseRepository;
import io.caden.transformers.shared.utils.RDFNamingConventionUtil;
import io.caden.transformers.shared.utils.SchemaBaseConstants;

@Repository("airbnbTripReservationPlaceRepo")
public class AirbnbTripReservationPlaceRepositoryImpl extends BaseRepository<AirbnbTripReservationPlace> implements AirbnbTripReservationPlaceRepository {

  private final RDFConfiguration config;
  private final AirbnbTripReservationPlaceToStatements airbnbTripReservationPlaceToStatements;
  private final SimpleValueFactory svf = SimpleValueFactory.getInstance();

  public AirbnbTripReservationPlaceRepositoryImpl(
    @Autowired final RDFConfiguration config,
    @Autowired final AirbnbTripReservationPlaceToStatements airbnbTripReservationPlaceToStatements
  ) {
    super(config);
    this.config = config;
    this.airbnbTripReservationPlaceToStatements = airbnbTripReservationPlaceToStatements;
  }

  @Override
  public AirbnbTripReservationPlace find(final String filter) {
    return null;
  }

  @Override
  public void update(final AirbnbTripReservationPlace airbnbTripReservationPlace, final Collection<Statement> statements) throws Exception {
    List<String> predicateWithNamespaces = Arrays.asList(
      config.getSchemaBase(SchemaBaseConstants.Property.AMENITY_FEATURE),
      config.getSchemaBase(SchemaBaseConstants.Property.CHECKIN_TIME),
      config.getSchemaBase(SchemaBaseConstants.Property.CHECKOUT_TIME),
      config.getSchemaBase(SchemaBaseConstants.Property.PETS_ALLOWED),
      config.getSchemaBase(SchemaBaseConstants.Property.NAME),
      config.getSchemaBase(SchemaBaseConstants.Property.IDENTIFIER),
      config.getSchemaBase(SchemaBaseConstants.Property.ADDRESS),
      config.getSchemaBase(SchemaBaseConstants.Property.NUMBER_OF_ROOMS),
      config.getSchemaBase(SchemaBaseConstants.Property.SAME_AS),
      config.getCadenBase(AirbnbConstants.IS_CERTIFIED_HOST),
      config.getCadenBase(AirbnbConstants.NUM_GUESTS_ALLOWED),
      config.getCadenBase(AirbnbConstants.SMOKING_ALLOWED),
      config.getCadenBase(AirbnbConstants.PARTIES_ALLOWED),
      config.getCadenBase(AirbnbConstants.CHILDREN_ALLOWED)
    );

    String instance = AirbnbTripReservationPlaceToStatements.getInstance(airbnbTripReservationPlace);

    super.delete(config.getCadenReferenceDataBase(instance), predicateWithNamespaces, config.getCadenReferenceDataIRI(""));
    super.save(statements, config.getCadenReferenceDataIRI(""));

    super.save(
        Collections.singletonList(svf.createStatement(
            config.getCadenBaseDataIRI(AirbnbTripReservationToStatements.getInstance(
                airbnbTripReservationPlace.getAirbnbTripReservation())),
            config.getSchemaIRI(SchemaBaseConstants.Relationship.RESERVATION_FOR),
            config.getCadenReferenceDataIRI(instance)
        )),
      config.getCadenBaseDataIRI(RDFNamingConventionUtil.generateUserGraphName(
        airbnbTripReservationPlace.getAirbnbTripReservation().getCadenAlias()
      ))
    );
  }

  @Override
  public void update(final AirbnbTripReservationPlace airbnbTripReservationPlace) throws Exception {
    if (airbnbTripReservationPlace.getCreatedAt() == null) {
      airbnbTripReservationPlace.setCreatedAt(new Date());
    }

    if (airbnbTripReservationPlace.getUuid() == null) {
      airbnbTripReservationPlace.setUuid(UUID.randomUUID().toString());
    }

    airbnbTripReservationPlace.setUpdatedAt(new Date());
    this.update(airbnbTripReservationPlace, this.airbnbTripReservationPlaceToStatements.map(airbnbTripReservationPlace));
  }
}
