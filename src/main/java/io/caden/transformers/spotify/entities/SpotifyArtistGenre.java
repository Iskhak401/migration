package io.caden.transformers.spotify.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;

import io.caden.transformers.shared.entities.RDFAbstractEntity;

public class SpotifyArtistGenre extends RDFAbstractEntity {
  @JsonBackReference
  private SpotifyArtist spotifyArtist;

  private String genre;

  public SpotifyArtist getSpotifyArtist() {
    return this.spotifyArtist;
  }

  public void setSpotifyArtist(final SpotifyArtist spotifyArtist) {
    this.spotifyArtist = spotifyArtist;
  }

  public String getGenre() {
    return this.genre;
  }

  public void setGenre(final String genre) {
    this.genre = genre;
  }
}
