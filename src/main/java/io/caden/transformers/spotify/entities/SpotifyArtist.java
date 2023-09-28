package io.caden.transformers.spotify.entities;

import java.util.Date;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import io.caden.transformers.shared.entities.RDFAbstractEntity;

public class SpotifyArtist extends RDFAbstractEntity {
  @JsonManagedReference
  private Set<SpotifyArtistGenre> spotifyArtistGenres;

  private String legalName;
  private String identifier;
  private String image;
  private String name;
  private String url;
  private Integer popularity;
  private String artistId;
  private Integer totalFollowers;
  private Date transformationDate;

  public String getLegalName() {
    return this.legalName;
  }

  public void setLegalName(final String legalName) {
    this.legalName = legalName;
  }

  public String getIdentifier() {
    return this.identifier;
  }

  public void setIdentifier(final String identifier) {
    this.identifier = identifier;
  }

  public String getImage() {
    return this.image;
  }

  public void setImage(final String image) {
    this.image = image;
  }

  public String getName() {
    return this.name;
  }

  public void setName(final String name) {
    this.name = name;
  }

  public String getArtistId() {
    return this.artistId;
  }

  public void setArtistId(final String artistId) {
    this.artistId = artistId;
  }

  public Integer getPopularity() {
    return this.popularity;
  }

  public void setPopularity(final Integer popularity) {
    this.popularity = popularity;
  }

  public String getUrl() {
    return this.url;
  }

  public void setUrl(final String url) {
    this.url = url;
  }

  public Date getTransformationDate() {
    return this.transformationDate;
  }

  public void setTransformationDate(final Date transformationDate) {
    this.transformationDate = transformationDate;
  }

  public Set<SpotifyArtistGenre> getSpotifyArtistGenres() {
    return this.spotifyArtistGenres;
  }

  public void setSpotifyArtistGenres(final Set<SpotifyArtistGenre> spotifyArtistGenres) {
    this.spotifyArtistGenres = spotifyArtistGenres;
  }

  public Integer getTotalFollowers() {
    return this.totalFollowers;
  }

  public void setTotalFollowers(final Integer totalFollowers) {
    this.totalFollowers = totalFollowers;
  }
}
