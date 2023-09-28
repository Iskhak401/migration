package io.caden.transformers.spotify.entities;

import java.util.Date;

import io.caden.transformers.shared.entities.RDFAbstractEntity;

public class SpotifyAlbum extends RDFAbstractEntity {
  private String cadenAlias;

  private String albumId;
  private String albumProductionType;
  private Integer numTracks;
  private Date dateCreated;
  private String identifier;
  private String image;
  private String name;
  private String url;
  private Date transformationDate;

  private MusicAlbumProductionType musicAlbumProductionType;

  public String getCadenAlias() {
    return this.cadenAlias;
  }

  public void setCadenAlias(final String cadenAlias) {
    this.cadenAlias = cadenAlias;
  }

  public String getAlbumId() {
    return this.albumId;
  }

  public void setAlbumId(final String albumId) {
    this.albumId = albumId;
  }

  public String getAlbumProductionType() {
    return this.albumProductionType;
  }

  public void setAlbumProductionType(final String albumProductionType) {
    this.albumProductionType = albumProductionType;
  }

  public Integer getNumTracks() {
    return this.numTracks;
  }

  public void setNumTracks(final Integer numTracks) {
    this.numTracks = numTracks;
  }

  public Date getDateCreated() {
    return this.dateCreated;
  }

  public void setDateCreated(final Date dateCreated) {
    this.dateCreated = dateCreated;
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

  public MusicAlbumProductionType getMusicAlbumProductionType() {
    return this.musicAlbumProductionType;
  }

  public void setMusicAlbumProductionType(final MusicAlbumProductionType musicAlbumProductionType) {
    this.musicAlbumProductionType = musicAlbumProductionType;
  }
}
