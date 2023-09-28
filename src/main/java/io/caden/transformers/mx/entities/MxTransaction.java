package io.caden.transformers.mx.entities;

import java.util.Date;

import io.caden.transformers.shared.entities.RDFAbstractEntity;

public class MxTransaction extends RDFAbstractEntity {
  private String cadenAlias;
  private String category;
  private Date orderDate;
  private String priceCurrency;
  private Double price;
  private String seller;
  private String description;
  private String categoryGuid;
  private String mxGuid;
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
  private String accountGuid;
  private Double latitude;
  private Double longitude;
  private String type;
  private Date transformationDate;
  private Date instanceTimestamp;
  private MxMerchant mxMerchant;
  private MxMerchantLocation mxMerchantLocation;
  private MxAccount mxAccount;

  public String getCadenAlias() {
    return this.cadenAlias;
  }

  public void setCadenAlias(final String cadenAlias) {
    this.cadenAlias = cadenAlias;
  }

  public String getCategory() {
    return this.category;
  }

  public void setCategory(final String category) {
    this.category = category;
  }

  public Date getOrderDate() {
    return this.orderDate;
  }

  public void setOrderDate(final Date orderDate) {
    this.orderDate = orderDate;
  }

  public String getPriceCurrency() {
    return this.priceCurrency;
  }

  public void setPriceCurrency(final String priceCurrency) {
    this.priceCurrency = priceCurrency;
  }

  public Double getPrice() {
    return this.price;
  }

  public void setPrice(final Double price) {
    this.price = price;
  }

  public String getSeller() {
    return this.seller;
  }

  public void setSeller(final String seller) {
    this.seller = seller;
  }

  public String getDescription() {
    return this.description;
  }

  public void setDescription(final String description) {
    this.description = description;
  }

  public String getCategoryGuid() {
    return this.categoryGuid;
  }

  public void setCategoryGuid(final String categoryGuid) {
    this.categoryGuid = categoryGuid;
  }

  public String getMxGuid() {
    return this.mxGuid;
  }

  public void setMxGuid(final String mxGuid) {
    this.mxGuid = mxGuid;
  }

  public String getTransactionId() {
    return this.transactionId;
  }

  public void setTransactionId(final String transactionId) {
    this.transactionId = transactionId;
  }

  public Boolean getIsBillPay() {
    return this.isBillPay;
  }

  public void setIsBillPay(final Boolean isBillPay) {
    this.isBillPay = isBillPay;
  }

  public Boolean getIsDirectDeposit() {
    return this.isDirectDeposit;
  }

  public void setIsDirectDeposit(final Boolean isDirectDeposit) {
    this.isDirectDeposit = isDirectDeposit;
  }

  public Boolean getIsExpense() {
    return this.isExpense;
  }

  public void setIsExpense(final Boolean isExpense) {
    this.isExpense = isExpense;
  }

  public Boolean getIsFee() {
    return this.isFee;
  }

  public void setIsFee(final Boolean isFee) {
    this.isFee = isFee;
  }

  public Boolean getIsIncome() {
    return this.isIncome;
  }

  public void setIsIncome(final Boolean isIncome) {
    this.isIncome = isIncome;
  }

  public Boolean getIsInternational() {
    return this.isInternational;
  }

  public void setIsInternational(final Boolean isInternational) {
    this.isInternational = isInternational;
  }

  public Boolean getIsOverdraftFee() {
    return this.isOverdraftFee;
  }

  public void setIsOverdraftFee(final Boolean isOverdraftFee) {
    this.isOverdraftFee = isOverdraftFee;
  }

  public Boolean getIsPayrollAdvance() {
    return this.isPayrollAdvance;
  }

  public void setIsPayrollAdvance(final Boolean isPayrollAdvance) {
    this.isPayrollAdvance = isPayrollAdvance;
  }

  public Boolean getIsRecurring() {
    return this.isRecurring;
  }

  public void setIsRecurring(final Boolean isRecurring) {
    this.isRecurring = isRecurring;
  }

  public Boolean getIsSubscription() {
    return this.isSubscription;
  }

  public void setIsSubscription(final Boolean isSubscription) {
    this.isSubscription = isSubscription;
  }

  public String getTopLevelCategory() {
    return this.topLevelCategory;
  }

  public void setTopLevelCategory(final String topLevelCategory) {
    this.topLevelCategory = topLevelCategory;
  }

  public String getAccountGuid() {
    return this.accountGuid;
  }

  public void setAccountGuid(final String accountGuid) {
    this.accountGuid = accountGuid;
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

  public Date getTransformationDate() {
    return this.transformationDate;
  }

  public void setTransformationDate(final Date transformationDate) {
    this.transformationDate = transformationDate;
  }

  public Date getInstanceTimestamp() {
    return this.instanceTimestamp;
  }

  public void setInstanceTimestamp(Date instanceTimestamp) {
    this.instanceTimestamp = instanceTimestamp;
  }

  public MxMerchant getMxMerchant() {
    return this.mxMerchant;
  }

  public void setMxMerchant(MxMerchant mxMerchant) {
    this.mxMerchant = mxMerchant;
  }

  public MxMerchantLocation getMxMerchantLocation() {
    return this.mxMerchantLocation;
  }

  public void setMxMerchantLocation(MxMerchantLocation mxMerchantLocation) {
    this.mxMerchantLocation = mxMerchantLocation;
  }

  public MxAccount getMxAccount() {
    return this.mxAccount;
  }

  public void setMxAccount(MxAccount mxAccount) {
    this.mxAccount = mxAccount;
  }
}
