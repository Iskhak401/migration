package io.caden.transformers.spotify.mappers;

import io.caden.transformers.config.RDFConfiguration;
import io.caden.transformers.shared.mappers.Mapper;
import io.caden.transformers.shared.utils.OwlConstants;
import io.caden.transformers.shared.utils.RDFNamingConventionUtil;
import io.caden.transformers.shared.utils.SchemaBaseConstants;
import io.caden.transformers.spotify.entities.SpotifyArtist;
import io.caden.transformers.spotify.entities.SpotifyTrack;

import org.eclipse.rdf4j.model.Statement;
import org.eclipse.rdf4j.model.impl.SimpleValueFactory;
import org.eclipse.rdf4j.model.util.Values;
import org.eclipse.rdf4j.model.vocabulary.RDF;
import org.eclipse.rdf4j.model.vocabulary.RDFS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@Component
public class SpotifyTrackToStatements extends Mapper<SpotifyTrack, Collection<Statement>> {
  private final RDFConfiguration config;
  private final SimpleValueFactory svf = SimpleValueFactory.getInstance();

  public SpotifyTrackToStatements(@Autowired final RDFConfiguration config) {
    this.config = config;
  }

  @Override
  public Collection<Statement> map(final SpotifyTrack spotifyTrack) {
    String instance = getInstance(spotifyTrack);

    List<Statement> statements =  new ArrayList<>(Arrays.asList(
      svf.createStatement(config.getCadenReferenceDataIRI(instance), RDF.TYPE, config.getSchemaIRI(SchemaBaseConstants.Class.MUSIC_RECORDING)),
      svf.createStatement(config.getCadenReferenceDataIRI(instance), RDF.TYPE, config.getOwlIRI(OwlConstants.NAMED_INDIVIDUAL)),
      svf.createStatement(config.getCadenReferenceDataIRI(instance), RDFS.LABEL, Values.literal(instance)),
      svf.createStatement(config.getCadenReferenceDataIRI(instance), config.getSchemaIRI(SchemaBaseConstants.Property.IDENTIFIER), Values.literal(spotifyTrack.getIdentifier()))
    ));

    if (spotifyTrack.getName() != null) {
      statements.add(
        svf.createStatement(config.getCadenReferenceDataIRI(instance), config.getSchemaIRI(SchemaBaseConstants.Property.NAME), Values.literal(spotifyTrack.getName()))
      );
    }

    if (spotifyTrack.getDuration() != null) {
      statements.add(
        svf.createStatement(config.getCadenReferenceDataIRI(instance), config.getSchemaIRI(SchemaBaseConstants.Property.DURATION), Values.literal(spotifyTrack.getDuration()))
      );
    }

    if (spotifyTrack.getUrl() != null) {
      statements.add(
        svf.createStatement(config.getCadenReferenceDataIRI(instance), config.getSchemaIRI(SchemaBaseConstants.Property.SAME_AS), Values.iri(spotifyTrack.getUrl()))
      );
    }

    if (spotifyTrack.isExplicit()) {
      statements.add(
        svf.createStatement(config.getCadenReferenceDataIRI(instance), config.getSchemaIRI(SchemaBaseConstants.Property.IS_FAMILY_FRIENDLY), Values.literal(spotifyTrack.isExplicit()))
      );
    }

    if (spotifyTrack.getPopularity() != null) {
      statements.add(
        svf.createStatement(config.getCadenReferenceDataIRI(instance), config.getSchemaIRI(SchemaBaseConstants.Property.POPULARITY), Values.literal(spotifyTrack.getPopularity()))
      );
    }

    if (spotifyTrack.getSpotifyAlbum() != null) {
      statements.add(
        svf.createStatement(
          config.getCadenReferenceDataIRI(instance),
          config.getSchemaIRI(SchemaBaseConstants.Relationship.IN_ALBUM),
          config.getCadenReferenceDataIRI(SpotifyAlbumToStatements.getInstance(spotifyTrack.getSpotifyAlbum()))
        )
      );
    }

    if (spotifyTrack.getSpotifyArtists() != null && !spotifyTrack.getSpotifyArtists().isEmpty()) {
      for (SpotifyArtist spotifyArtist : spotifyTrack.getSpotifyArtists()) {
        statements.add(
          svf.createStatement(
            config.getCadenReferenceDataIRI(instance),
            config.getSchemaIRI(SchemaBaseConstants.Relationship.BY_ARTIST),
            config.getCadenReferenceDataIRI(SpotifyArtistToStatements.getInstance(spotifyArtist))
          )
        );
      }
    }

    return statements;
  }

  public static String getInstance(final SpotifyTrack spotifyTrack) {
    return RDFNamingConventionUtil.generateReferenceName(
      SchemaBaseConstants.Class.MUSIC_RECORDING,
      spotifyTrack.getIdentifier()
    );
  }

  @Override
  public SpotifyTrack reverseMap(final Collection<Statement> value) {
    return null;
  }
}
