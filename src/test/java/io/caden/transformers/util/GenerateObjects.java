package io.caden.transformers.util;

import io.caden.transformers.amazon.entities.AmazonOrder;
import io.caden.transformers.amazon.entities.AmazonOrderStatus;
import io.caden.transformers.mx.dtos.MxApiAccountDto;
import io.caden.transformers.mx.dtos.MxApiTransactionDto;
import io.caden.transformers.mx.dtos.MxTransactionTransformationMessageDto;
import io.caden.transformers.shared.dtos.TransformationMessageBodyDto;

import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

public class GenerateObjects {
    public static TransformationMessageBodyDto generateTransformationMessageBodyDto() {
        var resp = new TransformationMessageBodyDto();
        resp.setCadenAlias(UUID.randomUUID());
        resp.setAccessKeyId("test");
        resp.setDatabaseEncryptionKey("test_encrypt");
        resp.setSessionToken("test_session");
        resp.setSecretAccessKey("test_secret");
        resp.setPath(UUID.randomUUID()+"/mx/"+UUID.randomUUID());
        return resp;
    }
    public static MxTransactionTransformationMessageDto generateMxTransactionTransformationMessageDto(String accountNumber, String id) {
        var resp = new MxTransactionTransformationMessageDto();
        var transactions = new ArrayList<MxApiTransactionDto>();
        transactions.add(generateMxApiTransactionDto(accountNumber, id));
        resp.setTransactions(transactions);
        return resp;
    }

    public static MxApiTransactionDto generateMxApiTransactionDto(String accountNumber, String id) {
        var resp = new MxApiTransactionDto();
        resp.setGuid(UUID.randomUUID().toString());
        resp.setAccount(setMxApiAccountDto(accountNumber, id));
        resp.setCategory("test_category");
        resp.setAccountGuid(UUID.randomUUID().toString());
        resp.setAmount(10.0);
        resp.setDate(new Date().toString());
        return resp;
    }

    public static MxApiAccountDto setMxApiAccountDto(String accountNumber, String id) {
        var resp = new MxApiAccountDto();
        resp.setAccountNumber(accountNumber);
        resp.setGuid(UUID.randomUUID().toString());
        resp.setId(id);
        resp.setType("test_type");
        resp.setName("test_name");
        return resp;
    }

    public static AmazonOrder generateAmazonOrder() {
        AmazonOrder amazonOrder = new AmazonOrder();
        amazonOrder.setOrderNumber("test");
        amazonOrder.setOrderStatus(AmazonOrderStatus.ORDER_DELIVERED);
        amazonOrder.setOrderDate(new Date());
        amazonOrder.setPrice(100.00);
        amazonOrder.setCadenAlias(UUID.randomUUID().toString());
        amazonOrder.setBillingAddress("test address");
        amazonOrder.setDeliveryFee(1.5);
        amazonOrder.setPaymentIdLastFour("1234");
        amazonOrder.setCustomer("testCustomer");
        amazonOrder.setPriceCurrency("testPriceCurrency");
        amazonOrder.setTax(2.00);
        return amazonOrder;
    }
}
