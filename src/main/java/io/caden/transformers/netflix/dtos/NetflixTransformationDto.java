package io.caden.transformers.netflix.dtos;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class NetflixTransformationDto {
  private NetflixApiProfileInfo profileInfo;
  private List<NetflixApiViewedItem> viewedItems;

  public NetflixTransformationDto() {
    this.profileInfo = null;
    this.viewedItems = new ArrayList<>();
  }

  public NetflixApiProfileInfo getProfileInfo() {
    return this.profileInfo;
  }

  public void setProfileInfo(NetflixApiProfileInfo profileInfo) {
    this.profileInfo = profileInfo;
  }

  public List<NetflixApiViewedItem> getViewedItems() {
    return this.viewedItems;
  }

  public void setViewedItems(List<NetflixApiViewedItem> viewedItems) {
    this.viewedItems = viewedItems;
  }

  @JsonIgnore
  public boolean isEmpty() {
    return this.getProfileInfo() == null || this.getViewedItems() == null || this.getViewedItems().isEmpty();
  }
}
