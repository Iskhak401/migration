package io.caden.transformers.mx.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class MxApiTransactionDto {
  private String category;
  private String date;
  private String currencyCode;
  private Double amount;
  private String merchantGuid;
  private Integer merchantCategoryCode;
  private String merchantLocationGuid;
  private String description;
  private String categoryGuid;
  private String guid;
  private String transactionId;
  private Boolean isBillPay;
  private Boolean isDirectDeposit;
  private Boolean isExpense;
  private Boolean isFee;
  private Boolean isIncome;
  private Boolean isInternational;
  private Boolean isOverdraftFee;
  private Boolean isPayrollAdvance;
  private Boolean isRecurring;
  private Boolean isSubscription;
  private String topLevelCategory;
  private String transactedAt;
  private Double latitude;
  private Double longitude;
  private String type;
  private String accountGuid;
  private String userGuid;

  // Not provided by MX
  private MxApiMerchantDto merchant;
  private MxApiMerchantLocationDto merchantLocation;
  private MxApiAccountDto account;

  public String getCategory() {
    return this.category;
  }
  public void setCategory(String category) {
    this.category = category;
  }
  public String getDate() {
    return this.date;
  }
  public void setDate(String date) {
    this.date = date;
  }
  public String getCurrencyCode() {
    return this.currencyCode;
  }
  public void setCurrencyCode(String currencyCode) {
    this.currencyCode = currencyCode;
  }
  public Double getAmount() {
    return this.amount;
  }
  public void setAmount(Double amount) {
    this.amount = amount;
  }
  public String getMerchantGuid() {
    return this.merchantGuid;
  }
  public void setMerchantGuid(String merchantGuid) {
    this.merchantGuid = merchantGuid;
  }
  public Integer getMerchantCategoryCode() {
    return this.merchantCategoryCode;
  }
  public void setMerchantCategoryCode(Integer merchantCategoryCode) {
    this.merchantCategoryCode = merchantCategoryCode;
  }
  public String getMerchantLocationGuid() {
    return this.merchantLocationGuid;
  }
  public void setMerchantLocationGuid(String merchantLocationGuid) {
    this.merchantLocationGuid = merchantLocationGuid;
  }
  public String getDescription() {
    return this.description;
  }
  public void setDescription(String description) {
    this.description = description;
  }
  public String getCategoryGuid() {
    return this.categoryGuid;
  }
  public void setCategoryGuid(String categoryGuid) {
    this.categoryGuid = categoryGuid;
  }
  public String getGuid() {
    return this.guid;
  }
  public void setGuid(String guid) {
    this.guid = guid;
  }
  public String getTransactionId() {
    return this.transactionId;
  }
  public void setTransactionId(String transactionId) {
    this.transactionId = transactionId;
  }
  public Boolean getIsBillPay() {
    return this.isBillPay;
  }
  public void setIsBillPay(Boolean isBillPay) {
    this.isBillPay = isBillPay;
  }
  public Boolean getIsDirectDeposit() {
    return this.isDirectDeposit;
  }
  public void setIsDirectDeposit(Boolean isDirectDeposit) {
    this.isDirectDeposit = isDirectDeposit;
  }
  public Boolean getIsExpense() {
    return this.isExpense;
  }
  public void setIsExpense(Boolean isExpense) {
    this.isExpense = isExpense;
  }
  public Boolean getIsFee() {
    return this.isFee;
  }
  public void setIsFee(Boolean isFee) {
    this.isFee = isFee;
  }
  public Boolean getIsIncome() {
    return this.isIncome;
  }
  public void setIsIncome(Boolean isIncome) {
    this.isIncome = isIncome;
  }
  public Boolean getIsInternational() {
    return this.isInternational;
  }
  public void setIsInternational(Boolean isInternational) {
    this.isInternational = isInternational;
  }
  public Boolean getIsOverdraftFee() {
    return this.isOverdraftFee;
  }
  public void setIsOverdraftFee(Boolean isOverdraftFee) {
    this.isOverdraftFee = isOverdraftFee;
  }
  public Boolean getIsPayrollAdvance() {
    return this.isPayrollAdvance;
  }
  public void setIsPayrollAdvance(Boolean isPayrollAdvance) {
    this.isPayrollAdvance = isPayrollAdvance;
  }
  public Boolean getIsRecurring() {
    return this.isRecurring;
  }
  public void setIsRecurring(Boolean isRecurring) {
    this.isRecurring = isRecurring;
  }
  public Boolean getIsSubscription() {
    return this.isSubscription;
  }
  public void setIsSubscription(Boolean isSubscription) {
    this.isSubscription = isSubscription;
  }
  public String getTopLevelCategory() {
    return this.topLevelCategory;
  }
  public void setTopLevelCategory(String topLevelCategory) {
    this.topLevelCategory = topLevelCategory;
  }
  public String getTransactedAt() {
    return this.transactedAt;
  }
  public void setTransactedAt(String transactedAt) {
    this.transactedAt = transactedAt;
  }
  public Double getLatitude() {
    return this.latitude;
  }
  public void setLatitude(Double latitude) {
    this.latitude = latitude;
  }
  public Double getLongitude() {
    return this.longitude;
  }
  public void setLongitude(Double longitude) {
    this.longitude = longitude;
  }
  public String getType() {
    return this.type;
  }
  public void setType(String type) {
    this.type = type;
  }
  public String getAccountGuid() {
    return this.accountGuid;
  }
  public void setAccountGuid(String accountGuid) {
    this.accountGuid = accountGuid;
  }
  public String getUserGuid() {
    return this.userGuid;
  }
  public void setUserGuid(String userGuid) {
    this.userGuid = userGuid;
  }
  public MxApiMerchantDto getMerchant() {
    return this.merchant;
  }
  public void setMerchant(MxApiMerchantDto merchant) {
    this.merchant = merchant;
  }
  public MxApiMerchantLocationDto getMerchantLocation() {
    return this.merchantLocation;
  }
  public void setMerchantLocation(MxApiMerchantLocationDto merchantLocation) {
    this.merchantLocation = merchantLocation;
  }
  public MxApiAccountDto getAccount() {
    return this.account;
  }
  public void setAccount(MxApiAccountDto account) {
    this.account = account;
  }

  // Utilities
  @JsonIgnore
  public String getLatitudeString() {
    return this.latitude != null ? this.latitude.toString() : "";
  }
  @JsonIgnore
  public String getLongitudeString() {
    return this.longitude != null ? this.longitude.toString() : "";
  }
  @JsonIgnore
  public String getMerchantGuidString() {
    return this.merchantGuid != null ? this.merchantGuid : "";
  }
  @JsonIgnore
  public String getMerchantLocationGuidString() {
    return this.merchantLocationGuid != null ? this.merchantLocationGuid : "";
  }
  @JsonIgnore
  public String getMerchantCategoryCodeString() {
    return this.merchantCategoryCode != null ? this.merchantCategoryCode.toString() : null;
  }
}
