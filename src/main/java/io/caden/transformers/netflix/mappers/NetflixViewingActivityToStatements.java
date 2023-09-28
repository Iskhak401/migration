package io.caden.transformers.netflix.mappers;

import io.caden.transformers.config.RDFConfiguration;
import io.caden.transformers.netflix.entities.NetflixViewingActivity;
import io.caden.transformers.shared.mappers.Mapper;
import io.caden.transformers.shared.utils.CadenBaseConstants;
import io.caden.transformers.shared.utils.OwlConstants;
import io.caden.transformers.shared.utils.RDFNamingConventionUtil;
import io.caden.transformers.shared.utils.SchemaBaseConstants;

import org.eclipse.rdf4j.model.Statement;
import org.eclipse.rdf4j.model.impl.SimpleValueFactory;
import org.eclipse.rdf4j.model.util.Values;
import org.eclipse.rdf4j.model.vocabulary.RDF;
import org.eclipse.rdf4j.model.vocabulary.RDFS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class NetflixViewingActivityToStatements extends Mapper<NetflixViewingActivity, Collection<Statement>>{

    private final RDFConfiguration config;
    private final SimpleValueFactory svf = SimpleValueFactory.getInstance();

    public NetflixViewingActivityToStatements(@Autowired final RDFConfiguration config) {
        this.config = config;
    }

    @Override
    public Collection<Statement> map(final NetflixViewingActivity viewingActivity) {
      // DERPECATED: use mapToWatchAction instead
      return Collections.emptyList();
    }

    @Override
    public NetflixViewingActivity reverseMap(Collection<Statement> value) {
        throw new UnsupportedOperationException("Not implemented exception");
    }

    public String getWatchActionInstance(final NetflixViewingActivity viewingActivity) {
      return RDFNamingConventionUtil.generateUserActionName(
        viewingActivity.getCadenAlias(),
        CadenBaseConstants.WATCH_ACTION,
        viewingActivity.getStartTime(),
        viewingActivity.getInstanceTimestamp(),
        UUID.fromString(viewingActivity.getUuid())
      );
    }

    public String getMovieInstance(final NetflixViewingActivity viewingActivity) {
      return RDFNamingConventionUtil.generateSlashReferenceName(CadenBaseConstants.Intake.NETFLIX, viewingActivity.getNetflixId());
    }

    public String getTvEpisodeInstance(final NetflixViewingActivity viewingActivity) {
      return RDFNamingConventionUtil.generateSlashReferenceName(CadenBaseConstants.Intake.NETFLIX, viewingActivity.getNetflixId());
    }

    public String getTvSeriesInstance(final NetflixViewingActivity viewingActivity) {
      return RDFNamingConventionUtil.generateSlashReferenceName(CadenBaseConstants.Intake.NETFLIX, viewingActivity.getNetflixProductionId());
    }

    public Collection<Statement> mapToWatchAction(final NetflixViewingActivity viewingActivity) {
      String cadenAlias = viewingActivity.getCadenAlias();
      String actionInstance = this.getWatchActionInstance(viewingActivity);
      String userInstance = RDFNamingConventionUtil.generateUserGraphName(cadenAlias);
      String objectInstance = viewingActivity.isSerie() ? this.getTvEpisodeInstance(viewingActivity) : this.getMovieInstance(viewingActivity);

      return new ArrayList<>(Arrays.asList(
        svf.createStatement(config.getCadenBaseDataIRI(actionInstance), RDF.TYPE, config.getCadenBaseIRI(CadenBaseConstants.WATCH_ACTION)),
        svf.createStatement(config.getCadenBaseDataIRI(actionInstance), RDF.TYPE, config.getOwlIRI(OwlConstants.NAMED_INDIVIDUAL)),
        svf.createStatement(config.getCadenBaseDataIRI(actionInstance), RDFS.LABEL, Values.literal(actionInstance)),
        svf.createStatement(config.getCadenBaseDataIRI(actionInstance), config.getSchemaIRI(SchemaBaseConstants.Property.START_TIME), Values.literal(viewingActivity.getStartTime())),
        svf.createStatement(config.getCadenBaseDataIRI(actionInstance), config.getCadenBaseIRI(CadenBaseConstants.SOURCE_INTAKE), config.getCadenBaseIRI(CadenBaseConstants.Intake.NETFLIX)),
        svf.createStatement(config.getCadenBaseDataIRI(actionInstance), config.getSchemaIRI(SchemaBaseConstants.Relationship.AGENT), config.getCadenBaseDataIRI(userInstance)),
        svf.createStatement(config.getCadenBaseDataIRI(actionInstance), config.getSchemaIRI(SchemaBaseConstants.Relationship.OBJECT), config.getCadenReferenceDataIRI(objectInstance))
      ));
    }

    public Collection<Statement> mapToMovie(final NetflixViewingActivity viewingActivity) {
      String movieInstance = this.getMovieInstance(viewingActivity);

      return new ArrayList<>(Arrays.asList(
        svf.createStatement(config.getCadenReferenceDataIRI(movieInstance), RDF.TYPE, config.getSchemaIRI(SchemaBaseConstants.Class.MOVIE)),
        svf.createStatement(config.getCadenReferenceDataIRI(movieInstance), RDF.TYPE, config.getOwlIRI(OwlConstants.NAMED_INDIVIDUAL)),
        svf.createStatement(config.getCadenReferenceDataIRI(movieInstance), RDFS.LABEL, Values.literal(movieInstance)),
        svf.createStatement(config.getCadenReferenceDataIRI(movieInstance), config.getSchemaIRI(SchemaBaseConstants.Property.NAME), Values.literal(viewingActivity.getProductionTitle())),
        svf.createStatement(config.getCadenReferenceDataIRI(movieInstance), config.getSchemaIRI(SchemaBaseConstants.Property.IDENTIFIER), Values.literal(viewingActivity.getNetflixId())),
        svf.createStatement(config.getCadenReferenceDataIRI(movieInstance), config.getSchemaIRI(SchemaBaseConstants.Property.SAME_AS), Values.iri(viewingActivity.getProductionUrl()))
      ));
    }

    public Collection<Statement> mapToTVEpisode(final NetflixViewingActivity viewingActivity) {
      String episodeInstance = this.getTvEpisodeInstance(viewingActivity);
      String seriesInstance = this.getTvSeriesInstance(viewingActivity);

      return new ArrayList<>(Arrays.asList(
        svf.createStatement(config.getCadenReferenceDataIRI(episodeInstance), RDF.TYPE, config.getSchemaIRI(SchemaBaseConstants.Class.TV_EPISODE)),
        svf.createStatement(config.getCadenReferenceDataIRI(episodeInstance), RDF.TYPE, config.getOwlIRI(OwlConstants.NAMED_INDIVIDUAL)),
        svf.createStatement(config.getCadenReferenceDataIRI(episodeInstance), RDFS.LABEL, Values.literal(episodeInstance)),
        svf.createStatement(config.getCadenReferenceDataIRI(episodeInstance), config.getSchemaIRI(SchemaBaseConstants.Property.NAME), Values.literal(viewingActivity.getEpisodeTitle())),
        svf.createStatement(config.getCadenReferenceDataIRI(episodeInstance), config.getSchemaIRI(SchemaBaseConstants.Property.IDENTIFIER), Values.literal(viewingActivity.getNetflixId())),
        svf.createStatement(config.getCadenReferenceDataIRI(episodeInstance), config.getSchemaIRI(SchemaBaseConstants.Property.SAME_AS), Values.iri(viewingActivity.getEpisodeUrl())),
        svf.createStatement(config.getCadenReferenceDataIRI(episodeInstance), config.getSchemaIRI(SchemaBaseConstants.Relationship.PART_OF_SERIES), config.getCadenReferenceDataIRI(seriesInstance))
      ));
    }

    public Collection<Statement> mapToTVSeries(final NetflixViewingActivity viewingActivity) {
      String seriesInstance = this.getTvSeriesInstance(viewingActivity);
      String episodeInstance = this.getTvEpisodeInstance(viewingActivity);

      return new ArrayList<>(Arrays.asList(
        svf.createStatement(config.getCadenReferenceDataIRI(seriesInstance), RDF.TYPE, config.getSchemaIRI(SchemaBaseConstants.Class.TV_SERIES)),
        svf.createStatement(config.getCadenReferenceDataIRI(seriesInstance), RDF.TYPE, config.getOwlIRI(OwlConstants.NAMED_INDIVIDUAL)),
        svf.createStatement(config.getCadenReferenceDataIRI(seriesInstance), RDFS.LABEL, Values.literal(seriesInstance)),
        svf.createStatement(config.getCadenReferenceDataIRI(seriesInstance), config.getSchemaIRI(SchemaBaseConstants.Property.NAME), Values.literal(viewingActivity.getProductionTitle())),
        svf.createStatement(config.getCadenReferenceDataIRI(seriesInstance), config.getSchemaIRI(SchemaBaseConstants.Property.IDENTIFIER), Values.literal(viewingActivity.getNetflixProductionId())),
        svf.createStatement(config.getCadenReferenceDataIRI(seriesInstance), config.getSchemaIRI(SchemaBaseConstants.Property.SAME_AS), Values.iri(viewingActivity.getProductionUrl())),
        svf.createStatement(config.getCadenReferenceDataIRI(seriesInstance), config.getSchemaIRI(SchemaBaseConstants.Relationship.EPISODE), config.getCadenReferenceDataIRI(episodeInstance))
      ));
    }
}
