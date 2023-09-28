package io.caden.transformers.spotify.entities;

import java.util.Date;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonBackReference;

import io.caden.transformers.shared.entities.RDFAbstractEntity;

public class SpotifyTrack extends RDFAbstractEntity {

  @JsonBackReference
  private SpotifyAlbum spotifyAlbum;

  @JsonBackReference
  private Set<SpotifyArtist> spotifyArtists;

  private ListenAction listenAction;

  private Integer duration;
  private String isrc;
  private boolean explicit;
  private String name;
  private String identifier;
  private String url;
  private String trackId;
  private Integer popularity;
  private Date transformationDate;

  public SpotifyAlbum getSpotifyAlbum() {
    return this.spotifyAlbum;
  }

  public void setSpotifyAlbum(final SpotifyAlbum spotifyAlbum) {
    this.spotifyAlbum = spotifyAlbum;
  }

  public Integer getDuration() {
    return this.duration;
  }

  public void setDuration(final Integer duration) {
    this.duration = duration;
  }

  public String getIsrc() {
    return this.isrc;
  }

  public void setIsrc(final String isrc) {
    this.isrc = isrc;
  }

  public boolean isExplicit() {
    return this.explicit;
  }

  public void setExplicit(final boolean explicit) {
    this.explicit = explicit;
  }

  public String getName() {
    return this.name;
  }

  public void setName(final String name) {
    this.name = name;
  }

  public String getIdentifier() {
    return this.identifier;
  }

  public void setIdentifier(final String identifier) {
    this.identifier = identifier;
  }

  public String getUrl() {
    return this.url;
  }

  public void setUrl(final String url) {
    this.url = url;
  }

  public String getTrackId() {
    return this.trackId;
  }

  public void setTrackId(final String trackId) {
    this.trackId = trackId;
  }

  public Integer getPopularity() {
    return this.popularity;
  }

  public void setPopularity(final Integer popularity) {
    this.popularity = popularity;
  }

  public Date getTransformationDate() {
    return this.transformationDate;
  }

  public void setTransformationDate(final Date transformationDate) {
    this.transformationDate = transformationDate;
  }

  public Set<SpotifyArtist> getSpotifyArtists() {
    return this.spotifyArtists;
  }

  public void setSpotifyArtists(final Set<SpotifyArtist> spotifyArtists) {
    this.spotifyArtists = spotifyArtists;
  }

  public ListenAction getListenAction() {
    return this.listenAction;
  }

  public void setListenAction(final ListenAction listenAction) {
    this.listenAction = listenAction;
  }
}
