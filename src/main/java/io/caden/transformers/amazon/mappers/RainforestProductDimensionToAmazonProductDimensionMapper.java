package io.caden.transformers.amazon.mappers;

import org.springframework.stereotype.Component;

import io.caden.transformers.amazon.dtos.rainforest.RainforestProductDimensionDto;
import io.caden.transformers.amazon.entities.AmazonProductDimension;

import io.caden.transformers.shared.mappers.Mapper;

@Component
public class RainforestProductDimensionToAmazonProductDimensionMapper extends Mapper<RainforestProductDimensionDto, AmazonProductDimension> {

  @Override
  public AmazonProductDimension map(RainforestProductDimensionDto value) {
    if (value == null) {
      return null;
    }

    AmazonProductDimension amazonProductDimension = new AmazonProductDimension();
    amazonProductDimension.setName(value.getName());
    amazonProductDimension.setValue(value.getValue());

    return amazonProductDimension;
  }

  @Override
  public RainforestProductDimensionDto reverseMap(AmazonProductDimension value) {
    throw new UnsupportedOperationException("Not implemented exception");
  }
}
