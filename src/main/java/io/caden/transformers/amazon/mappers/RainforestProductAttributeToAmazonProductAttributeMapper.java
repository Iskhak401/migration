package io.caden.transformers.amazon.mappers;

import org.springframework.stereotype.Component;

import io.caden.transformers.amazon.dtos.rainforest.RainforestProductAttributeDto;
import io.caden.transformers.amazon.entities.AmazonProductAttribute;
import io.caden.transformers.shared.mappers.Mapper;

@Component
public class RainforestProductAttributeToAmazonProductAttributeMapper extends Mapper<RainforestProductAttributeDto, AmazonProductAttribute> {

  @Override
  public AmazonProductAttribute map(RainforestProductAttributeDto value) {
    if (value == null) {
      return null;
    }

    AmazonProductAttribute amazonProductAttribute = new AmazonProductAttribute();
    amazonProductAttribute.setName(value.getName());
    amazonProductAttribute.setValue(value.getValue());

    return amazonProductAttribute;
  }

  @Override
  public RainforestProductAttributeDto reverseMap(AmazonProductAttribute value) {
    throw new UnsupportedOperationException("Not implemented exception");
  }
}
