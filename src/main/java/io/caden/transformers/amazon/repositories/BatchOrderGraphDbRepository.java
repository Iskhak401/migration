package io.caden.transformers.amazon.repositories;

import com.google.common.collect.Lists;
import io.caden.transformers.amazon.entities.*;
import io.caden.transformers.amazon.mappers.AmazonOrderItemToStatements;
import io.caden.transformers.amazon.mappers.AmazonOrderToStatements;
import io.caden.transformers.amazon.mappers.AmazonParcelDeliveryToStatements;
import io.caden.transformers.amazon.mappers.AmazonSellerToStatements;
import io.caden.transformers.amazon.models.ExistingAmazonOrder;
import io.caden.transformers.config.RDFConfiguration;
import io.caden.transformers.shared.repositories.BaseBatchGraphRepository;
import io.caden.transformers.shared.repositories.BaseBatchRepository;
import io.caden.transformers.shared.repositories.UserGraphRepository;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.rdf4j.model.Statement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Repository
@Slf4j
public class BatchOrderGraphDbRepository extends BaseBatchGraphRepository<AmazonOrder, BatchAmazonOrder> {

    private final AmazonSellerToStatements amazonSellerToStatements;
    private final AmazonParcelDeliveryToStatements amazonParcelDeliveryToStatements;
    private final AmazonOrderItemToStatements amazonOrderItemToStatements;
    private final AmazonOrderToStatements amazonOrderToStatements;
    private final AmazonOrderRepository amazonOrderRepo;

    protected BatchOrderGraphDbRepository(@Autowired RDFConfiguration config,
                                          @Autowired UserGraphRepository userGraphRepository,
                                          @Autowired BaseBatchRepository<AmazonOrder, BatchAmazonOrder> baseBatchRepository,
                                          @Autowired AmazonSellerToStatements amazonSellerToStatements,
                                          @Autowired AmazonParcelDeliveryToStatements amazonParcelDeliveryToStatements,
                                          @Autowired AmazonOrderItemToStatements amazonOrderItemToStatements,
                                          @Autowired AmazonOrderToStatements amazonOrderToStatements,
                                          @Autowired AmazonOrderRepository amazonOrderRepo) {
        super(config, userGraphRepository, baseBatchRepository);
        this.amazonSellerToStatements = amazonSellerToStatements;
        this.amazonParcelDeliveryToStatements = amazonParcelDeliveryToStatements;
        this.amazonOrderItemToStatements = amazonOrderItemToStatements;
        this.amazonOrderToStatements = amazonOrderToStatements;
        this.amazonOrderRepo = amazonOrderRepo;
    }

    @Override
    public List<BatchAmazonOrder> transform(List<AmazonOrder> batchObjects, UUID cognitoId, UUID extractionId, UUID cadenAlias) {
        return batchObjects.stream().map(order -> {
            BatchAmazonOrder batchAmazonOrder = new BatchAmazonOrder();
            batchAmazonOrder.setBatchObject(order);
            batchAmazonOrder.setCognitoId(cognitoId);
            batchAmazonOrder.setExtractionId(extractionId);
            return  batchAmazonOrder;
        }).collect(Collectors.toList());
    }

    @Override
    public List<Statement> buildStatements(AmazonOrder amazonOrder) {
        String cadenAlias = amazonOrder.getCadenAlias();
        String orderNumber = amazonOrder.getOrderNumber();
        Map<String, ExistingAmazonOrder> existingOrdersMap = amazonOrderRepo.findExistingOrdersByCadenAliasAndOrderNumbers(cadenAlias, Lists.newArrayList(orderNumber));
        Set<AmazonOrderItem> items = amazonOrder.getAmazonOrderItems();
        List<Statement> statements = new LinkedList<>();

        if(existingOrdersMap.get(orderNumber) != null) {
            return statements;
        }

        for (AmazonOrderItem amazonOrderItem : items) {
            AmazonSeller seller = amazonOrderItem.getSeller();

            if(seller != null) {
                statements.addAll(toReferenceGraphStatements(amazonSellerToStatements.map(seller)));
            }

            AmazonParcelDelivery parcelDelivery = amazonOrderItem.getParcelDelivery();

            if (parcelDelivery != null) {
                statements.addAll(toUserGraphStatements(amazonParcelDeliveryToStatements.map(parcelDelivery, amazonOrder), cadenAlias));
            }

            statements.addAll(toUserGraphStatements(amazonOrderItemToStatements.map(amazonOrderItem, amazonOrder), cadenAlias));
        }

        statements.addAll(toUserGraphStatements(amazonOrderToStatements.map(amazonOrder), cadenAlias));

        return statements;
    }

    @Override
    public List<AmazonOrder> cleanUp(List<AmazonOrder> amazonOrders) {
        Map<String, AmazonOrder> uniqueAmazonOrdersMap = amazonOrders.stream()
                .collect(Collectors.toMap(AmazonOrder::getOrderNumber, Function.identity(), (og, rep) -> og));

        return new LinkedList<>(uniqueAmazonOrdersMap.values());
    }
}
