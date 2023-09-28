package io.caden.transformers.amazon.mappers;

import org.springframework.stereotype.Component;

import io.caden.transformers.amazon.dtos.rainforest.RainforestProductImageDto;
import io.caden.transformers.amazon.entities.AmazonProductImage;

import io.caden.transformers.shared.mappers.Mapper;

@Component
public class RainforestProductImageToAmazonProductImageMapper extends Mapper<RainforestProductImageDto, AmazonProductImage> {

  @Override
  public AmazonProductImage map(RainforestProductImageDto value) {
    if (value == null) {
      return null;
    }

    AmazonProductImage amazonProductImage = new AmazonProductImage();
    amazonProductImage.setVariant(value.getVariant());
    amazonProductImage.setLink(value.getLink());

    return amazonProductImage;
  }

  @Override
  public RainforestProductImageDto reverseMap(AmazonProductImage value) {
    throw new UnsupportedOperationException("Not implemented exception");
  }
}
