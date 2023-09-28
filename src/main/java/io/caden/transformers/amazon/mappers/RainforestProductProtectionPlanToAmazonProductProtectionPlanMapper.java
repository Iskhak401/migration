package io.caden.transformers.amazon.mappers;

import org.springframework.stereotype.Component;

import io.caden.transformers.amazon.dtos.rainforest.RainforestProductProtectionPlanDto;
import io.caden.transformers.amazon.entities.AmazonProductProtectionPlan;

import io.caden.transformers.shared.mappers.Mapper;

@Component
public class RainforestProductProtectionPlanToAmazonProductProtectionPlanMapper extends Mapper<RainforestProductProtectionPlanDto, AmazonProductProtectionPlan> {

  @Override
  public AmazonProductProtectionPlan map(RainforestProductProtectionPlanDto value) {
    if (value == null) {
      return null;
    }

    AmazonProductProtectionPlan amazonProductProtectionPlan = new AmazonProductProtectionPlan();
    amazonProductProtectionPlan.setTitle(value.getTitle());
    amazonProductProtectionPlan.setAsin(value.getAsin());

    if (value.getRainforestProductPrice() != null) {
      amazonProductProtectionPlan.setPrice(value.getRainforestProductPrice().getValue());
    }

    return amazonProductProtectionPlan;
  }

  @Override
  public RainforestProductProtectionPlanDto reverseMap(AmazonProductProtectionPlan value) {
    throw new UnsupportedOperationException("Not implemented exception");
  }
}
