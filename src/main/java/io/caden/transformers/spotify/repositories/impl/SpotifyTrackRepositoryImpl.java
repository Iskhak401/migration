package io.caden.transformers.spotify.repositories.impl;

import io.caden.transformers.config.RDFConfiguration;
import io.caden.transformers.shared.repositories.BaseRepository;
import io.caden.transformers.shared.utils.RDFNamingConventionUtil;
import io.caden.transformers.shared.utils.SchemaBaseConstants;
import io.caden.transformers.spotify.entities.SpotifyTrack;
import io.caden.transformers.spotify.mappers.ListenActionToStatements;
import io.caden.transformers.spotify.mappers.SpotifyTrackToStatements;
import io.caden.transformers.spotify.repositories.SpotifyTrackRepository;

import org.eclipse.rdf4j.model.impl.SimpleValueFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository("spotifyTrackRepo")
public class SpotifyTrackRepositoryImpl extends BaseRepository<SpotifyTrack> implements SpotifyTrackRepository {

  private static final Logger LOGGER = LoggerFactory.getLogger(SpotifyTrackRepositoryImpl.class);

  private final RDFConfiguration config;
  private final SpotifyTrackToStatements spotifyMapper;
  private final SimpleValueFactory svf = SimpleValueFactory.getInstance();

  public SpotifyTrackRepositoryImpl(
    @Autowired final RDFConfiguration config,
    @Autowired final SpotifyTrackToStatements spotifyMapper
  ) {
    super(config);
    this.config = config;
    this.spotifyMapper = spotifyMapper;
  }

  @Override
  public SpotifyTrack find(String filter) throws Exception {
    return null;
  }

  @Override
  public void update(final SpotifyTrack spotifyTrack) throws Exception {
    List<String> predicateWithNamespaces = Arrays.asList(
      config.getSchemaBase(SchemaBaseConstants.Property.NAME),
      config.getSchemaBase(SchemaBaseConstants.Property.SAME_AS),
      config.getSchemaBase(SchemaBaseConstants.Property.DURATION),
      config.getSchemaBase(SchemaBaseConstants.Property.IS_FAMILY_FRIENDLY)
    );

    String instance = SpotifyTrackToStatements.getInstance(spotifyTrack);

    super.delete(config.getCadenReferenceDataBase(instance), predicateWithNamespaces, config.getCadenReferenceDataIRI(""));
    super.save(this.spotifyMapper.map(spotifyTrack), config.getCadenReferenceDataIRI(""));

    super.save(
        Collections.singletonList(svf.createStatement(
            config.getCadenBaseDataIRI(
                ListenActionToStatements.getInstance(spotifyTrack.getListenAction())),
            config.getSchemaIRI(SchemaBaseConstants.Relationship.OBJECT),
            config.getCadenReferenceDataIRI(instance)
        )),
      config.getCadenBaseDataIRI(RDFNamingConventionUtil.generateUserGraphName(spotifyTrack.getListenAction().getCadenAlias()))
    );
  }
}
