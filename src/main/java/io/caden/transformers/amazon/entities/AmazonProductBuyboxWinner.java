package io.caden.transformers.amazon.entities;

import io.caden.transformers.shared.entities.RDFAbstractEntity;
import lombok.Data;

@Data
public class AmazonProductBuyboxWinner extends RDFAbstractEntity {

  private Boolean prime;
  private Boolean priceOnlyAvailableInCart;
  private Boolean amazonFresh;
  private String offerId;
  private Integer newOffersCount;
  private Double newOffersFrom;
  private Integer usedOffersCount;
  private Double usedOffersFrom;
  private Integer mixedOffersCount;
  private Double mixedOffersFrom;
  private Double price;
  private Double recommendedRetailPrice;
  private Double unitPrice;
  private Double shippingPrice;
  private Double freeShippingMinimumSpend;
  private Double savePrice;
  private Boolean unqualifiedBuyBox;
  private String availabilityType;
  private String availabilityRaw;
  private Integer availabilityDispatchDays;
  private Integer availabilityStockLevel;

}
