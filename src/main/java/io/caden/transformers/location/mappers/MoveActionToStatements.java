package io.caden.transformers.location.mappers;

import java.util.*;

import org.eclipse.rdf4j.model.Statement;
import org.eclipse.rdf4j.model.impl.SimpleValueFactory;
import org.eclipse.rdf4j.model.util.Values;
import org.eclipse.rdf4j.model.vocabulary.RDF;
import org.eclipse.rdf4j.model.vocabulary.RDFS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import io.caden.transformers.config.RDFConfiguration;
import io.caden.transformers.location.entities.MoveAction;
import io.caden.transformers.shared.mappers.Mapper;
import io.caden.transformers.shared.utils.CadenBaseConstants;
import io.caden.transformers.shared.utils.OwlConstants;
import io.caden.transformers.shared.utils.RDFNamingConventionUtil;
import io.caden.transformers.shared.utils.SchemaBaseConstants;

@Component
public class MoveActionToStatements extends Mapper<MoveAction, Collection<Statement>> {
  private final RDFConfiguration config;
  private final SimpleValueFactory svf = SimpleValueFactory.getInstance();

  public MoveActionToStatements(@Autowired final RDFConfiguration config) {
    this.config = config;
  }

  @Override
  public Collection<Statement> map(final MoveAction moveAction) {
    String instance = getInstance(moveAction);
    String userInstance = RDFNamingConventionUtil.generateUserGraphName(moveAction.getCadenAlias().toString());

    List<Statement> statements =  new ArrayList<>(Arrays.asList(
      svf.createStatement(config.getCadenBaseDataIRI(instance), RDF.TYPE, config.getSchemaIRI(SchemaBaseConstants.Class.MOVE_ACTION)),
      svf.createStatement(config.getCadenBaseDataIRI(instance), RDF.TYPE, config.getOwlIRI(OwlConstants.NAMED_INDIVIDUAL)),
      svf.createStatement(config.getCadenBaseDataIRI(instance), RDFS.LABEL, Values.literal(instance)),
      svf.createStatement(config.getCadenBaseDataIRI(instance), config.getSchemaIRI(SchemaBaseConstants.Relationship.AGENT), config.getCadenBaseDataIRI(userInstance)),
      svf.createStatement(config.getCadenBaseDataIRI(instance), config.getCadenBaseIRI(CadenBaseConstants.SOURCE_INTAKE), config.getCadenBaseIRI(CadenBaseConstants.Intake.LOCATION)),
      svf.createStatement(config.getCadenBaseDataIRI(instance), config.getSchemaIRI(SchemaBaseConstants.Property.END_TIME), Values.literal(moveAction.getEndTime()))
    ));

    if (moveAction.getStartTime() != null) {
      statements.add(
        svf.createStatement(config.getCadenBaseDataIRI(instance), config.getSchemaIRI(SchemaBaseConstants.Property.START_TIME), Values.literal(moveAction.getStartTime()))
      );
    }

    if (moveAction.getFromLocation() != null && !moveAction.getFromLocation().isBlank()) {
      statements.add(
        svf.createStatement(config.getCadenBaseDataIRI(instance), config.getSchemaIRI(SchemaBaseConstants.Relationship.FROM_LOCATION), config.getCadenBaseDataIRI(moveAction.getFromLocation()))
      );
    }

    String coordinatesInstance = this.getGeoCoordinatesInstance(moveAction);

    statements.addAll(Arrays.asList(
      svf.createStatement(config.getCadenBaseDataIRI(coordinatesInstance), RDF.TYPE, config.getSchemaIRI(SchemaBaseConstants.Class.GEO_COORDINATES)),
      svf.createStatement(config.getCadenBaseDataIRI(coordinatesInstance), RDF.TYPE, config.getOwlIRI(OwlConstants.NAMED_INDIVIDUAL)),
      svf.createStatement(config.getCadenBaseDataIRI(coordinatesInstance), RDFS.LABEL, Values.literal(coordinatesInstance)),
      svf.createStatement(config.getCadenBaseDataIRI(coordinatesInstance), config.getSchemaIRI(SchemaBaseConstants.Property.LATITUDE), Values.literal(moveAction.getLatitude())),
      svf.createStatement(config.getCadenBaseDataIRI(coordinatesInstance), config.getSchemaIRI(SchemaBaseConstants.Property.LONGITUDE), Values.literal(moveAction.getLongitude())),
      svf.createStatement(config.getCadenBaseDataIRI(instance), config.getSchemaIRI(SchemaBaseConstants.Relationship.TO_LOCATION), config.getCadenBaseDataIRI(coordinatesInstance))
    ));

    return statements;
  }

  public static String getInstance(final MoveAction moveAction) {
    return RDFNamingConventionUtil.generateUserActionName(
      moveAction.getCadenAlias().toString(),
      SchemaBaseConstants.Class.MOVE_ACTION,
      moveAction.getEndTime(),
      moveAction.getCreatedAt(),
      UUID.fromString(moveAction.getUuid())
    );
  }

  public String getGeoCoordinatesInstance(final MoveAction moveAction) {
    return RDFNamingConventionUtil.generateAuxiliaryName(
      SchemaBaseConstants.Class.GEO_COORDINATES,
      SchemaBaseConstants.Class.MOVE_ACTION,
      moveAction.getEndTime(),
      moveAction.getEndTime(),
      UUID.fromString(moveAction.getUuid())
    );
  }

  @Override
  public MoveAction reverseMap(final Collection<Statement> value) {
    throw new UnsupportedOperationException("Not implemented exception");
  }
}
