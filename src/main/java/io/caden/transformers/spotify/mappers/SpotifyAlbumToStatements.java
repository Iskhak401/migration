package io.caden.transformers.spotify.mappers;


import io.caden.transformers.config.RDFConfiguration;
import io.caden.transformers.shared.mappers.Mapper;
import io.caden.transformers.shared.utils.OwlConstants;
import io.caden.transformers.shared.utils.RDFNamingConventionUtil;
import io.caden.transformers.shared.utils.SchemaBaseConstants;
import io.caden.transformers.spotify.entities.SpotifyAlbum;

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

@Component
public class SpotifyAlbumToStatements extends Mapper<SpotifyAlbum, Collection<Statement>> {
  private final RDFConfiguration config;
  private final SimpleValueFactory svf = SimpleValueFactory.getInstance();

  public SpotifyAlbumToStatements(@Autowired final RDFConfiguration config) {
    this.config = config;
  }

  @Override
  public Collection<Statement> map(final SpotifyAlbum spotifyAlbum) {
    String instance = getInstance(spotifyAlbum);

    List<Statement> statements =  new ArrayList<>(Arrays.asList(
      svf.createStatement(config.getCadenReferenceDataIRI(instance), RDF.TYPE, config.getSchemaIRI(SchemaBaseConstants.Class.MUSIC_ALBUM)),
      svf.createStatement(config.getCadenReferenceDataIRI(instance), RDF.TYPE, config.getOwlIRI(OwlConstants.NAMED_INDIVIDUAL)),
      svf.createStatement(config.getCadenReferenceDataIRI(instance), RDFS.LABEL, Values.literal(instance)),
      svf.createStatement(config.getCadenReferenceDataIRI(instance), config.getSchemaIRI(SchemaBaseConstants.Property.IDENTIFIER), Values.literal(spotifyAlbum.getIdentifier()))
    ));

    if (spotifyAlbum.getName() != null) {
      statements.add(
        svf.createStatement(config.getCadenReferenceDataIRI(instance), config.getSchemaIRI(SchemaBaseConstants.Property.NAME), Values.literal(spotifyAlbum.getName()))
      );
    }

    if (spotifyAlbum.getUrl() != null) {
      statements.add(
        svf.createStatement(config.getCadenReferenceDataIRI(instance), config.getSchemaIRI(SchemaBaseConstants.Property.SAME_AS), Values.iri(spotifyAlbum.getUrl()))
      );
    }

    if (spotifyAlbum.getNumTracks() != null) {
      statements.add(
        svf.createStatement(config.getCadenReferenceDataIRI(instance), config.getSchemaIRI(SchemaBaseConstants.Property.NUM_TRACKS), Values.literal(spotifyAlbum.getNumTracks()))
      );
    }

    if (spotifyAlbum.getMusicAlbumProductionType() != null) {
      statements.add(
        svf.createStatement(
          config.getCadenReferenceDataIRI(instance),
          config.getSchemaIRI(SchemaBaseConstants.Relationship.ALBUM_RELEASE_TYPE),
          config.getSchemaIRI(spotifyAlbum.getMusicAlbumProductionType().label)
        )
      );
    }

    return statements;
  }

  public static String getInstance(final SpotifyAlbum spotifyAlbum) {
    return RDFNamingConventionUtil.generateReferenceName(
      SchemaBaseConstants.Class.MUSIC_ALBUM,
      spotifyAlbum.getIdentifier()
    );
  }

  @Override
  public SpotifyAlbum reverseMap(final Collection<Statement> value) {
    return null;
  }
}
