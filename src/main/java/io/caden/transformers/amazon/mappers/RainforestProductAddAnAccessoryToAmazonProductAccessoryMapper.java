package io.caden.transformers.amazon.mappers;

import org.springframework.stereotype.Component;

import io.caden.transformers.amazon.dtos.rainforest.RainforestProductAddAnAccessoryDto;
import io.caden.transformers.amazon.entities.AmazonProductAccessory;
import io.caden.transformers.shared.mappers.Mapper;

@Component
public class RainforestProductAddAnAccessoryToAmazonProductAccessoryMapper extends Mapper<RainforestProductAddAnAccessoryDto, AmazonProductAccessory> {

  @Override
  public AmazonProductAccessory map(RainforestProductAddAnAccessoryDto value) {
    if (value == null) {
      return null;
    }

    AmazonProductAccessory amazonProductAccessory = new AmazonProductAccessory();
    amazonProductAccessory.setTitle(value.getTitle());
    amazonProductAccessory.setAsin(value.getAsin());

    if (value.getRainforestProductPrice() != null) {
      amazonProductAccessory.setPrice(value.getRainforestProductPrice().getValue());
    }

    return amazonProductAccessory;
  }

  @Override
  public RainforestProductAddAnAccessoryDto reverseMap(AmazonProductAccessory value) {
    throw new UnsupportedOperationException("Not implemented exception");
  }
}
