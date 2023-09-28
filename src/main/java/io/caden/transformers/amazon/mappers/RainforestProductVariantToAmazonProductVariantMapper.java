package io.caden.transformers.amazon.mappers;

import io.caden.transformers.amazon.dtos.rainforest.RainforestProductVariantDto;
import io.caden.transformers.amazon.entities.AmazonProductVariant;
import io.caden.transformers.shared.mappers.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RainforestProductVariantToAmazonProductVariantMapper extends Mapper<RainforestProductVariantDto, AmazonProductVariant> {

  private RainforestProductDimensionToAmazonProductDimensionMapper dimensionMapper;

  public RainforestProductVariantToAmazonProductVariantMapper(
    @Autowired final RainforestProductDimensionToAmazonProductDimensionMapper dimensionMapper
  ) {
    this.dimensionMapper = dimensionMapper;
  }

  @Override
  public AmazonProductVariant map(RainforestProductVariantDto value) {
    if (value == null) {
      return null;
    }

    AmazonProductVariant amazonProductVariant = new AmazonProductVariant();
    amazonProductVariant.setTitle(value.getTitle());
    amazonProductVariant.setAsin(value.getAsin());
    amazonProductVariant.setLink(value.getLink());
    amazonProductVariant.setFormat(value.getFormat());
    amazonProductVariant.setCurrentProduct(value.isCurrentProduct());
    amazonProductVariant.setPriceOnlyAvailableInCart(value.isPriceOnlyAvailableInCart());
    amazonProductVariant.setMainImage(value.getMainImage());

    if (value.getRainforestProductPrice() != null) {
      amazonProductVariant.setPrice(value.getRainforestProductPrice().getValue());
    }


    return amazonProductVariant;
  }

  @Override
  public RainforestProductVariantDto reverseMap(AmazonProductVariant value) {
    throw new UnsupportedOperationException("Not implemented exception");
  }
}
