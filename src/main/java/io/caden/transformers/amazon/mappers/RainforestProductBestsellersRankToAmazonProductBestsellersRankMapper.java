package io.caden.transformers.amazon.mappers;

import org.springframework.stereotype.Component;

import io.caden.transformers.amazon.dtos.rainforest.RainforestProductBestsellersRankDto;
import io.caden.transformers.amazon.entities.AmazonProductBestsellersRank;

import io.caden.transformers.shared.mappers.Mapper;

@Component
public class RainforestProductBestsellersRankToAmazonProductBestsellersRankMapper extends Mapper<RainforestProductBestsellersRankDto, AmazonProductBestsellersRank> {

  @Override
  public AmazonProductBestsellersRank map(RainforestProductBestsellersRankDto value) {
    if (value == null) {
      return null;
    }

    AmazonProductBestsellersRank amazonProductBestsellersRank = new AmazonProductBestsellersRank();
    amazonProductBestsellersRank.setRank(value.getRank());
    amazonProductBestsellersRank.setCategory(value.getCategory());
    amazonProductBestsellersRank.setLink(value.getLink());

    return amazonProductBestsellersRank;
  }

  @Override
  public RainforestProductBestsellersRankDto reverseMap(AmazonProductBestsellersRank value) {
    throw new UnsupportedOperationException("Not implemented exception");
  }
}
