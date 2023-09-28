package io.caden.transformers.amazon.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.caden.transformers.amazon.entities.AmazonOrder;
import io.caden.transformers.amazon.entities.AmazonOrderItem;
import io.caden.transformers.amazon.entities.AmazonProduct;
import io.caden.transformers.amazon.mappers.RainforestRequestProductResultToAmazonProductMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.util.Date;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

@Service
@RequiredArgsConstructor
@Slf4j
public class AmazonOrdersTransformationHelper {

    private final RainforestService rainforestService;
    private final RainforestRequestProductResultToAmazonProductMapper rainforestMapper;

    public void populateAmazonOrder(String cadenAlias, AmazonOrder amazonOrder) throws Exception {
        Set<AmazonOrderItem> items = amazonOrder.getAmazonOrderItems();

        for (AmazonOrderItem amazonOrderItem : items) {
            amazonOrderItem.setCadenAlias(cadenAlias);
            amazonOrderItem.setCreatedAt(amazonOrderItem.getCreatedAt() == null ? new Date() : amazonOrderItem.getCreatedAt());
            //Populate object with only asin, enrichment to happen by separate process
            populateAmazonProduct(amazonOrderItem);
        }
        amazonOrder.setAmazonOrderItems(items);
    }

    private void populateAmazonProduct(AmazonOrderItem amazonOrderItem) throws PatternSyntaxException, NullPointerException, Exception {
        URI uri = new URI(amazonOrderItem.getLink());

        Matcher matcher = Pattern.compile("(?<=product/)\\w+").matcher(uri.getPath());
        matcher.find();

        String asin = matcher.group();
        AmazonProduct amazonProduct = new AmazonProduct();
        amazonProduct.setAsin(asin);
        amazonOrderItem.setAmazonProduct(amazonProduct);

    }

    private AmazonProduct enrichAmazonProduct(String domain, String asin) throws JsonProcessingException {
        return rainforestMapper.map(rainforestService.getProduct(domain, asin));
    }

    private void enrichAmazonGTI(AmazonProduct amazonProduct, RainforestService rainforestService, String domain, String asin) {
        amazonProduct.setGtin(rainforestService.getGtin(domain, asin));
    }

}
