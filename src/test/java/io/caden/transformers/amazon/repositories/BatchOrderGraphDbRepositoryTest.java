package io.caden.transformers.amazon.repositories;

import io.caden.transformers.amazon.entities.*;
import io.caden.transformers.amazon.mappers.AmazonOrderItemToStatements;
import io.caden.transformers.amazon.mappers.AmazonOrderToStatements;
import io.caden.transformers.amazon.mappers.AmazonParcelDeliveryToStatements;
import io.caden.transformers.amazon.mappers.AmazonSellerToStatements;
import io.caden.transformers.config.RDFConfiguration;
import io.caden.transformers.shared.repositories.BaseBatchRepository;
import io.caden.transformers.shared.repositories.UserGraphRepository;
import org.eclipse.rdf4j.model.impl.SimpleValueFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.*;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BatchOrderGraphDbRepositoryTest {
    @Mock
    private AmazonOrderRepository amazonTransactionRepo;
    @Mock
    private UserGraphRepository userGraphRepository;
    @Mock
    private RDFConfiguration rdfConfiguration;
    @Mock
    private BaseBatchRepository baseBatchRepository;
    @Mock
    private Page<BatchAmazonOrder> batch;
    @Mock
    private AmazonSellerToStatements amazonSellerToStatements;
    @Mock
    private AmazonParcelDeliveryToStatements amazonParcelDeliveryToStatements;
    @Mock
    private AmazonOrderItemToStatements amazonOrderItemToStatements;
    @Mock
    private AmazonOrderToStatements amazonOrderToStatements;
    @InjectMocks
    private BatchOrderGraphDbRepository batchOrderGraphDbRepository;


    @Test
    void flush() throws Exception {
        when(baseBatchRepository.findAll(ArgumentMatchers.any(Pageable.class))).thenReturn(batch);
        when(batch.hasContent()).thenReturn(true);
        when(batch.getContent()).thenReturn(generateAmazonOrders(10, false));

        batchOrderGraphDbRepository.flush(10);

        verify(amazonSellerToStatements, times(10)).map((AmazonSeller) any());
        verify(userGraphRepository, atMostOnce()).save(ArgumentMatchers.any());
        verify(baseBatchRepository, atMostOnce()).deleteAllByIdInBatch(ArgumentMatchers.any());
    }

    @Test
    void flush_whenNothingToFlush() throws Exception {
        when(baseBatchRepository.findAll(ArgumentMatchers.any(Pageable.class))).thenReturn(batch);
        when(batch.hasContent()).thenReturn(false);

        batchOrderGraphDbRepository.flush(10);

        verify(userGraphRepository, never()).save(ArgumentMatchers.any());
        verify(baseBatchRepository, never()).deleteAllByIdInBatch(ArgumentMatchers.any());
    }

    @Test
    void flush_whenDuplicationIsPresent() throws Exception {
        when(baseBatchRepository.findAll(ArgumentMatchers.any(Pageable.class))).thenReturn(batch);
        when(batch.hasContent()).thenReturn(true);
        when(batch.getContent()).thenReturn(generateAmazonOrders(10, true));

        batchOrderGraphDbRepository.flush(11);

        verify(amazonSellerToStatements, times(10)).map((AmazonSeller) any());
        verify(userGraphRepository, atMostOnce()).save(ArgumentMatchers.any());
        verify(baseBatchRepository, atMostOnce()).deleteAllByIdInBatch(ArgumentMatchers.any());
    }

    private List<BatchAmazonOrder> generateAmazonOrders(int n, boolean dup) {
        List<BatchAmazonOrder> batchAmazonOrders = new LinkedList<>();
        List<AmazonOrder> amazonOrders = new LinkedList<>();
        String dupAmazonOrderUUID = "";
        for (int i = 0; i < n; i++) {
            BatchAmazonOrder batchAmazonOrder = new BatchAmazonOrder();
            AmazonOrder amazonOrder = new AmazonOrder();
            Set<AmazonOrderItem> amazonOrderItemSet = new HashSet<>();
            amazonOrder.setCadenAlias(UUID.randomUUID().toString());
            amazonOrder.setOrderNumber("TR-" + UUID.randomUUID());
            AmazonOrderItem amazonOrderItem = new AmazonOrderItem();
            AmazonSeller amazonSeller = new AmazonSeller();
            AmazonParcelDelivery amazonParcelDelivery = new AmazonParcelDelivery();
            amazonParcelDelivery.setDeliveryAddress("address");
            amazonParcelDelivery.setTrackingNumber("number");
            amazonParcelDelivery.setDeliveryMethod(DeliveryMethod.LOCKER_DELIVERY);
            amazonSeller.setName("randomName");
            amazonSeller.setIdentifier("identifier");
            amazonSeller.setSameAs("sameAs");
            amazonOrderItem.setSeller(amazonSeller);
            amazonOrderItem.setParcelDelivery(amazonParcelDelivery);
            amazonOrderItemSet.add(amazonOrderItem);
            amazonOrder.setAmazonOrderItems(amazonOrderItemSet);
            dupAmazonOrderUUID = amazonOrder.getOrderNumber();
            batchAmazonOrder.setBatchObject(amazonOrder);
            batchAmazonOrder.setCognitoId(UUID.randomUUID());
            batchAmazonOrders.add(batchAmazonOrder);
        }

        if(dup) {
            BatchAmazonOrder batchAmazonOrder = new BatchAmazonOrder();
            AmazonOrder amazonOrder = new AmazonOrder();
            amazonOrder.setOrderNumber(dupAmazonOrderUUID);
            batchAmazonOrder.setBatchObject(amazonOrder);
            batchAmazonOrders.add(batchAmazonOrder);
        }

        return batchAmazonOrders;
    }
}
