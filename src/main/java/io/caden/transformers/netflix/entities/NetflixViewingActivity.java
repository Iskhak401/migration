package io.caden.transformers.netflix.entities;

import java.util.Date;

import io.caden.transformers.shared.entities.RDFAbstractEntity;

public class NetflixViewingActivity extends RDFAbstractEntity {
  private String cadenAlias;

  private String object;

  private Date startTime;

  private String netflixId;

  private String netflixProductionId;

  private Date transformationDate;

  private String productionTitle;

  private String episodeTitle;

  private Boolean serie;

  private NetflixProfile netflixProfile;

  private Date instanceTimestamp;

  private String productionUrl;

  private String episodeUrl;

  public String getCadenAlias() {
    return this.cadenAlias;
  }

  public void setCadenAlias(final String cadenAlias) {
    this.cadenAlias = cadenAlias;
  }

  public String getObject() {
    return this.object;
  }

  public void setObject(final String object) {
    this.object = object;
  }

  public Date getStartTime() {
    return this.startTime;
  }

  public void setStartTime(final Date startTime) {
    this.startTime = startTime;
  }

  public String getNetflixId() {
    return this.netflixId;
  }

  public void setNetflixId(final String netflixId) {
    this.netflixId = netflixId;
  }

  public String getNetflixProductionId() {
    return this.netflixProductionId;
  }

  public void setNetflixProductionId(final String netflixProductionId) {
    this.netflixProductionId = netflixProductionId;
  }

  public Date getTransformationDate() {
    return this.transformationDate;
  }

  public void setTransformationDate(final Date transformationDate) {
    this.transformationDate = transformationDate;
  }

  public String getProductionTitle() {
    return this.productionTitle;
  }

  public void setProductionTitle(final String productionTitle) {
    this.productionTitle = productionTitle;
  }

  public String getEpisodeTitle() {
    return this.episodeTitle;
  }

  public void setEpisodeTitle(final String episodeTitle) {
    this.episodeTitle = episodeTitle;
  }

  public Boolean isSerie() {
    return this.serie;
  }

  public void setSerie(final Boolean serie) {
    this.serie = serie;
  }

  public NetflixProfile getNetflixProfile() {
    return this.netflixProfile;
  }

  public void setNetflixProfile(final NetflixProfile netflixProfile) {
    this.netflixProfile = netflixProfile;
  }

  public Date getInstanceTimestamp() {
    return this.instanceTimestamp;
  }

  public void setInstanceTimestamp(Date instanceTimestamp) {
    this.instanceTimestamp = instanceTimestamp;
  }

  public String getProductionUrl() {
    return this.productionUrl;
  }

  public void setProductionUrl(final String productionUrl) {
    this.productionUrl = productionUrl;
  }

  public String getEpisodeUrl() {
    return this.episodeUrl;
  }

  public void setEpisodeUrl(final String episodeUrl) {
    this.episodeUrl = episodeUrl;
  }
}
