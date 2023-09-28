package io.caden.transformers.netflix.dtos;

import java.util.Date;

public class NetflixApiViewedItemWrapper {
  private NetflixApiViewedItem netflixApiViewedItem;
  private String netflixUrl;

  public NetflixApiViewedItemWrapper(
    NetflixApiViewedItem netflixApiViewedItem,
    String netflixUrl
  ) {
    this.netflixApiViewedItem = netflixApiViewedItem;
    this.netflixUrl = netflixUrl;
  }

  public boolean isSerie() {
    return this.netflixApiViewedItem.getSeries() != null;
  }
  public long getNetflixId() {
    return this.netflixApiViewedItem.getMovieID();
  }
  public long getNetflixProductionId() {
    return this.isSerie() ? this.netflixApiViewedItem.getSeries() : this.netflixApiViewedItem.getMovieID();
  }
  public Date getDateObject() {
    return new Date(this.netflixApiViewedItem.getDate());
  }
  public String getFullTitle() {
    return this.isSerie() ? this.netflixApiViewedItem.getSeriesTitle() + ": " + this.netflixApiViewedItem.getTitle() : this.netflixApiViewedItem.getTitle();
  }
  public String getProductionTitle() {
    return this.isSerie() ? this.netflixApiViewedItem.getSeriesTitle() : this.netflixApiViewedItem.getTitle();
  }
  public String getEpisodeTitle() {
    return this.isSerie() ? this.netflixApiViewedItem.getEpisodeTitle() : null;
  }
  public String getProductionUrl() {
    return this.netflixUrl + "/title/" + this.getNetflixProductionId();
  }
  public String getEpisodeUrl() {
    return this.isSerie() ? this.netflixUrl + "/title/" + this.getNetflixId() : null;
  }
}
