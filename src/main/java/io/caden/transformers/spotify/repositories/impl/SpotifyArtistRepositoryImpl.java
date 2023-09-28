package io.caden.transformers.spotify.repositories.impl;

import io.caden.transformers.config.RDFConfiguration;
import io.caden.transformers.shared.repositories.BaseRepository;
import io.caden.transformers.shared.utils.SchemaBaseConstants;
import io.caden.transformers.spotify.entities.SpotifyArtist;
import io.caden.transformers.spotify.mappers.SpotifyArtistToStatements;
import io.caden.transformers.spotify.repositories.SpotifyArtistRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository("spotifyArtistRepo")
public class SpotifyArtistRepositoryImpl extends BaseRepository<SpotifyArtist> implements SpotifyArtistRepository {

  private static final Logger LOGGER = LoggerFactory.getLogger(SpotifyArtistRepositoryImpl.class);

  private final RDFConfiguration config;
  private final SpotifyArtistToStatements spotifyMapper;

  public SpotifyArtistRepositoryImpl(
    @Autowired final RDFConfiguration config,
    @Autowired final SpotifyArtistToStatements spotifyMapper
  ) {
    super(config);
    this.config = config;
    this.spotifyMapper = spotifyMapper;
  }

  @Override
  public SpotifyArtist find(String filter) throws Exception {
    return null;
  }

  @Override
  public void update(final SpotifyArtist spotifyArtist, String cadenAlias) throws Exception {
    List<String> predicateWithNamespaces = Arrays.asList(
      config.getSchemaBase(SchemaBaseConstants.Property.NAME),
      config.getSchemaBase(SchemaBaseConstants.Property.SAME_AS),
      config.getSchemaBase(SchemaBaseConstants.Property.INTERACTION_STATISTIC),
      config.getSchemaBase(SchemaBaseConstants.Property.GENRE)
    );

    String instance = SpotifyArtistToStatements.getInstance(spotifyArtist);

    super.delete(config.getCadenReferenceDataBase(instance), predicateWithNamespaces, config.getCadenReferenceDataIRI(""));
    super.save(this.spotifyMapper.map(spotifyArtist), config.getCadenReferenceDataIRI(""));
  }
}
