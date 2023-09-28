package io.caden.transformers.amazon.mappers;

import org.springframework.stereotype.Component;

import io.caden.transformers.amazon.dtos.rainforest.RainforestProductBuyboxWinnerDto;
import io.caden.transformers.amazon.entities.AmazonProductBuyboxWinner;

import io.caden.transformers.shared.mappers.Mapper;

@Component
public class RainforestProductBuyboxWinnerToAmazonProductBuyboxWinnerMapper extends Mapper<RainforestProductBuyboxWinnerDto, AmazonProductBuyboxWinner> {

  @Override
  public AmazonProductBuyboxWinner map(RainforestProductBuyboxWinnerDto value) {
    if (value == null) {
      return null;
    }

    AmazonProductBuyboxWinner amazonProductBuyboxWinner = new AmazonProductBuyboxWinner();
    amazonProductBuyboxWinner.setPrime(value.isPrime());
    amazonProductBuyboxWinner.setPriceOnlyAvailableInCart(value.isPriceOnlyAvailableInCart());
    amazonProductBuyboxWinner.setAmazonFresh(value.isAmazonFresh());
    amazonProductBuyboxWinner.setOfferId(value.getOfferId());
    amazonProductBuyboxWinner.setNewOffersCount(value.getNewOffersCount());
    amazonProductBuyboxWinner.setUsedOffersCount(value.getUsedOffersCount());
    amazonProductBuyboxWinner.setMixedOffersCount(value.getMixedOffersCount());
    amazonProductBuyboxWinner.setUnqualifiedBuyBox(value.isUnqualifiedBuyBox());

    if (value.getNewOffersFrom() != null) {
      amazonProductBuyboxWinner.setNewOffersFrom(value.getNewOffersFrom().getValue());
    }

    if (value.getUsedOffersFrom() != null) {
      amazonProductBuyboxWinner.setUsedOffersFrom(value.getUsedOffersFrom().getValue());
    }

    if (value.getMixedOffersFrom() != null) {
      amazonProductBuyboxWinner.setMixedOffersFrom(value.getMixedOffersFrom().getValue());
    }

    if (value.getRainforestProductPrice() != null) {
      amazonProductBuyboxWinner.setPrice(value.getRainforestProductPrice().getValue());
    }

    if (value.getRecommendedRetailPrice() != null) {
      amazonProductBuyboxWinner.setRecommendedRetailPrice(value.getRecommendedRetailPrice().getValue());
    }

    if (value.getUnitPrice() != null) {
      amazonProductBuyboxWinner.setUnitPrice(value.getUnitPrice().getValue());
    }

    if (value.getShippingPrice() != null) {
      amazonProductBuyboxWinner.setShippingPrice(value.getShippingPrice().getValue());
    }

    if (value.getFreeShippingMinimumSpend() != null) {
      amazonProductBuyboxWinner.setFreeShippingMinimumSpend(value.getFreeShippingMinimumSpend().getValue());
    }

    if (value.getSavePrice() != null) {
      amazonProductBuyboxWinner.setSavePrice(value.getSavePrice().getValue());
    }

    if (value.getRainforestProductAvailability() != null) {
      amazonProductBuyboxWinner.setAvailabilityType(value.getRainforestProductAvailability().getType());
      amazonProductBuyboxWinner.setAvailabilityRaw(value.getRainforestProductAvailability().getRaw());
      amazonProductBuyboxWinner.setAvailabilityDispatchDays(value.getRainforestProductAvailability().getDispatchDays());
      amazonProductBuyboxWinner.setAvailabilityStockLevel(value.getRainforestProductAvailability().getStockLevel());
    }

    return amazonProductBuyboxWinner;
  }

  @Override
  public RainforestProductBuyboxWinnerDto reverseMap(AmazonProductBuyboxWinner value) {
    throw new UnsupportedOperationException("Not implemented exception");
  }  
}
