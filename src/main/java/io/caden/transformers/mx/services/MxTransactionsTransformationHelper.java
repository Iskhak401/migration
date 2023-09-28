package io.caden.transformers.mx.services;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

import io.caden.transformers.mx.dtos.MxApiTransactionDto;
import io.caden.transformers.mx.entities.MxAccount;
import io.caden.transformers.mx.entities.MxMerchant;
import io.caden.transformers.mx.entities.MxMerchantLocation;
import io.caden.transformers.mx.entities.MxTransaction;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;

@Service
public class MxTransactionsTransformationHelper {
    // Creates and returns an MxTransaction from an MxApiTransactionDto.
      public void transformTransaction(String cadenAlias, MxApiTransactionDto transactionDto, MxTransaction mxTransaction) throws ParseException {

      mxTransaction.setCadenAlias(cadenAlias);
      mxTransaction.setUuid(UUID.randomUUID().toString());
      mxTransaction.setTransformationDate(new Date());
      mxTransaction.setCategory(transactionDto.getCategory());
      mxTransaction.setCategoryGuid(transactionDto.getCategoryGuid());
      mxTransaction.setDescription(transactionDto.getDescription());
      mxTransaction.setIsBillPay(transactionDto.getIsBillPay());
      mxTransaction.setIsDirectDeposit(transactionDto.getIsDirectDeposit());
      mxTransaction.setIsExpense(transactionDto.getIsExpense());
      mxTransaction.setIsFee(transactionDto.getIsFee());
      mxTransaction.setIsIncome(transactionDto.getIsIncome());
      mxTransaction.setIsInternational(transactionDto.getIsInternational());
      mxTransaction.setIsOverdraftFee(transactionDto.getIsOverdraftFee());
      mxTransaction.setIsPayrollAdvance(transactionDto.getIsPayrollAdvance());
      mxTransaction.setIsRecurring(transactionDto.getIsRecurring());
      mxTransaction.setIsSubscription(transactionDto.getIsSubscription());
      mxTransaction.setMxGuid(transactionDto.getGuid());
      mxTransaction.setOrderDate(new SimpleDateFormat("yyyy-MM-dd").parse(transactionDto.getDate()));
      mxTransaction.setPrice(transactionDto.getAmount());
      mxTransaction.setPriceCurrency(transactionDto.getCurrencyCode());
      mxTransaction.setSeller(ObjectUtils.firstNonNull(
              transactionDto.getMerchantLocationGuid(),
              transactionDto.getMerchantGuid(),
              transactionDto.getMerchantCategoryCodeString()
      ));
      mxTransaction.setTopLevelCategory(transactionDto.getTopLevelCategory());
      mxTransaction.setTransactionId(transactionDto.getTransactionId());
      mxTransaction.setAccountGuid(transactionDto.getAccountGuid());
      mxTransaction.setLatitude(transactionDto.getLatitude());
      mxTransaction.setLongitude(transactionDto.getLongitude());
      mxTransaction.setType(transactionDto.getType());
      mxTransaction.setInstanceTimestamp(new Date());

      Optional.ofNullable(transactionDto.getMerchant())
                  .map(merchantDto -> {
                      MxMerchant merchant = new MxMerchant();
                      merchant.setIdentifier(merchantDto.getGuid());
                      merchant.setLogo(merchantDto.getLogoUrl());
                      merchant.setName(merchantDto.getName());
                      merchant.setUrl(merchantDto.getWebsiteUrl());
                      return merchant;
                  })
                  .ifPresent(mxTransaction::setMxMerchant);


      Optional.ofNullable(transactionDto.getMerchantLocation())
                  .map(merchantLocationDto -> {
                    MxMerchantLocation merchantLocation = new MxMerchantLocation();
                    merchantLocation.setIdentifier(merchantLocationDto.getGuid());
                    merchantLocation.setLatitude(merchantLocationDto.getLatitude());
                    merchantLocation.setLongitude(merchantLocationDto.getLongitude());
                    merchantLocation.setTelephone(merchantLocationDto.getPhoneNumber());
                    merchantLocation.setStreetAddress(merchantLocationDto.getStreetAddress());
                    merchantLocation.setAddressLocality(merchantLocationDto.getCity());
                    merchantLocation.setAddressRegion(merchantLocationDto.getState());
                    merchantLocation.setAddressCountry(merchantLocationDto.getCountry());
                    merchantLocation.setPostalCode(merchantLocationDto.getPostalCode());
                    return merchantLocation;
                  })
                  .ifPresent(mxTransaction::setMxMerchantLocation);

      Optional.ofNullable(transactionDto.getAccount())
                  .map(accountDto -> {
                      MxAccount account = new MxAccount();
                      account.setPaymentId(accountDto.getAccountNumber());

                      return account;
                  })
                  .ifPresent(mxTransaction::setMxAccount);

    }
}
