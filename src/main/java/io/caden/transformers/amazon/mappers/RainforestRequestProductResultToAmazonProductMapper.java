package io.caden.transformers.amazon.mappers;

import io.caden.transformers.amazon.dtos.rainforest.RainforestProductDto;
import io.caden.transformers.amazon.dtos.rainforest.RainforestRequestProductResultDto;
import io.caden.transformers.amazon.entities.AmazonProduct;
import io.caden.transformers.shared.mappers.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RainforestRequestProductResultToAmazonProductMapper extends Mapper<RainforestRequestProductResultDto, AmazonProduct> {

  private RainforestProductBuyboxWinnerToAmazonProductBuyboxWinnerMapper buyboxWinnerMapper;

  private RainforestProductRichDescriptionToAmazonProductRichDescriptionMapper richDescriptionMapper;

  private RainforestProductAuthorToAmazonProductAuthorMapper authorMapper;

  private RainforestProductEnergyEfficiencyToAmazonProductEnergyEfficiencyMapper energyEfficiencyMapper;

  private RainforestProductProtectionPlanToAmazonProductProtectionPlanMapper protectionPlanMapper;

  private RainforestProductAddAnAccessoryToAmazonProductAccessoryMapper accessoryMapper;

  private RainforestProductCollectionChildrenToAmazonProductCollectionChildrenMapper collectionChildrenMapper;

  private RainforestProductImageToAmazonProductImageMapper imageMapper;

  private RainforestProductVideoToAmazonProductVideoMapper videoMapper;

  private RainforestProductBestsellersRankToAmazonProductBestsellersRankMapper bestsellersRankMapper;

  private RainforestProductAttributeToAmazonProductAttributeMapper attributeMapper;

  private RainforestProductSpecificationToAmazonProductSpecificationMapper specificationMapper;

  private RainforestProductServiceToAmazonProductServiceMapper serviceMapper;

  private RainforestProductDocumentToAmazonProductDocumentMapper documentMapper;

  private RainforestProductVariantToAmazonProductVariantMapper variantMapper;

  private RainforestProductRatingBreakdownToAmazonProductRatingBreakdownMapper ratingBreakdownMapper;

  private RainforestProductCategoryToAmazonProductCategoryMapper categoryMapper;

  public RainforestRequestProductResultToAmazonProductMapper(
    @Autowired final RainforestProductBuyboxWinnerToAmazonProductBuyboxWinnerMapper buyboxWinnerMapper,
    @Autowired final RainforestProductRichDescriptionToAmazonProductRichDescriptionMapper richDescriptionMapper,
    @Autowired final RainforestProductAuthorToAmazonProductAuthorMapper authorMapper,
    @Autowired final RainforestProductEnergyEfficiencyToAmazonProductEnergyEfficiencyMapper energyEfficiencyMapper,
    @Autowired final RainforestProductProtectionPlanToAmazonProductProtectionPlanMapper protectionPlanMapper,
    @Autowired final RainforestProductAddAnAccessoryToAmazonProductAccessoryMapper accessoryMapper,
    @Autowired final RainforestProductCollectionChildrenToAmazonProductCollectionChildrenMapper collectionChildrenMapper,
    @Autowired final RainforestProductImageToAmazonProductImageMapper imageMapper,
    @Autowired final RainforestProductVideoToAmazonProductVideoMapper videoMapper,
    @Autowired final RainforestProductBestsellersRankToAmazonProductBestsellersRankMapper bestsellersRankMapper,
    @Autowired final RainforestProductAttributeToAmazonProductAttributeMapper attributeMapper,
    @Autowired final RainforestProductSpecificationToAmazonProductSpecificationMapper specificationMapper,
    @Autowired final RainforestProductServiceToAmazonProductServiceMapper serviceMapper,
    @Autowired final RainforestProductDocumentToAmazonProductDocumentMapper documentMapper,
    @Autowired final RainforestProductVariantToAmazonProductVariantMapper variantMapper,
    @Autowired final RainforestProductRatingBreakdownToAmazonProductRatingBreakdownMapper ratingBreakdownMapper,
    @Autowired final RainforestProductCategoryToAmazonProductCategoryMapper categoryMapper
  ) {
    this.buyboxWinnerMapper = buyboxWinnerMapper;
    this.richDescriptionMapper = richDescriptionMapper;
    this.authorMapper = authorMapper;
    this.energyEfficiencyMapper = energyEfficiencyMapper;
    this.protectionPlanMapper = protectionPlanMapper;
    this.accessoryMapper = accessoryMapper;
    this.collectionChildrenMapper = collectionChildrenMapper;
    this.imageMapper = imageMapper;
    this.videoMapper = videoMapper;
    this.bestsellersRankMapper = bestsellersRankMapper;
    this.attributeMapper = attributeMapper;
    this.specificationMapper = specificationMapper;
    this.serviceMapper = serviceMapper;
    this.documentMapper = documentMapper;
    this.variantMapper = variantMapper;
    this.ratingBreakdownMapper = ratingBreakdownMapper;
    this.categoryMapper = categoryMapper;
  }

  @Override
  public AmazonProduct map(RainforestRequestProductResultDto rainforestRequestProductResult) {
    if (rainforestRequestProductResult == null || rainforestRequestProductResult.getRainforestProduct() == null) {
      return null;
    }

    RainforestProductDto rainforestProduct = rainforestRequestProductResult.getRainforestProduct();

    AmazonProduct amazonProduct = new AmazonProduct();
    amazonProduct.setJsonData(rainforestRequestProductResult.getJsonData());
    amazonProduct.setTitle(rainforestProduct.getTitle());
    amazonProduct.setAsin(rainforestProduct.getAsin());
    amazonProduct.setLink(rainforestProduct.getLink());
    amazonProduct.setDescription(rainforestProduct.getDescription());
    amazonProduct.setSearchAlias(rainforestProduct.getSearchAlias());
    amazonProduct.setPromotionsFeature(rainforestProduct.getPromotionsFeature());
    amazonProduct.setFormat(rainforestProduct.getFormat());
    amazonProduct.setRuntime(rainforestProduct.getRuntime());
    amazonProduct.setAddOnItem(rainforestProduct.isAddOnItem());
    amazonProduct.setFirstAvailable(rainforestProduct.getFirstAvailable());
    amazonProduct.setCollection(rainforestProduct.isCollection());
    amazonProduct.setMainImage(rainforestProduct.getMainImage());
    amazonProduct.set360View(rainforestProduct.is360View());
    amazonProduct.setModelNumber(rainforestProduct.getModelNumber());
    amazonProduct.setCoupon(rainforestProduct.isCoupon());
    amazonProduct.setCouponText(rainforestProduct.getCouponText());
    amazonProduct.setSellOnAmazon(rainforestProduct.isSellOnAmazon());
    amazonProduct.setKindleUnlimited(rainforestProduct.isKindleUnlimited());
    amazonProduct.setBookDescription(rainforestProduct.getBookDescription());
    amazonProduct.setAudibleSample(rainforestProduct.getAudibleSample());
    amazonProduct.setRating(rainforestProduct.getRating());
    amazonProduct.setRatingsTotal(rainforestProduct.getRatingsTotal());
    amazonProduct.setReviewsTotal(rainforestProduct.getReviewsTotal());
    amazonProduct.setBundle(rainforestProduct.isBundle());
    amazonProduct.setBrand(rainforestProduct.getBrand());
    amazonProduct.setWeight(rainforestProduct.getWeight());
    amazonProduct.setDimensions(rainforestProduct.getDimensions());
    amazonProduct.setShippingWeight(rainforestProduct.getShippingWeight());
    amazonProduct.setGtin(rainforestProduct.getGtin());
    amazonProduct.setColor(rainforestProduct.getColor());
    amazonProduct.setManufacturer(rainforestProduct.getManufacturer());
    amazonProduct.setParentAsin(rainforestProduct.getParentAsin());

    if (rainforestProduct.getRainforestProductPlusContent() != null) {
      amazonProduct.setPlusContent(rainforestProduct.getRainforestProductPlusContent().isPlusContent());
      amazonProduct.setBrandStory(rainforestProduct.getRainforestProductPlusContent().isBrandStory());
      amazonProduct.setThirdParty(rainforestProduct.getRainforestProductPlusContent().isThirdParty());
      amazonProduct.setCompanyLogo(rainforestProduct.getRainforestProductPlusContent().getCompanyLogo());
      amazonProduct.setCompanyDescriptionText(rainforestProduct.getRainforestProductPlusContent().getCompanyDescriptionText());
    }

    if (rainforestProduct.getRainforestProductSubTitle() != null) {
      amazonProduct.setSubTitle(rainforestProduct.getRainforestProductSubTitle().getText());
    }

    amazonProduct.setAmazonProductBuyboxWinner(
      this.buyboxWinnerMapper.map(rainforestProduct.getRainforestProductBuyboxWinner())  
    );


    amazonProduct.setAmazonProductEnergyEfficiency(
      this.energyEfficiencyMapper.map(rainforestProduct.getRainforestProductEnergyEfficiency())
    );
    return amazonProduct;
  }

  @Override
  public RainforestRequestProductResultDto reverseMap(AmazonProduct value) {
    throw new UnsupportedOperationException("Not implemented exception");
  }
}
