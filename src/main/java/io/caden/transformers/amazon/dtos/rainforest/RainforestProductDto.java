package io.caden.transformers.amazon.dtos.rainforest;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.caden.transformers.shared.utils.RDFUtil;

public class RainforestProductDto {
  private String title;
  private String asin;
  private String link;
  private String image;
  private String description;
  private String searchAlias;
  @JsonProperty("promotions_feature")
  private String promotionsFeature;
  @JsonProperty("rich_product_description")
  private List<RainforestProductRichDescriptionDto> rainforestProductRichDescriptions;
  @JsonProperty("keywords_list")
  private List<String> keywords;
  @JsonProperty("authors")
  private List<RainforestProductAuthorDto> rainforestProductAuthors;
  private String format;
  private String runtime;
  @JsonProperty("energy_efficiency")
  private RainforestProductEnergyEfficiencyDto rainforestProductEnergyEfficiency;
  @JsonProperty("protection_plans")
  private List<RainforestProductProtectionPlanDto> rainforestProductProtectionPlans;
  @JsonProperty("add_an_accessory")
  private List<RainforestProductAddAnAccessoryDto> rainforestProductAddAnAccessories;
  private Boolean addOnItem;
  private Date firstAvailable;
  @JsonProperty("is_collection")
  private Boolean collection;
  @JsonProperty("collection_size")
  private Integer collectionSize;
  @JsonProperty("collection_children")
  private List<RainforestProductCollectionChildrenDto> rainforestProductCollectionChildrens;
  private String mainImage;
  @JsonProperty("images")
  private List<RainforestProductImageDto> rainforestProductImages;
  @JsonProperty("images_count")
  private Integer imagesCount;
  @JsonProperty("videos")
  private List<RainforestProductVideoDto> rainforestProductVideos;
  @JsonProperty("videos_count")
  private Integer videosCount;
  @JsonProperty("has_360_view")
  private Boolean has360View;
  @JsonProperty("bestsellers_rank")
  private List<RainforestProductBestsellersRankDto> rainforestProductBestsellersRanks;
  @JsonProperty("bestsellers_rank_flat")
  private String bestsellersRankFlat;
  @JsonProperty("feature_bullets")
  private List<String> featureBullets;
  @JsonProperty("feature_bullets_count")
  private Integer featureBulletsCount;
  @JsonProperty("model_number")
  private String modelNumber;
  @JsonProperty("feature_bullets_flat")
  private String featureBulletsFlat;
  @JsonProperty("sub_title")
  private RainforestProductSubTitleDto rainforestProductSubTitle;
  @JsonProperty("a_plus_content")
  private RainforestProductPlusContentDto rainforestProductPlusContent;
  @JsonProperty("attributes")
  private List<RainforestProductAttributeDto> rainforestProductAttributes;
  @JsonProperty("specifications")
  private List<RainforestProductSpecificationDto> rainforestProductSpecifications;
  @JsonProperty("specifications_flat")
  private String specificationsFlat;
  @JsonProperty("services")
  private List<RainforestProductServiceDto> rainforestProductServices;
  @JsonProperty("has_coupon")
  private Boolean coupon;
  @JsonProperty("coupon_text")
  private String couponText;
  @JsonProperty("sell_on_amazon")
  private Boolean sellOnAmazon;
  @JsonProperty("amazons_choice")
  private RainforestProductAmazonsChoiceDto rainforestProductAmazonsChoice;
  @JsonProperty("bestseller_badge")
  private RainforestProductBestsellerBadgeDto rainforestProductBestsellerBadge;
  @JsonProperty("kindle_unlimited")
  private Boolean kindleUnlimited;
  @JsonProperty("book_description")
  private String bookDescription;
  @JsonProperty("audible_sample")
  private String audibleSample;
  @JsonProperty("promotions")
  private List<RainforestProductPromotionDto> rainforestProductPromotions;
  @JsonProperty("categories")
  private List<RainforestProductCategoryDto> rainforestProductCategories;
  @JsonProperty("documents")
  private List<RainforestProductDocumentDto> rainforestProductDocuments;
  @JsonProperty("variants")
  private List<RainforestProductVariantDto> rainforestProductVariants;
  @JsonProperty("editorial_reviews")
  private List<RainforestProductEditorialReviewDto> rainforestProductEditorialReviews;
  private Double rating;
  @JsonProperty("ratings_total")
  private Integer ratingsTotal;
  private List<RainforestProductRatingBreakdownDto> rainforestProductRatingBreakdowns;
  @JsonProperty("reviews_total")
  private Integer reviewsTotal;
  @JsonProperty("is_bundle")
  private Boolean bundle;
  private String brand;
  @JsonProperty("asin_to_gtin")
  private String gtin;
  private String color;
  private String manufacturer;

  private String weight;
  private String dimensions;
  @JsonProperty("shipping_weight")
  private String shippingWeight;
  @JsonProperty("buybox_winner")
  private RainforestProductBuyboxWinnerDto rainforestProductBuyboxWinner;
  @JsonProperty("more_buying_choices")
  private List<RainforestProductMoreBuyingChoiceDto> rainforestProductMoreBuyingChoices;
  @JsonProperty("parent_asin")
  private String parentAsin;

  public String getTitle() {
    return this.title;
  }

  public void setTitle(final String title) {
    this.title = title;
  }

  public String getAsin() {
    return this.asin;
  }

  public void setAsin(final String asin) {
    this.asin = asin;
  }

  public String getLink() {
    return this.link;
  }

  public void setLink(final String link) {
    this.link = link;
  }

  public String getImage() {
    return this.image;
  }

  public void setImage(final String image) {
    this.image = image;
  }

  public String getDescription() {
    return this.description;
  }

  public void setDescription(final String description) {
    this.description = description;
  }

  public String getSearchAlias() {
    return this.searchAlias;
  }

  public void setSearchAlias(final String searchAlias) {
    this.searchAlias = searchAlias;
  }

  public String getPromotionsFeature() {
    return this.promotionsFeature;
  }

  public void setPromotionsFeature(final String promotionsFeature) {
    this.promotionsFeature = promotionsFeature;
  }

  public List<RainforestProductRichDescriptionDto> getRainforestProductRichDescriptions() {
    return this.rainforestProductRichDescriptions;
  }

  public void setRainforestProductRichDescriptions(final List<RainforestProductRichDescriptionDto> rainforestProductRichDescriptions) {
    this.rainforestProductRichDescriptions = rainforestProductRichDescriptions;
  }

  public List<String> getKeywords() {
    return this.keywords;
  }

  public void setKeywords(final List<String> keywords) {
    this.keywords = keywords;
  }

  public List<RainforestProductAuthorDto> getRainforestProductAuthors() {
    return this.rainforestProductAuthors;
  }

  public void setRainforestProductAuthors(final List<RainforestProductAuthorDto> rainforestProductAuthors) {
    this.rainforestProductAuthors = rainforestProductAuthors;
  }

  public String getFormat() {
    return this.format;
  }

  public void setFormat(final String format) {
    this.format = format;
  }

  public String getRuntime() {
    return this.runtime;
  }

  public void setRuntime(final String runtime) {
    this.runtime = runtime;
  }

  public RainforestProductEnergyEfficiencyDto getRainforestProductEnergyEfficiency() {
    return this.rainforestProductEnergyEfficiency;
  }

  public void setRainforestProductEnergyEfficiency(final RainforestProductEnergyEfficiencyDto rainforestProductEnergyEfficiency) {
    this.rainforestProductEnergyEfficiency = rainforestProductEnergyEfficiency;
  }

  public List<RainforestProductProtectionPlanDto> getRainforestProductProtectionPlans() {
    return this.rainforestProductProtectionPlans;
  }

  public void setRainforestProductProtectionPlans(final List<RainforestProductProtectionPlanDto> rainforestProductProtectionPlans) {
    this.rainforestProductProtectionPlans = rainforestProductProtectionPlans;
  }

  public List<RainforestProductAddAnAccessoryDto> getRainforestProductAddAnAccessories() {
    return this.rainforestProductAddAnAccessories;
  }

  public void setRainforestProductAddAnAccessories(final List<RainforestProductAddAnAccessoryDto> rainforestProductAddAnAccessories) {
    this.rainforestProductAddAnAccessories = rainforestProductAddAnAccessories;
  }

  public Boolean isAddOnItem() {
    return this.addOnItem;
  }

  public void setAddOnItem(final Boolean addOnItem) {
    this.addOnItem = addOnItem;
  }

  public Date getFirstAvailable() {
    return this.firstAvailable;
  }

  public void setFirstAvailable(final Date firstAvailable) {
    this.firstAvailable = firstAvailable;
  }

  public Boolean isCollection() {
    return this.collection;
  }

  public void setCollection(final Boolean collection) {
    this.collection = collection;
  }

  public Integer getCollectionSize() {
    return this.collectionSize;
  }

  public void setCollectionSize(final Integer collectionSize) {
    this.collectionSize = collectionSize;
  }

  public List<RainforestProductCollectionChildrenDto> getRainforestProductCollectionChildrens() {
    return this.rainforestProductCollectionChildrens;
  }

  public void setRainforestProductCollectionChildrens(final List<RainforestProductCollectionChildrenDto> rainforestProductCollectionChildrens) {
    this.rainforestProductCollectionChildrens = rainforestProductCollectionChildrens;
  }

  public String getMainImage() {
    return this.mainImage;
  }

  public void setMainImage(final String mainImage) {
    this.mainImage = mainImage;
  }

  public List<RainforestProductImageDto> getRainforestProductImages() {
    return this.rainforestProductImages;
  }

  public void setRainforestProductImages(final List<RainforestProductImageDto> rainforestProductImages) {
    this.rainforestProductImages = rainforestProductImages;
  }

  public Integer getImagesCount() {
      return this.imagesCount;
  }

  public void setImagesCount(final Integer imagesCount) {
    this.imagesCount = imagesCount;
  }

  public List<RainforestProductVideoDto> getRainforestProductVideos() {
    return this.rainforestProductVideos;
  }

  public void setRainforestProductVideos(final List<RainforestProductVideoDto> rainforestProductVideos) {
    this.rainforestProductVideos = rainforestProductVideos;
  }

  public Integer getVideosCount() {
    return this.videosCount;
  }

  public void setVideosCount(final Integer videosCount) {
    this.videosCount = videosCount;
  }

  public Boolean is360View() {
    return this.has360View;
  }

  public void set360View(final Boolean has360View) {
    this.has360View = has360View;
  }

  public List<RainforestProductBestsellersRankDto> getRainforestProductBestsellersRanks() {
    return this.rainforestProductBestsellersRanks;
  }

  public void setRainforestProductBestsellersRanks(final List<RainforestProductBestsellersRankDto> rainforestProductBestsellersRanks) {
    this.rainforestProductBestsellersRanks = rainforestProductBestsellersRanks;
  }

  public String getBestsellersRankFlat() {
    return this.bestsellersRankFlat;
  }

  public void setBestsellersRankFlat(final String bestsellersRankFlat) {
    this.bestsellersRankFlat = bestsellersRankFlat;
  }

  public List<String> getFeatureBullets() {
    return this.featureBullets;
  }

  public void setFeatureBullets(final List<String> featureBullets) {
    this.featureBullets = featureBullets;
  }

  public Integer getFeatureBulletsCount() {
    return this.featureBulletsCount;
  }

  public void setFeatureBulletsCount(final Integer featureBulletsCount) {
    this.featureBulletsCount = featureBulletsCount;
  }

  public String getModelNumber() {
    return this.modelNumber;
  }

  public void setModelNumber(final String modelNumber) {
    this.modelNumber = modelNumber;
  }

  public String getFeatureBulletsFlat() {
    return this.featureBulletsFlat;
  }

  public void setFeatureBulletsFlat(final String featureBulletsFlat) {
    this.featureBulletsFlat = featureBulletsFlat;
  }

  public RainforestProductSubTitleDto getRainforestProductSubTitle() {
    return this.rainforestProductSubTitle;
  }

  public void setRainforestProductSubTitle(final RainforestProductSubTitleDto rainforestProductSubTitle) {
    this.rainforestProductSubTitle = rainforestProductSubTitle;
  }

  public RainforestProductPlusContentDto getRainforestProductPlusContent() {
    return this.rainforestProductPlusContent;
  }

  public void setRainforestProductPlusContent(final RainforestProductPlusContentDto rainforestProductPlusContent) {
    this.rainforestProductPlusContent = rainforestProductPlusContent;
  }

  public List<RainforestProductAttributeDto> getRainforestProductAttributes() {
    return this.rainforestProductAttributes;
  }

  public void setRainforestProductAttributes(final List<RainforestProductAttributeDto> rainforestProductAttributes) {
    this.rainforestProductAttributes = rainforestProductAttributes;
  }

  public List<RainforestProductSpecificationDto> getRainforestProductSpecifications() {
    return this.rainforestProductSpecifications;
  }

  public void setRainforestProductSpecifications(final List<RainforestProductSpecificationDto> rainforestProductSpecifications) {
    this.rainforestProductSpecifications = rainforestProductSpecifications;
  }

  public String getSpecificationsFlat() {
    return this.specificationsFlat;
  }

  public void setSpecificationsFlat(final String specificationsFlat) {
    this.specificationsFlat = specificationsFlat;
  }

  public List<RainforestProductServiceDto> getRainforestProductServices() {
    return this.rainforestProductServices;
  }

  public void setRainforestProductServices(final List<RainforestProductServiceDto> rainforestProductServices) {
    this.rainforestProductServices = rainforestProductServices;
  }

  public Boolean isCoupon() {
    return this.coupon;
  }

  public void setCoupon(final Boolean coupon) {
    this.coupon = coupon;
  }

  public String getCouponText() {
    return this.couponText;
  }

  public void setCouponText(final String couponText) {
    this.couponText = couponText;
  }

  public Boolean isSellOnAmazon() {
    return this.sellOnAmazon;
  }

  public void setSellOnAmazon(final Boolean sellOnAmazon) {
    this.sellOnAmazon = sellOnAmazon;
  }

  public RainforestProductAmazonsChoiceDto getRainforestProductAmazonsChoice() {
    return this.rainforestProductAmazonsChoice;
  }

  public void setRainforestProductAmazonsChoice(final RainforestProductAmazonsChoiceDto rainforestProductAmazonsChoice) {
    this.rainforestProductAmazonsChoice = rainforestProductAmazonsChoice;
  }

  public RainforestProductBestsellerBadgeDto getRainforestProductBestsellerBadge() {
    return this.rainforestProductBestsellerBadge;
  }

  public void setRainforestProductBestsellerBadge(final RainforestProductBestsellerBadgeDto rainforestProductBestsellerBadge) {
    this.rainforestProductBestsellerBadge = rainforestProductBestsellerBadge;
  }

  public Boolean isKindleUnlimited() {
    return this.kindleUnlimited;
  }

  public void setKindleUnlimited(final Boolean kindleUnlimited) {
    this.kindleUnlimited = kindleUnlimited;
  }

  public String getBookDescription() {
    return this.bookDescription;
  }

  public void setBookDescription(final String bookDescription) {
    this.bookDescription = bookDescription;
  }

  public String getAudibleSample() {
    return this.audibleSample;
  }

  public void setAudibleSample(final String audibleSample) {
    this.audibleSample = audibleSample;
  }

  public List<RainforestProductPromotionDto> getRainforestProductPromotions() {
    return this.rainforestProductPromotions;
  }

  public void setRainforestProductPromotions(final List<RainforestProductPromotionDto> rainforestProductPromotions) {
    this.rainforestProductPromotions = rainforestProductPromotions;
  }

  public List<RainforestProductCategoryDto> getRainforestProductCategories() {
    return this.rainforestProductCategories;
  }

  public void setRainforestProductCategories(final List<RainforestProductCategoryDto> rainforestProductCategories) {
    this.rainforestProductCategories = rainforestProductCategories;
  }

  public List<RainforestProductDocumentDto> getRainforestProductDocuments() {
    return this.rainforestProductDocuments;
  }

  public void setRainforestProductDocuments(final List<RainforestProductDocumentDto> rainforestProductDocuments) {
    this.rainforestProductDocuments = rainforestProductDocuments;
  }

  public List<RainforestProductVariantDto> getRainforestProductVariants() {
    return this.rainforestProductVariants;
  }

  public void setRainforestProductVariants(final List<RainforestProductVariantDto> rainforestProductVariants) {
    this.rainforestProductVariants = rainforestProductVariants;
  }

  public List<RainforestProductEditorialReviewDto> getRainforestProductEditorialReviews() {
    return this.rainforestProductEditorialReviews;
  }

  public void setRainforestProductEditorialReviews(final List<RainforestProductEditorialReviewDto> rainforestProductEditorialReviews) {
    this.rainforestProductEditorialReviews = rainforestProductEditorialReviews;
  }

  public Double getRating() {
    return this.rating;
  }

  public void setRating(final Double rating) {
    this.rating = rating;
  }

  public Integer getRatingsTotal() {
    return this.ratingsTotal;
  }

  public void setRatingsTotal(final Integer ratingsTotal) {
    this.ratingsTotal = ratingsTotal;
  }

  public List<RainforestProductRatingBreakdownDto> getRainforestProductRatingBreakdowns() {
    return this.rainforestProductRatingBreakdowns;
  }

  public void setRainforestProductRatingBreakdowns(final List<RainforestProductRatingBreakdownDto> rainforestProductRatingBreakdowns) {
    this.rainforestProductRatingBreakdowns = rainforestProductRatingBreakdowns;
  }

  public Integer getReviewsTotal() {
    return this.reviewsTotal;
  }

  public void setReviewsTotal(final Integer reviewsTotal) {
    this.reviewsTotal = reviewsTotal;
  }

  public Boolean isBundle() {
    return this.bundle;
  }

  public void setBundle(final Boolean bundle) {
    this.bundle = bundle;
  }

  public String getBrand() {
    return this.brand;
  }

  public void setBrand(final String brand) {
    this.brand = brand;
  }

  public String getWeight() {
    return this.weight;
  }

  public void setWeight(final String weight) {
    this.weight = weight;
  }

  public String getDimensions() {
    return this.dimensions;
  }

  public void setDimensions(final String dimensions) {
    this.dimensions = dimensions;
  }

  public String getShippingWeight() {
    return this.shippingWeight;
  }

  public void setShippingWeight(final String shippingWeight) {
    this.shippingWeight = shippingWeight;
  }

  public RainforestProductBuyboxWinnerDto getRainforestProductBuyboxWinner() {
    return this.rainforestProductBuyboxWinner;
  }

  public void setRainforestProductBuyboxWinner(final RainforestProductBuyboxWinnerDto rainforestProductBuyboxWinner) {
    this.rainforestProductBuyboxWinner = rainforestProductBuyboxWinner;
  }

  public List<RainforestProductMoreBuyingChoiceDto> getRainforestProductMoreBuyingChoices() {
    return this.rainforestProductMoreBuyingChoices;
  }

  public void setRainforestProductMoreBuyingChoices(final List<RainforestProductMoreBuyingChoiceDto> rainforestProductMoreBuyingChoices) {
    this.rainforestProductMoreBuyingChoices = rainforestProductMoreBuyingChoices;
  }

  public String getGtin() {
    return gtin;
  }

  public void setGtin(final String gtin) {
    this.gtin = gtin;
  }

  public String getColor() {
    return color;
  }

  public void setColor(final String color) {
    this.color = color;
  }

  public String getManufacturer() {
    return manufacturer;
  }

  public void setManufacturer(final String manufacturer) {
    this.manufacturer = manufacturer;
  }

  public String getParentAsin() {
    return this.parentAsin;
  }

  public void setParentAsin(final String parentAsin) {
    this.parentAsin = parentAsin;
  }

  @JsonProperty("search_alias")
  private void unpackSearchAlias(Map<String, String> searchAlias) {
    this.searchAlias = searchAlias.get("title");
  }

  @JsonProperty("add_on_item")
  private void unpackAddOnItem(Map<String, String> addOnItem) {
    this.addOnItem = Boolean.parseBoolean(addOnItem.get("is_add_on_item"));
  }

  @SuppressWarnings("unchecked")
  @JsonProperty("first_available")
  private void unpackFirstAvailable(Object object) throws ParseException {
    String date = "";

    if (object instanceof String) {
      date = object.toString();
    } else if (object instanceof Map) {
      date = ((Map<String, String>) object).get("utc");
    }

    if (!date.isEmpty()) {
      this.firstAvailable = RDFUtil.getDate(date);
    }
  }

  @JsonProperty("main_image")
  private void unpackMainImage(Map<String, String> mainImage) {
    this.mainImage = mainImage.get("link");
  }

  @JsonProperty("rating_breakdown")
  private void unpackRatingBreakdowns(Map<String, Map<String, Integer>> ratingBreakdowns) {
    this.rainforestProductRatingBreakdowns = new ArrayList<>();

    for (Map.Entry<String, Map<String, Integer>> entry : ratingBreakdowns.entrySet()) {
      RainforestProductRatingBreakdownDto rainforestProductRatingBreakdown = new RainforestProductRatingBreakdownDto();
      rainforestProductRatingBreakdown.setName(entry.getKey());
      rainforestProductRatingBreakdown.setCount(entry.getValue().get("count"));
      rainforestProductRatingBreakdown.setPercentage(Double.valueOf(entry.getValue().get("percentage")));

      this.rainforestProductRatingBreakdowns.add(rainforestProductRatingBreakdown);
    }
  }
}
