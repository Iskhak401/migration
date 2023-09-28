package io.caden.transformers.amazon.services;

import io.caden.transformers.amazon.entities.AmazonGraphDbBatch;
import io.caden.transformers.amazon.repositories.AmazonGraphDbBatchRepository;
import io.caden.transformers.amazon.repositories.AmazonOrderRepository;
import io.caden.transformers.amazon.repositories.BatchOrderGraphDbRepository;
import io.caden.transformers.shared.dtos.TransformationMessageBodyDto;
import io.caden.transformers.shared.services.vault.VaultManagementService;
import io.caden.transformers.util.GenerateObjects;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AmazonOrdersTransformationTest {

    @Mock
    private VaultManagementService vaultManagementService;
    @Mock
    private AmazonOrdersTransformationHelper amazonOrdersTransformationHelper;
    @Mock
    private AmazonOrderRepository amazonOrderRepository;

    @Mock
    private AmazonGraphDbBatchRepository amazonGraphDbBatchRepository;

    @Mock
    private BatchOrderGraphDbRepository batchOrderGraphDbRepository;

    @InjectMocks
    private AmazonOrdersTransformation amazonOrdersTransformation;

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(amazonOrdersTransformation, "amazonUrl", "amazon.com");
        ReflectionTestUtils.setField(amazonOrdersTransformation, "defaultBatchSize", 100);
    }

    @Test
    void receivingMessage() throws Exception {
        when(vaultManagementService.getExtractedData(any(TransformationMessageBodyDto.class)))
                .thenReturn("<!DOCTYPE html>\n" +
                        "<html>\n" +
                        "<head>\n" +
                        "    <title>Sample Order Details</title>\n" +
                        "</head>\n" +
                        "<body>\n" +
                        "    <div id=\"orderDetails\">\n" +
                        "        <div class=\"order-date-invoice-item\">Ordered on May 23, 2023</div>\n" +
                        "        <div class=\"order-date-invoice-item\">Order# 123456</div>\n" +
                        "\n" +
                        "        <div class=\"displayAddressLI\">123 Main Street</div>\n" +
                        "        <div class=\"displayAddressLI\">City, State, Zip</div>\n" +
                        "\n" +
                        "        <div class=\"js-shipment-info-container\">\n" +
                        "            <div class=\"a-row\">\n" +
                        "                <div>Shipment Status:</div>\n" +
                        "                <div>Delivered on June 2, 2023</div>\n" +
                        "            </div>\n" +
                        "            <div class=\"a-row\">\n" +
                        "                <div>Other Status Information:</div>\n" +
                        "                <div>Some status information here</div>\n" +
                        "            </div>\n" +
                        "        </div>\n" +
                        "\n" +
                        "        <div id=\"od-subtotals\">\n" +
                        "            <div class=\"a-fixed-right-grid-col a-col-right\">\n" +
                        "                <div>Subtotal: $50.00</div>\n" +
                        "                <div>Shipping: $5.00</div>\n" +
                        "                <div>Tax: $5.00 (estimated)</div>\n" +
                        "            </div>\n" +
                        "        </div>\n" +
                        "    </div>\n" +
                        "\n" +
                        "</body>\n" +
                        "</html>");

        TransformationMessageBodyDto transformationMessageBodyDto = GenerateObjects.generateTransformationMessageBodyDto();
        amazonOrdersTransformation.receiveMessage(transformationMessageBodyDto);

        verify(batchOrderGraphDbRepository, atMostOnce()).queue(anyList(), any());
    }

    @Test
    void flush() throws Exception {
        when(amazonGraphDbBatchRepository.findFirstByOrderById()).thenReturn(null);

        amazonOrdersTransformation.flush();

        verify(batchOrderGraphDbRepository).flush(eq(100));
    }

    @Test
    void flush_fromDB() throws Exception {
        AmazonGraphDbBatch amazonGraphDbBatch = new AmazonGraphDbBatch();
        amazonGraphDbBatch.setBatchSize(50);
        when(amazonGraphDbBatchRepository.findFirstByOrderById()).thenReturn(amazonGraphDbBatch);

        amazonOrdersTransformation.flush();

        verify(batchOrderGraphDbRepository).flush(eq(50));
    }
}