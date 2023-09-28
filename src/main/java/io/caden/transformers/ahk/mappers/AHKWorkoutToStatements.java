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

import io.caden.transformers.ahk.entities.AHKWorkout;
import io.caden.transformers.ahk.utils.AppleHealthKitConstants;
import io.caden.transformers.config.RDFConfiguration;
import io.caden.transformers.shared.mappers.Mapper;
import io.caden.transformers.shared.utils.CadenBaseConstants;
import io.caden.transformers.shared.utils.OwlConstants;
import io.caden.transformers.shared.utils.RDFNamingConventionUtil;
import io.caden.transformers.shared.utils.SchemaBaseConstants;

@Component
public class AHKWorkoutToStatements extends Mapper<AHKWorkout, Collection<Statement>> {
  private final RDFConfiguration config;
  private final SimpleValueFactory svf = SimpleValueFactory.getInstance();

  public AHKWorkoutToStatements(@Autowired final RDFConfiguration config) {
    this.config = config;
  }

  @Override
  public Collection<Statement> map(final AHKWorkout ahkWorkout) {
    String instance = getInstance(ahkWorkout);
    String userInstance = RDFNamingConventionUtil.generateUserGraphName(ahkWorkout.getCadenAlias().toString());

    List<Statement> statements =  new ArrayList<>(Arrays.asList(
      svf.createStatement(config.getCadenBaseDataIRI(instance), RDF.TYPE, config.getCadenBaseIRI(AppleHealthKitConstants.EXERCISE_ACTION)),
      svf.createStatement(config.getCadenBaseDataIRI(instance), RDF.TYPE, config.getOwlIRI(OwlConstants.NAMED_INDIVIDUAL)),
      svf.createStatement(config.getCadenBaseDataIRI(instance), RDFS.LABEL, Values.literal(instance)),
      svf.createStatement(config.getCadenBaseDataIRI(instance), config.getSchemaIRI(SchemaBaseConstants.Relationship.AGENT), config.getCadenBaseDataIRI(userInstance)),
      svf.createStatement(config.getCadenBaseDataIRI(instance), config.getCadenBaseIRI(CadenBaseConstants.SOURCE_INTAKE), config.getCadenBaseIRI(CadenBaseConstants.Intake.APPLE_HEALTH_KIT))
    ));

    if (ahkWorkout.getDistance() != null) {
      statements.add(
        svf.createStatement(config.getCadenBaseDataIRI(instance), config.getSchemaIRI(SchemaBaseConstants.Property.DISTANCE), Values.literal(ahkWorkout.getDistance()))
      );
    }

    if (ahkWorkout.getExerciseType() != null) {
      statements.add(
        svf.createStatement(config.getCadenBaseDataIRI(instance), config.getSchemaIRI(SchemaBaseConstants.Property.EXERCISE_TYPE), Values.literal(ahkWorkout.getExerciseType()))
      );
    }

    if (ahkWorkout.getStartDate() != null) {
      statements.add(
        svf.createStatement(config.getCadenBaseDataIRI(instance), config.getSchemaIRI(SchemaBaseConstants.Property.START_TIME), Values.literal(ahkWorkout.getStartDate()))
      );
    }

    if (ahkWorkout.getEndDate() != null) {
      statements.add(
        svf.createStatement(config.getCadenBaseDataIRI(instance), config.getSchemaIRI(SchemaBaseConstants.Property.END_TIME), Values.literal(ahkWorkout.getEndDate()))
      );
    }

    if (ahkWorkout.getTotalEnergyBurned() != null) {
      statements.add(
        svf.createStatement(config.getCadenBaseDataIRI(instance), config.getCadenBaseIRI(AppleHealthKitConstants.TOTAL_ENERGY_BURNED), Values.literal(ahkWorkout.getTotalEnergyBurned()))
      );
    }

    if (ahkWorkout.getTotalFlightsClimbed() != null) {
      statements.add(
        svf.createStatement(config.getCadenBaseDataIRI(instance), config.getCadenBaseIRI(AppleHealthKitConstants.TOTAL_FLIGHTS_CLIMBED), Values.literal(ahkWorkout.getTotalFlightsClimbed()))
      );
    }

    if (ahkWorkout.getTotalSwimmingStrokeCount() != null) {
      statements.add(
        svf.createStatement(config.getCadenBaseDataIRI(instance), config.getCadenBaseIRI(AppleHealthKitConstants.TOTAL_SWIMMING_STROKE_COUNT), Values.literal(ahkWorkout.getTotalSwimmingStrokeCount()))
      );
    }

    return statements;
  }

  public static String getInstance(final AHKWorkout ahkWorkout) {
    return RDFNamingConventionUtil.generateUserActionName(
      ahkWorkout.getCadenAlias().toString(),
      AppleHealthKitConstants.EXERCISE_ACTION,
      ObjectUtils.defaultIfNull(ahkWorkout.getStartDate(), ahkWorkout.getEndDate()),
      ahkWorkout.getCreatedAt()
    );
  }

  @Override
  public AHKWorkout reverseMap(final Collection<Statement> value) {
    throw new UnsupportedOperationException("Not implemented exception");
  }
}
