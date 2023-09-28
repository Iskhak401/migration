package io.caden.transformers.ahk.repositories.impl;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.eclipse.rdf4j.model.IRI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import io.caden.transformers.ahk.entities.AHKWorkout;
import io.caden.transformers.ahk.mappers.AHKWorkoutToStatements;
import io.caden.transformers.ahk.repositories.AHKWorkoutRepository;
import io.caden.transformers.ahk.utils.AppleHealthKitConstants;
import io.caden.transformers.config.RDFConfiguration;
import io.caden.transformers.shared.repositories.BaseRepository;
import io.caden.transformers.shared.utils.RDFNamingConventionUtil;
import io.caden.transformers.shared.utils.SchemaBaseConstants;

@Repository("ahkWorkoutRepo")
public class AHKWorkoutRepositoryImpl extends BaseRepository<AHKWorkout> implements AHKWorkoutRepository {
  private final RDFConfiguration config;
  private final AHKWorkoutToStatements ahkWorkoutToStatements;

  public AHKWorkoutRepositoryImpl(
    @Autowired final RDFConfiguration config,
    @Autowired final AHKWorkoutToStatements ahkWorkoutToStatements
  ) {
    super(config);
    this.config = config;
    this.ahkWorkoutToStatements = ahkWorkoutToStatements;
  }

  @Override
  public AHKWorkout find(final String filter) throws Exception {
    throw new UnsupportedOperationException("Not implemented exception");
  }

  @Override
  public void update(final AHKWorkout ahkWorkout) throws Exception {
    if (ahkWorkout.getCreatedAt() == null) {
      ahkWorkout.setCreatedAt(new Date());
    }

    if (ahkWorkout.getUuid() == null) {
      ahkWorkout.setUuid(UUID.randomUUID().toString());
    }

    ahkWorkout.setUpdatedAt(new Date());

    List<String> predicateWithNamespaces = Arrays.asList(
      config.getSchemaBase(SchemaBaseConstants.Property.DISTANCE),
      config.getSchemaBase(SchemaBaseConstants.Property.EXERCISE_TYPE),
      config.getSchemaBase(SchemaBaseConstants.Property.START_TIME),
      config.getSchemaBase(SchemaBaseConstants.Property.END_TIME),
      config.getCadenBase(AppleHealthKitConstants.TOTAL_ENERGY_BURNED),
      config.getCadenBase(AppleHealthKitConstants.TOTAL_FLIGHTS_CLIMBED),
      config.getCadenBase(AppleHealthKitConstants.TOTAL_SWIMMING_STROKE_COUNT)
    );

    String instance = AHKWorkoutToStatements.getInstance(ahkWorkout);
    IRI userInstance = config.getCadenBaseDataIRI(RDFNamingConventionUtil.generateUserGraphName(ahkWorkout.getCadenAlias().toString()));

    super.delete(config.getCadenReferenceDataBase(instance), predicateWithNamespaces, userInstance);
    super.save(this.ahkWorkoutToStatements.map(ahkWorkout), userInstance);
  }
}
