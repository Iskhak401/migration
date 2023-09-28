package io.caden.transformers.amazon.mappers;

import org.springframework.stereotype.Component;

import io.caden.transformers.amazon.dtos.rainforest.RainforestProductCategoryDto;
import io.caden.transformers.amazon.entities.AmazonProductCategory;

import io.caden.transformers.shared.mappers.Mapper;

@Component
public class RainforestProductCategoryToAmazonProductCategoryMapper extends Mapper<RainforestProductCategoryDto, AmazonProductCategory> {

  @Override
  public AmazonProductCategory map(RainforestProductCategoryDto value) {
    if (value == null) {
      return null;
    }

    AmazonProductCategory amazonProductCategory = new AmazonProductCategory();
    amazonProductCategory.setName(value.getName());
    amazonProductCategory.setLink(value.getLink());
    amazonProductCategory.setExternalId(value.getCategoryId());

    return amazonProductCategory;
  }

  @Override
  public RainforestProductCategoryDto reverseMap(AmazonProductCategory value) {
    throw new UnsupportedOperationException("Not implemented exception");
  }
}
