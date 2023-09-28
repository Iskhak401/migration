package io.caden.transformers.spotify.repositories.impl;

import io.caden.transformers.config.RDFConfiguration;
import io.caden.transformers.shared.repositories.BaseRepository;
import io.caden.transformers.shared.utils.SchemaBaseConstants;
import io.caden.transformers.spotify.entities.SpotifyAlbum;
import io.caden.transformers.spotify.mappers.SpotifyAlbumToStatements;
import io.caden.transformers.spotify.repositories.SpotifyAlbumRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;

@Repository("spotifyAlbumRepo")
public class SpotifyAlbumRepositoryImpl extends BaseRepository<SpotifyAlbum> implements SpotifyAlbumRepository {

  private static final Logger LOGGER = LoggerFactory.getLogger(SpotifyAlbumRepositoryImpl.class);

  private final RDFConfiguration config;
  private final SpotifyAlbumToStatements spotifyMapper;

  public SpotifyAlbumRepositoryImpl(
    @Autowired final RDFConfiguration config,
    @Autowired final SpotifyAlbumToStatements spotifyMapper
  ) {
    super(config);
    this.config = config;
    this.spotifyMapper = spotifyMapper;
  }

  @Override
  public SpotifyAlbum find(String filter) throws Exception {
    return null;
  }

  @Override
  public void update(final SpotifyAlbum spotifyAlbum, String cadenAlias) throws Exception {
    List<String> predicateWithNamespaces = Arrays.asList(
      config.getSchemaBase(SchemaBaseConstants.Property.NAME),
      config.getSchemaBase(SchemaBaseConstants.Property.SAME_AS),
      config.getSchemaBase(SchemaBaseConstants.Property.NUM_TRACKS),
      config.getSchemaBase(SchemaBaseConstants.Relationship.ALBUM_RELEASE_TYPE)
    );

    String instance = SpotifyAlbumToStatements.getInstance(spotifyAlbum);

    super.delete(config.getCadenReferenceDataBase(instance), predicateWithNamespaces, config.getCadenReferenceDataIRI(""));
    super.save(this.spotifyMapper.map(spotifyAlbum), config.getCadenReferenceDataIRI(""));
  }
}
