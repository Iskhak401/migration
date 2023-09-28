package io.caden.transformers.amazon.mappers;

import org.springframework.stereotype.Component;

import io.caden.transformers.amazon.dtos.rainforest.RainforestProductEnergyEfficiencyDto;
import io.caden.transformers.amazon.entities.AmazonProductEnergyEfficiency;

import io.caden.transformers.shared.mappers.Mapper;

@Component
public class RainforestProductEnergyEfficiencyToAmazonProductEnergyEfficiencyMapper extends Mapper<RainforestProductEnergyEfficiencyDto, AmazonProductEnergyEfficiency> {

  @Override
  public AmazonProductEnergyEfficiency map(RainforestProductEnergyEfficiencyDto value) {
    if (value == null) {
      return null;
    }

    AmazonProductEnergyEfficiency amazonProductEnergyEfficiency = new AmazonProductEnergyEfficiency();
    amazonProductEnergyEfficiency.setRating(value.getRating());
    amazonProductEnergyEfficiency.setRatingImage(value.getRatingImage());
    amazonProductEnergyEfficiency.setProductFicheImage(value.getProductFicheImage());

    return amazonProductEnergyEfficiency;
  }

  @Override
  public RainforestProductEnergyEfficiencyDto reverseMap(AmazonProductEnergyEfficiency value) {
    throw new UnsupportedOperationException("Not implemented exception");
  }
}
