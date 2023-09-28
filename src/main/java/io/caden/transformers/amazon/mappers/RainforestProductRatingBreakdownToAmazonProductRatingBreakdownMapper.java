package io.caden.transformers.amazon.mappers;

import org.springframework.stereotype.Component;

import io.caden.transformers.amazon.dtos.rainforest.RainforestProductRatingBreakdownDto;
import io.caden.transformers.amazon.entities.AmazonProductRatingBreakdown;

import io.caden.transformers.shared.mappers.Mapper;

@Component
public class RainforestProductRatingBreakdownToAmazonProductRatingBreakdownMapper extends Mapper<RainforestProductRatingBreakdownDto, AmazonProductRatingBreakdown> {

  @Override
  public AmazonProductRatingBreakdown map(RainforestProductRatingBreakdownDto value) {
    if (value == null) {
      return null;
    }

    AmazonProductRatingBreakdown amazonProductRatingBreakdown = new AmazonProductRatingBreakdown();
    amazonProductRatingBreakdown.setName(value.getName());
    amazonProductRatingBreakdown.setPercentage(value.getPercentage());
    amazonProductRatingBreakdown.setCount(value.getCount());

    return amazonProductRatingBreakdown;
  }

  @Override
  public RainforestProductRatingBreakdownDto reverseMap(AmazonProductRatingBreakdown value) {
    throw new UnsupportedOperationException("Not implemented exception");
  }
}
