package io.caden.transformers.amazon.mappers;

import org.springframework.stereotype.Component;

import io.caden.transformers.amazon.dtos.rainforest.RainforestProductRichDescriptionDto;
import io.caden.transformers.amazon.entities.AmazonProductRichDescription;

import io.caden.transformers.shared.mappers.Mapper;

@Component
public class RainforestProductRichDescriptionToAmazonProductRichDescriptionMapper extends Mapper<RainforestProductRichDescriptionDto, AmazonProductRichDescription> {

  @Override
  public AmazonProductRichDescription map(RainforestProductRichDescriptionDto value) {
    if (value == null) {
      return null;
    }

    AmazonProductRichDescription amazonProductRichDescription = new AmazonProductRichDescription();
    amazonProductRichDescription.setTitle(value.getTitle());
    amazonProductRichDescription.setDescription(value.getDescription());

    return amazonProductRichDescription;
  }

  @Override
  public RainforestProductRichDescriptionDto reverseMap(AmazonProductRichDescription value) {
    throw new UnsupportedOperationException("Not implemented exception");
  }
}
