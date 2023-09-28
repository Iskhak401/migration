package io.caden.transformers.amazon.mappers;

import org.springframework.stereotype.Component;

import io.caden.transformers.amazon.dtos.rainforest.RainforestProductVideoDto;
import io.caden.transformers.amazon.entities.AmazonProductVideo;

import io.caden.transformers.shared.mappers.Mapper;

@Component
public class RainforestProductVideoToAmazonProductVideoMapper extends Mapper<RainforestProductVideoDto, AmazonProductVideo> {

  @Override
  public AmazonProductVideo map(RainforestProductVideoDto value) {
    if (value == null) {
      return null;
    }

    AmazonProductVideo amazonProductVideo = new AmazonProductVideo();
    amazonProductVideo.setTitle(value.getTitle());
    amazonProductVideo.setGroupId(value.getGroupId());
    amazonProductVideo.setGroupType(value.getGroupType());
    amazonProductVideo.setLink(value.getLink());
    amazonProductVideo.setDurationSeconds(value.getDurationSeconds());
    amazonProductVideo.setWidth(value.getWidth());
    amazonProductVideo.setHeight(value.getHeight());
    amazonProductVideo.setThumbnail(value.getThumbnail());
    amazonProductVideo.setHeroVideo(value.isHeroVideo());
    amazonProductVideo.setVariant(value.getVariant());

    return amazonProductVideo;
  }

  @Override
  public RainforestProductVideoDto reverseMap(AmazonProductVideo value) {
    throw new UnsupportedOperationException("Not implemented exception");
  }
}
