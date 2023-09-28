package io.caden.transformers.spotify.mappers;

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

import io.caden.transformers.config.RDFConfiguration;
import io.caden.transformers.shared.mappers.Mapper;
import io.caden.transformers.shared.utils.CadenBaseConstants;
import io.caden.transformers.shared.utils.OwlConstants;
import io.caden.transformers.shared.utils.RDFNamingConventionUtil;
import io.caden.transformers.shared.utils.SchemaBaseConstants;
import io.caden.transformers.spotify.entities.ListenAction;
import io.caden.transformers.spotify.utils.SpotifyConstants;

@Component
public class ListenActionToStatements extends Mapper<ListenAction, Collection<Statement>> {

  private final RDFConfiguration config;
  private final SimpleValueFactory svf = SimpleValueFactory.getInstance();

  public ListenActionToStatements(@Autowired final RDFConfiguration config) {
    this.config = config;
  }

  @Override
  public Collection<Statement> map(final ListenAction listenAction) {
    String listenActionInstance = getInstance(listenAction);
    String userInstance = RDFNamingConventionUtil.generateUserGraphName(listenAction.getCadenAlias());

    List<Statement> statements =  new ArrayList<>(Arrays.asList(
      svf.createStatement(config.getCadenBaseDataIRI(listenActionInstance), RDF.TYPE, config.getCadenBaseIRI(SpotifyConstants.LISTEN_ACTION)),
      svf.createStatement(config.getCadenBaseDataIRI(listenActionInstance), RDF.TYPE, config.getOwlIRI(OwlConstants.NAMED_INDIVIDUAL)),
      svf.createStatement(config.getCadenBaseDataIRI(listenActionInstance), RDFS.LABEL, Values.literal(listenActionInstance)),
      svf.createStatement(config.getCadenBaseDataIRI(listenActionInstance), config.getSchemaIRI(SchemaBaseConstants.Relationship.AGENT), config.getCadenBaseDataIRI(userInstance)),
      svf.createStatement(config.getCadenBaseDataIRI(listenActionInstance), config.getCadenBaseIRI(CadenBaseConstants.SOURCE_INTAKE), config.getCadenBaseIRI(CadenBaseConstants.Intake.SPOTIFY))
    ));

    if (listenAction.getStartTime() != null) {
      statements.add(
        svf.createStatement(config.getCadenBaseDataIRI(listenActionInstance), config.getSchemaIRI(SchemaBaseConstants.Property.START_TIME), Values.literal(listenAction.getStartTime()))
      );
    }

    return statements;
  }

  public static String getInstance(final ListenAction listenAction) {
    return RDFNamingConventionUtil.generateUserActionName(
      listenAction.getCadenAlias(),
      SpotifyConstants.LISTEN_ACTION,
      listenAction.getStartTime(),
      listenAction.getCreatedAt()
    );
  }

  @Override
  public ListenAction reverseMap(final Collection<Statement> value) {
    return null;
  }
}
