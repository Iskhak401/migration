package io.caden.transformers.amazon.mappers;

import org.springframework.stereotype.Component;

import io.caden.transformers.amazon.dtos.rainforest.RainforestProductAuthorDto;
import io.caden.transformers.amazon.entities.AmazonProductAuthor;

import io.caden.transformers.shared.mappers.Mapper;

@Component
public class RainforestProductAuthorToAmazonProductAuthorMapper extends Mapper<RainforestProductAuthorDto, AmazonProductAuthor> {

  @Override
  public AmazonProductAuthor map(RainforestProductAuthorDto value) {
    if (value == null) {
      return null;
    }

    AmazonProductAuthor amazonProductAuthor = new AmazonProductAuthor();
    amazonProductAuthor.setName(value.getName());
    amazonProductAuthor.setLink(value.getLink());

    return amazonProductAuthor;
  }

  @Override
  public RainforestProductAuthorDto reverseMap(AmazonProductAuthor value) {
    throw new UnsupportedOperationException("Not implemented exception");
  }
}
