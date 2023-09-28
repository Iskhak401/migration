package io.caden.transformers.amazon.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.databind.JsonNode;
import io.caden.transformers.shared.entities.RDFAbstractEntity;

import java.util.Date;
import java.util.Set;

public class AmazonProduct extends RDFAbstractEntity {

  private String title;
  private String asin;
  private String link;
  private String color;
  private String manufacturer;
  private String gtin;
  private String description;
  private String searchAlias;
  private String promotionsFeature;
  private String format;
  private String runtime;
  private Boolean addOnItem;
  private Date firstAvailable;
  private Boolean collection;
  private String mainImage;
  private Boolean has360View;
  private String modelNumber;
  private String subTitle;
  private Boolean coupon;
  private String couponText;
  private Boolean sellOnAmazon;
  private Boolean kindleUnlimited;
  private String bookDescription;
  private String audibleSample;
  private Double rating;
  private Integer ratingsTotal;
  private Integer reviewsTotal;
  private Boolean bundle;
  private String brand;
  private String weight;
  private String dimensions;
  private String shippingWeight;
  private String parentAsin;
  private Boolean plusContent;
  private Boolean brandStory;
  private Boolean thirdParty;
  private String companyLogo;
  private String companyDescriptionText;

  private JsonNode jsonData;

  private AmazonProductBuyboxWinner amazonProductBuyboxWinner;

  @JsonBackReference(value = "amazonProductKeywords")
  private Set<AmazonProductKeyword> amazonProductKeywords;

  @JsonBackReference(value = "amazonProductRichDescriptions")
  private Set<AmazonProductRichDescription> amazonProductRichDescriptions;

  @JsonBackReference(value = "amazonProductAuthors")
  private Set<AmazonProductAuthor> amazonProductAuthors;

  private AmazonProductEnergyEfficiency amazonProductEnergyEfficiency;

  @JsonBackReference(value = "amazonProductAccessories")
  private Set<AmazonProductAccessory> amazonProductAccessories;
  
  @JsonBackReference(value = "amazonProductCollectionChildrens")
  private Set<AmazonProductCollectionChildren> amazonProductCollectionChildrens;
  
  @JsonBackReference(value = "amazonProductImages")
  private Set<AmazonProductImage> amazonProductImages;
  
  @JsonBackReference(value = "amazonProductVideos")
  private Set<AmazonProductVideo> amazonProductVideos;
  
  @JsonBackReference(value = "amazonProductBestsellersRanks")
  private Set<AmazonProductBestsellersRank> amazonProductBestsellersRanks;
  
  @JsonBackReference(value = "amazonProductFeatureBullets")
  private Set<AmazonProductFeatureBullet> amazonProductFeatureBullets;

  @JsonBackReference(value = "amazonProductAttributes")
  private Set<AmazonProductAttribute> amazonProductAttributes;
  
  @JsonBackReference(value = "amazonProductSpecifications")
  private Set<AmazonProductSpecification> amazonProductSpecifications;
  
  @JsonBackReference(value = "amazonProductServices")
  private Set<AmazonProductService> amazonProductServices;
  
  @JsonBackReference(value = "amazonProductCategories")
  private Set<AmazonProductCategory> amazonProductCategories;
  
  @JsonBackReference(value = "amazonProductDocuments")
  private Set<AmazonProductDocument> amazonProductDocuments;
  
  @JsonBackReference(value = "amazonProductVariants")
  private Set<AmazonProductVariant> amazonProductVariants;

  @JsonBackReference(value = "amazonProductRatingBreakdowns")
  private Set<AmazonProductRatingBreakdown> amazonProductRatingBreakdowns;

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

  public Set<AmazonProductRichDescription> getAmazonProductRichDescriptions() {
    return this.amazonProductRichDescriptions;
  }

  public void setAmazonProductRichDescriptions(final Set<AmazonProductRichDescription> amazonProductRichDescriptions) {
    this.amazonProductRichDescriptions = amazonProductRichDescriptions;
  }

  public Set<AmazonProductKeyword> getAmazonProductKeywords() {
    return this.amazonProductKeywords;
  }

  public void setAmazonProductKeywords(final Set<AmazonProductKeyword> amazonProductKeywords) {
    this.amazonProductKeywords = amazonProductKeywords;
  }

  public Set<AmazonProductAuthor> getAmazonProductAuthors() {
    return this.amazonProductAuthors;
  }

  public void setAmazonProductAuthors(final Set<AmazonProductAuthor> amazonProductAuthors) {
    this.amazonProductAuthors = amazonProductAuthors;
  }

  public String getFormat() {
    return this.format;
  }

  public void setFormat(final String format) {
    this.format = format;
  }

  public JsonNode getJsonData() {
    return jsonData;
  }

  public void setJsonData(final JsonNode jsonData) {
    this.jsonData = jsonData;
  }

  public String getRuntime() {
    return this.runtime;
  }

  public void setRuntime(final String runtime) {
    this.runtime = runtime;
  }

  public AmazonProductEnergyEfficiency getAmazonProductEnergyEfficiency() {
    return this.amazonProductEnergyEfficiency;
  }

  public void setAmazonProductEnergyEfficiency(final AmazonProductEnergyEfficiency amazonProductEnergyEfficiency) {
    this.amazonProductEnergyEfficiency = amazonProductEnergyEfficiency;
  }

  public Set<AmazonProductAccessory> getAmazonProductAccessories() {
    return this.amazonProductAccessories;
  }

  public void setAmazonProductAccessories(final Set<AmazonProductAccessory> amazonProductAccessories) {
    this.amazonProductAccessories = amazonProductAccessories;
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

  public Set<AmazonProductCollectionChildren> getAmazonProductCollectionChildrens() {
    return this.amazonProductCollectionChildrens;
  }

  public void setAmazonProductCollectionChildrens(final Set<AmazonProductCollectionChildren> amazonProductCollectionChildrens) {
    this.amazonProductCollectionChildrens = amazonProductCollectionChildrens;
  }

  public String getMainImage() {
    return this.mainImage;
  }

  public void setMainImage(final String mainImage) {
    this.mainImage = mainImage;
  }

  public Set<AmazonProductImage> getAmazonProductImages() {
    return this.amazonProductImages;
  }

  public void setAmazonProductImages(final Set<AmazonProductImage> amazonProductImages) {
    this.amazonProductImages = amazonProductImages;
  }

  public Set<AmazonProductVideo> getAmazonProductVideos() {
    return this.amazonProductVideos;
  }

  public void setAmazonProductVideos(final Set<AmazonProductVideo> amazonProductVideos) {
    this.amazonProductVideos = amazonProductVideos;
  }

  public Boolean is360View() {
    return this.has360View;
  }

  public void set360View(final Boolean has360View) {
    this.has360View = has360View;
  }

  public Set<AmazonProductBestsellersRank> getAmazonProductBestsellersRanks() {
    return this.amazonProductBestsellersRanks;
  }

  public void setAmazonProductBestsellersRanks(final Set<AmazonProductBestsellersRank> amazonProductBestsellersRanks) {
    this.amazonProductBestsellersRanks = amazonProductBestsellersRanks;
  }

  public Set<AmazonProductFeatureBullet> getAmazonProductFeatureBullets() {
    return this.amazonProductFeatureBullets;
  }

  public void setAmazonProductFeatureBullets(final Set<AmazonProductFeatureBullet> amazonProductFeatureBullets) {
    this.amazonProductFeatureBullets = amazonProductFeatureBullets;
  }

  public String getModelNumber() {
    return this.modelNumber;
  }

  public void setModelNumber(final String modelNumber) {
    this.modelNumber = modelNumber;
  }

  public Boolean isPlusContent() {
    return this.plusContent;
  }

  public void setPlusContent(final Boolean plusContent) {
    this.plusContent = plusContent;
  }

  public Boolean isBrandStory() {
    return this.brandStory;
  }

  public void setBrandStory(final Boolean brandStory) {
    this.brandStory = brandStory;
  }

  public Boolean isThirdParty() {
    return this.thirdParty;
  }

  public void setThirdParty(final Boolean thirdParty) {
    this.thirdParty = thirdParty;
  }

  public String getCompanyLogo() {
    return this.companyLogo;
  }

  public void setCompanyLogo(final String companyLogo) {
    this.companyLogo = companyLogo;
  }

  public String getCompanyDescriptionText() {
    return this.companyDescriptionText;
  }

  public void setCompanyDescriptionText(final String companyDescriptionText) {
    this.companyDescriptionText = companyDescriptionText;
  }

  public Set<AmazonProductAttribute> getAmazonProductAttributes() {
    return this.amazonProductAttributes;
  }

  public void setAmazonProductAttributes(final Set<AmazonProductAttribute> amazonProductAttributes) {
    this.amazonProductAttributes = amazonProductAttributes;
  }

  public Set<AmazonProductSpecification> getAmazonProductSpecifications() {
    return this.amazonProductSpecifications;
  }

  public void setAmazonProductSpecifications(final Set<AmazonProductSpecification> amazonProductSpecifications) {
    this.amazonProductSpecifications = amazonProductSpecifications;
  }

  public Set<AmazonProductService> getAmazonProductServices() {
    return this.amazonProductServices;
  }

  public void setAmazonProductServices(final Set<AmazonProductService> amazonProductServices) {
    this.amazonProductServices = amazonProductServices;
  }

  public String getSubTitle() {
    return this.subTitle;
  }

  public void setSubTitle(final String subTitle) {
    this.subTitle = subTitle;
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

  public Set<AmazonProductCategory> getAmazonProductCategories() {
    return this.amazonProductCategories;
  }

  public void setAmazonProductCategories(final Set<AmazonProductCategory> amazonProductCategories) {
    this.amazonProductCategories = amazonProductCategories;
  }

  public Set<AmazonProductDocument> getAmazonProductDocuments() {
    return this.amazonProductDocuments;
  }

  public void setAmazonProductDocuments(final Set<AmazonProductDocument> amazonProductDocuments) {
    this.amazonProductDocuments = amazonProductDocuments;
  }

  public Set<AmazonProductVariant> getAmazonProductVariants() {
    return this.amazonProductVariants;
  }

  public void setAmazonProductVariants(final Set<AmazonProductVariant> amazonProductVariants) {
    this.amazonProductVariants = amazonProductVariants;
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

  public Set<AmazonProductRatingBreakdown> getAmazonProductRatingBreakdowns() {
    return this.amazonProductRatingBreakdowns;
  }

  public void setAmazonProductRatingBreakdowns(final Set<AmazonProductRatingBreakdown> amazonProductRatingBreakdowns) {
    this.amazonProductRatingBreakdowns = amazonProductRatingBreakdowns;
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

  public AmazonProductBuyboxWinner getAmazonProductBuyboxWinner() {
    return this.amazonProductBuyboxWinner;
  }

  public void setAmazonProductBuyboxWinner(final AmazonProductBuyboxWinner amazonProductBuyboxWinner) {
    this.amazonProductBuyboxWinner = amazonProductBuyboxWinner;
  }

  public String getParentAsin() {
    return this.parentAsin;
  }

  public void setParentAsin(final String parentAsin) {
    this.parentAsin = parentAsin;
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

  public String getGtin() {
    return gtin;
  }

  public void setGtin(final String gtin) {
    this.gtin = gtin;
  }
}
