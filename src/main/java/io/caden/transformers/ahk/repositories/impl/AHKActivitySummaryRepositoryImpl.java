package io.caden.transformers.ahk.repositories.impl;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.eclipse.rdf4j.model.IRI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import io.caden.transformers.ahk.entities.AHKActivitySummary;
import io.caden.transformers.ahk.mappers.AHKActivitySummaryToStatements;
import io.caden.transformers.ahk.repositories.AHKActivitySummaryRepository;
import io.caden.transformers.ahk.utils.AppleHealthKitConstants;
import io.caden.transformers.config.RDFConfiguration;
import io.caden.transformers.shared.repositories.BaseRepository;
import io.caden.transformers.shared.utils.RDFNamingConventionUtil;
import io.caden.transformers.shared.utils.SchemaBaseConstants;

@Repository("ahkActivitySummaryRepo")
public class AHKActivitySummaryRepositoryImpl extends BaseRepository<AHKActivitySummary> implements AHKActivitySummaryRepository {
  private final RDFConfiguration config;
  private final AHKActivitySummaryToStatements ahkActivitySummaryToStatements;

  public AHKActivitySummaryRepositoryImpl(
    @Autowired final RDFConfiguration config,
    @Autowired final AHKActivitySummaryToStatements ahkActivitySummaryToStatements
  ) {
    super(config);
    this.config = config;
    this.ahkActivitySummaryToStatements = ahkActivitySummaryToStatements;
  }

  @Override
  public AHKActivitySummary find(final String filter) throws Exception {
    throw new UnsupportedOperationException("Not implemented exception");
  }

  @Override
  public void update(final AHKActivitySummary ahkActivitySummary) throws Exception {
    if (ahkActivitySummary.getCreatedAt() == null) {
      ahkActivitySummary.setCreatedAt(new Date());
    }

    if (ahkActivitySummary.getUuid() == null) {
      ahkActivitySummary.setUuid(UUID.randomUUID().toString());
    }

    ahkActivitySummary.setUpdatedAt(new Date());

    List<String> predicateWithNamespaces = Arrays.asList(
      config.getCadenBase(AppleHealthKitConstants.ACTIVE_ENERGY_BURNED),
      config.getCadenBase(AppleHealthKitConstants.ACTIVE_ENERGY_BURNED_GOAL),
      config.getCadenBase(AppleHealthKitConstants.MOVE_TIME),
      config.getCadenBase(AppleHealthKitConstants.MOVE_TIME_GOAL),
      config.getCadenBase(AppleHealthKitConstants.EXERCISE_TIME),
      config.getCadenBase(AppleHealthKitConstants.EXERCISE_TIME_GOAL),
      config.getCadenBase(AppleHealthKitConstants.STAND_HOURS),
      config.getCadenBase(AppleHealthKitConstants.STAND_HOURS_GOAL),
      config.getCadenBase(AppleHealthKitConstants.STEP_COUNT),
      config.getSchemaBase(SchemaBaseConstants.Property.START_TIME),
      config.getSchemaBase(SchemaBaseConstants.Property.END_TIME)
    );

    String instance = AHKActivitySummaryToStatements.getInstance(ahkActivitySummary);
    IRI userInstance = config.getCadenBaseDataIRI(RDFNamingConventionUtil.generateUserGraphName(ahkActivitySummary.getCadenAlias().toString()));

    super.delete(config.getCadenReferenceDataBase(instance), predicateWithNamespaces, userInstance);
    super.save(this.ahkActivitySummaryToStatements.map(ahkActivitySummary), userInstance);
  }
}
