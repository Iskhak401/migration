package io.caden.transformers.amazon.mappers;

import org.springframework.stereotype.Component;

import io.caden.transformers.amazon.dtos.rainforest.RainforestProductServiceDto;
import io.caden.transformers.amazon.entities.AmazonProductService;

import io.caden.transformers.shared.mappers.Mapper;

@Component
public class RainforestProductServiceToAmazonProductServiceMapper extends Mapper<RainforestProductServiceDto, AmazonProductService> {

  @Override
  public AmazonProductService map(RainforestProductServiceDto value) {
    if (value == null) {
      return null;
    }

    AmazonProductService amazonProductService = new AmazonProductService();
    amazonProductService.setTitle(value.getTitle());

    if (value.getWhatsIncluded() != null) {
      amazonProductService.setWhatsIncluded(String.join(", ", value.getWhatsIncluded()));
    }

    if (value.getRainforestProductPrice() != null) {
      amazonProductService.setPrice(value.getRainforestProductPrice().getValue());
    }

    return amazonProductService;
  }

  @Override
  public RainforestProductServiceDto reverseMap(AmazonProductService value) {
    throw new UnsupportedOperationException("Not implemented exception");
  }
}
