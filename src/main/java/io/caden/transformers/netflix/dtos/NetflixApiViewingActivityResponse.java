package io.caden.transformers.netflix.dtos;

import java.util.List;

public class NetflixApiViewingActivityResponse {
  private NetflixApiProfileInfo profileInfo;
  private List<NetflixApiViewedItem> viewedItems;

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
}
