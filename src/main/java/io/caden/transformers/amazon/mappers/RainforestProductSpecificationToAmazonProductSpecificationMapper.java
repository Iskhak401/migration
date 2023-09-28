package io.caden.transformers.amazon.mappers;

import org.springframework.stereotype.Component;

import io.caden.transformers.amazon.dtos.rainforest.RainforestProductSpecificationDto;
import io.caden.transformers.amazon.entities.AmazonProductSpecification;

import io.caden.transformers.shared.mappers.Mapper;

@Component
public class RainforestProductSpecificationToAmazonProductSpecificationMapper extends Mapper<RainforestProductSpecificationDto, AmazonProductSpecification> {

  @Override
  public AmazonProductSpecification map(RainforestProductSpecificationDto value) {
    if (value == null) {
      return null;
    }

    AmazonProductSpecification amazonProductSpecification = new AmazonProductSpecification();
    amazonProductSpecification.setName(value.getName());
    amazonProductSpecification.setValue(value.getValue());

    return amazonProductSpecification;
  }

  @Override
  public RainforestProductSpecificationDto reverseMap(AmazonProductSpecification value) {
    throw new UnsupportedOperationException("Not implemented exception");
  }
}
