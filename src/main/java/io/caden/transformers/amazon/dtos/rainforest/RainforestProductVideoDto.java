package io.caden.transformers.amazon.dtos.rainforest;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RainforestProductVideoDto {
  private String title;
  @JsonProperty("group_id")
  private String groupId;
  @JsonProperty("group_type")
  private String groupType;
  private String link;
  @JsonProperty("duration_seconds")
  private Integer durationSeconds;
  private Integer width;
  private Integer height;
  private String thumbnail;
  @JsonProperty("is_hero_video")
  private Boolean heroVideo;
  private String variant;

  public String getTitle() {
    return this.title;
  }

  public void setTitle(final String title) {
    this.title = title;
  }

  public String getGroupId() {
    return this.groupId;
  }

  public void setGroupId(final String groupId) {
    this.groupId = groupId;
  }

  public String getGroupType() {
    return this.groupType;
  }

  public void setGroupType(final String groupType) {
    this.groupType = groupType;
  }

  public String getLink() {
    return this.link;
  }

  public void setLink(final String link) {
    this.link = link;
  }

  public Integer getDurationSeconds() {
    return this.durationSeconds;
  }

  public void setDurationSeconds(final Integer durationSeconds) {
    this.durationSeconds = durationSeconds;
  }

  public Integer getWidth() {
    return this.width;
  }

  public void setWidth(final Integer width) {
    this.width = width;
  }

  public Integer getHeight() {
    return this.height;
  }

  public void setHeight(final Integer height) {
    this.height = height;
  }

  public String getThumbnail() {
    return this.thumbnail;
  }

  public void setThumbnail(final String thumbnail) {
    this.thumbnail = thumbnail;
  }

  public Boolean isHeroVideo() {
    return this.heroVideo;
  }

  public void setHeroVideo(Boolean heroVideo) {
    this.heroVideo = heroVideo;
  }

  public String getVariant() {
    return this.variant;
  }

  public void setVariant(final String variant) {
    this.variant = variant;
  }
}
