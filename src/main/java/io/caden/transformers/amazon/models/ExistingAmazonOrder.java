package io.caden.transformers.amazon.models;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ExistingAmazonOrder {
    private String orderId;
    private String orderNumber;
    private String orderStatus;
}
