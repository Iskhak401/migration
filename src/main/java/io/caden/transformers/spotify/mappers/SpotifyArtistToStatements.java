package io.caden.transformers.spotify.mappers;

import io.caden.transformers.config.RDFConfiguration;
import io.caden.transformers.shared.mappers.Mapper;
import io.caden.transformers.shared.utils.OwlConstants;
import io.caden.transformers.shared.utils.RDFNamingConventionUtil;
import io.caden.transformers.shared.utils.SchemaBaseConstants;
import io.caden.transformers.spotify.entities.SpotifyArtist;
import io.caden.transformers.spotify.entities.SpotifyArtistGenre;

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
public class SpotifyArtistToStatements extends Mapper<SpotifyArtist, Collection<Statement>> {
  private final RDFConfiguration config;
  private final SimpleValueFactory svf = SimpleValueFactory.getInstance();

  public SpotifyArtistToStatements(@Autowired final RDFConfiguration config) {
    this.config = config;
  }

  @Override
  public Collection<Statement> map(final SpotifyArtist spotifyArtist) {
    String instance = getInstance(spotifyArtist);

    List<Statement> statements =  new ArrayList<>(Arrays.asList(
      svf.createStatement(config.getCadenReferenceDataIRI(instance), RDF.TYPE, config.getSchemaIRI(SchemaBaseConstants.Class.MUSIC_GROUP)),
      svf.createStatement(config.getCadenReferenceDataIRI(instance), RDF.TYPE, config.getOwlIRI(OwlConstants.NAMED_INDIVIDUAL)),
      svf.createStatement(config.getCadenReferenceDataIRI(instance), RDFS.LABEL, Values.literal(instance)),
      svf.createStatement(config.getCadenReferenceDataIRI(instance), config.getSchemaIRI(SchemaBaseConstants.Property.IDENTIFIER), Values.literal(spotifyArtist.getIdentifier()))
    ));

    if (spotifyArtist.getName() != null) {
      statements.add(
        svf.createStatement(config.getCadenReferenceDataIRI(instance), config.getSchemaIRI(SchemaBaseConstants.Property.NAME), Values.literal(spotifyArtist.getName()))
      );
    }

    if (spotifyArtist.getUrl() != null) {
      statements.add(
        svf.createStatement(config.getCadenReferenceDataIRI(instance), config.getSchemaIRI(SchemaBaseConstants.Property.SAME_AS), Values.iri(spotifyArtist.getUrl()))
      );
    }

    if (spotifyArtist.getTotalFollowers() != null) {
      statements.add(
        svf.createStatement(config.getCadenReferenceDataIRI(instance), config.getSchemaIRI(SchemaBaseConstants.Property.INTERACTION_STATISTIC), Values.literal(spotifyArtist.getTotalFollowers()))
      );
    }

    if (spotifyArtist.getSpotifyArtistGenres() != null && !spotifyArtist.getSpotifyArtistGenres().isEmpty()) {
      for (SpotifyArtistGenre artistGenre : spotifyArtist.getSpotifyArtistGenres()) {
        statements.add(
          svf.createStatement(
            config.getCadenReferenceDataIRI(instance),
            config.getSchemaIRI(SchemaBaseConstants.Property.GENRE),
            Values.literal(artistGenre.getGenre())
          )
        );
      }
    }

    return statements;
  }

  public static String getInstance(final SpotifyArtist spotifyArtist) {
    return RDFNamingConventionUtil.generateReferenceName(
      SchemaBaseConstants.Class.MUSIC_GROUP,
      spotifyArtist.getIdentifier()
    );
  }

  @Override
  public SpotifyArtist reverseMap(final Collection<Statement> value) {
    return null;
  }
}
