package io.caden.transformers.ahk.mappers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.apache.commons.lang3.ObjectUtils;
import org.eclipse.rdf4j.model.Statement;
import org.eclipse.rdf4j.model.impl.SimpleValueFactory;
import org.eclipse.rdf4j.model.util.Values;
import org.eclipse.rdf4j.model.vocabulary.RDF;
import org.eclipse.rdf4j.model.vocabulary.RDFS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import io.caden.transformers.ahk.entities.AHKActivitySummary;
import io.caden.transformers.ahk.utils.AppleHealthKitConstants;
import io.caden.transformers.config.RDFConfiguration;
import io.caden.transformers.shared.mappers.Mapper;
import io.caden.transformers.shared.utils.CadenBaseConstants;
import io.caden.transformers.shared.utils.OwlConstants;
import io.caden.transformers.shared.utils.RDFNamingConventionUtil;
import io.caden.transformers.shared.utils.SchemaBaseConstants;

@Component
public class AHKActivitySummaryToStatements extends Mapper<AHKActivitySummary, Collection<Statement>> {
  private final RDFConfiguration config;
  private final SimpleValueFactory svf = SimpleValueFactory.getInstance();

  public AHKActivitySummaryToStatements(@Autowired final RDFConfiguration config) {
    this.config = config;
  }

  @Override
  public Collection<Statement> map(final AHKActivitySummary ahkActivitySummary) {
    String instance = getInstance(ahkActivitySummary);
    String userInstance = RDFNamingConventionUtil.generateUserGraphName(ahkActivitySummary.getCadenAlias().toString());

    List<Statement> statements =  new ArrayList<>(Arrays.asList(
      svf.createStatement(config.getCadenBaseDataIRI(instance), RDF.TYPE, config.getCadenBaseIRI(AppleHealthKitConstants.DAILY_ACTIVITY)),
      svf.createStatement(config.getCadenBaseDataIRI(instance), RDF.TYPE, config.getOwlIRI(OwlConstants.NAMED_INDIVIDUAL)),
      svf.createStatement(config.getCadenBaseDataIRI(instance), RDFS.LABEL, Values.literal(instance)),
      svf.createStatement(config.getCadenBaseDataIRI(instance), config.getSchemaIRI(SchemaBaseConstants.Relationship.AGENT), config.getCadenBaseDataIRI(userInstance)),
      svf.createStatement(config.getCadenBaseDataIRI(instance), config.getCadenBaseIRI(CadenBaseConstants.SOURCE_INTAKE), config.getCadenBaseIRI(CadenBaseConstants.Intake.APPLE_HEALTH_KIT))
    ));

    if (ahkActivitySummary.getActiveEnergyBurned() != null) {
      statements.add(
        svf.createStatement(config.getCadenBaseDataIRI(instance), config.getCadenBaseIRI(AppleHealthKitConstants.ACTIVE_ENERGY_BURNED), Values.literal(ahkActivitySummary.getActiveEnergyBurned()))
      );
    }

    if (ahkActivitySummary.getActiveEnergyBurnedGoal() != null) {
      statements.add(
        svf.createStatement(config.getCadenBaseDataIRI(instance), config.getCadenBaseIRI(AppleHealthKitConstants.ACTIVE_ENERGY_BURNED_GOAL), Values.literal(ahkActivitySummary.getActiveEnergyBurnedGoal()))
      );
    }

    if (ahkActivitySummary.getMoveTime() != null) {
      statements.add(
        svf.createStatement(config.getCadenBaseDataIRI(instance), config.getCadenBaseIRI(AppleHealthKitConstants.MOVE_TIME), Values.literal(ahkActivitySummary.getMoveTime()))
      );
    }

    if (ahkActivitySummary.getMoveTimeGoal() != null) {
      statements.add(
        svf.createStatement(config.getCadenBaseDataIRI(instance), config.getCadenBaseIRI(AppleHealthKitConstants.MOVE_TIME_GOAL), Values.literal(ahkActivitySummary.getMoveTimeGoal()))
      );
    }

    if (ahkActivitySummary.getExerciseTime() != null) {
      statements.add(
        svf.createStatement(config.getCadenBaseDataIRI(instance), config.getCadenBaseIRI(AppleHealthKitConstants.EXERCISE_TIME), Values.literal(ahkActivitySummary.getExerciseTime()))
      );
    }

    if (ahkActivitySummary.getExerciseTimeGoal() != null) {
      statements.add(
        svf.createStatement(config.getCadenBaseDataIRI(instance), config.getCadenBaseIRI(AppleHealthKitConstants.EXERCISE_TIME_GOAL), Values.literal(ahkActivitySummary.getExerciseTimeGoal()))
      );
    }

    if (ahkActivitySummary.getStandHours() != null) {
      statements.add(
        svf.createStatement(config.getCadenBaseDataIRI(instance), config.getCadenBaseIRI(AppleHealthKitConstants.STAND_HOURS), Values.literal(ahkActivitySummary.getStandHours()))
      );
    }

    if (ahkActivitySummary.getStandHoursGoal() != null) {
      statements.add(
        svf.createStatement(config.getCadenBaseDataIRI(instance), config.getCadenBaseIRI(AppleHealthKitConstants.STAND_HOURS_GOAL), Values.literal(ahkActivitySummary.getStandHoursGoal()))
      );
    }

    if (ahkActivitySummary.getStepCount() != null && ahkActivitySummary.getStepCount() > 0) {
      statements.add(
        svf.createStatement(config.getCadenBaseDataIRI(instance), config.getCadenBaseIRI(AppleHealthKitConstants.STEP_COUNT), Values.literal(ahkActivitySummary.getStepCount()))
      );
    }

    if (ahkActivitySummary.getStartDate() != null) {
      statements.add(
        svf.createStatement(config.getCadenBaseDataIRI(instance), config.getSchemaIRI(SchemaBaseConstants.Property.START_TIME), Values.literal(ahkActivitySummary.getStartDate()))
      );
    }

    if (ahkActivitySummary.getEndDate() != null) {
      statements.add(
        svf.createStatement(config.getCadenBaseDataIRI(instance), config.getSchemaIRI(SchemaBaseConstants.Property.END_TIME), Values.literal(ahkActivitySummary.getEndDate()))
      );
    }

    return statements;
  }

  public static String getInstance(final AHKActivitySummary ahkActivitySummary) {
    return RDFNamingConventionUtil.generateUserActionName(
      ahkActivitySummary.getCadenAlias().toString(),
      AppleHealthKitConstants.DAILY_ACTIVITY,
      ObjectUtils.defaultIfNull(ahkActivitySummary.getStartDate(), ahkActivitySummary.getEndDate()),
      ahkActivitySummary.getCreatedAt()
    );
  }

  @Override
  public AHKActivitySummary reverseMap(final Collection<Statement> value) {
    throw new UnsupportedOperationException("Not implemented exception");
  }
}
