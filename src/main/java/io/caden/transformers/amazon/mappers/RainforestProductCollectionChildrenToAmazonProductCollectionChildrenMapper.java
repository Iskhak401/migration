package io.caden.transformers.amazon.mappers;

import org.springframework.stereotype.Component;

import io.caden.transformers.amazon.dtos.rainforest.RainforestProductCollectionChildrenDto;
import io.caden.transformers.amazon.entities.AmazonProductCollectionChildren;

import io.caden.transformers.shared.mappers.Mapper;

@Component
public class RainforestProductCollectionChildrenToAmazonProductCollectionChildrenMapper extends Mapper<RainforestProductCollectionChildrenDto, AmazonProductCollectionChildren> {

  @Override
  public AmazonProductCollectionChildren map(RainforestProductCollectionChildrenDto value) {
    if (value == null) {
      return null;
    }

    AmazonProductCollectionChildren amazonProductCollectionChildren = new AmazonProductCollectionChildren();
    amazonProductCollectionChildren.setTitle(value.getTitle());
    amazonProductCollectionChildren.setAsin(value.getAsin());
    amazonProductCollectionChildren.setLink(value.getLink());

    return amazonProductCollectionChildren;
  }

  @Override
  public RainforestProductCollectionChildrenDto reverseMap(AmazonProductCollectionChildren value) {
    throw new UnsupportedOperationException("Not implemented exception");
  }
}
