package io.caden.transformers.amazon.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.awspring.cloud.messaging.listener.SqsMessageDeletionPolicy;
import io.awspring.cloud.messaging.listener.annotation.SqsListener;
import io.caden.transformers.amazon.entities.AmazonGraphDbBatch;
import io.caden.transformers.amazon.entities.AmazonOrder;
import io.caden.transformers.amazon.models.AmazonOrderDetailsPage;
import io.caden.transformers.amazon.repositories.AmazonGraphDbBatchRepository;
import io.caden.transformers.amazon.repositories.BatchOrderGraphDbRepository;
import io.caden.transformers.shared.dtos.TransformationMessageBodyDto;
import io.caden.transformers.shared.exceptions.CustomException;
import io.caden.transformers.shared.exceptions.DataSchemaException;
import io.caden.transformers.shared.services.vault.VaultManagementService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.parser.Parser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.regex.PatternSyntaxException;
import java.util.stream.Collectors;

import static net.logstash.logback.argument.StructuredArguments.kv;


@Service
@RequiredArgsConstructor
@Slf4j
public class AmazonOrdersTransformation {

    private final VaultManagementService vaultManagementService;
    private final AmazonOrdersTransformationHelper amazonOrdersTransformationHelper;
    private final BatchOrderGraphDbRepository batchOrderGraphDbRepository;
    private final AmazonGraphDbBatchRepository amazonGraphDbBatchRepository;
    @Value("${orders.transactions.batch.size}")
    private int defaultBatchSize;
    private int dbBatchSize = 0;

    @Value("${amazon.url}")
    private String amazonUrl;

    @SqsListener(value = "${aws.sqs.amazon.transformations}", deletionPolicy = SqsMessageDeletionPolicy.ON_SUCCESS)
    public void receiveMessage(TransformationMessageBodyDto transformationMessageBodyDto) {
        long transformationStartTime = System.currentTimeMillis();
        String path = transformationMessageBodyDto.getPath();
        String cadenAlias = transformationMessageBodyDto.getCadenAlias().toString();

        try {
            String html = this.vaultManagementService.getExtractedData(transformationMessageBodyDto);

            AmazonOrderDetailsPage orderDetailsPage = new AmazonOrderDetailsPage(
                    Parser.parse(html, this.amazonUrl)
            );

            List<AmazonOrder> amazonOrders = orderDetailsPage.getAmazonOrders(this.amazonUrl);

            for (AmazonOrder amazonOrder : amazonOrders) {
                amazonOrder.setCadenAlias(cadenAlias);
                amazonOrder.setUuid(UUID.randomUUID().toString());
                amazonOrder.setCreatedAt(amazonOrder.getCreatedAt() == null ? new Date() : amazonOrder.getCreatedAt());
                amazonOrdersTransformationHelper.populateAmazonOrder(cadenAlias, amazonOrder);
            }

            batchOrderGraphDbRepository.queue(amazonOrders, transformationMessageBodyDto);

            long transformationTotalTime = System.currentTimeMillis() - transformationStartTime;
            log.info("AmazonTransformation took {}ms", transformationTotalTime,
                    kv("total_time_ms", transformationTotalTime),
                    kv("path", path));
        } catch (DataSchemaException e) {
            log.error(e.getMessage(), e);
        } catch (JsonProcessingException e) {
            log.error("Error processing JSON message body.", e);
            throw CustomException.withCode(HttpStatus.BAD_REQUEST, "class: " + e.getClass().getName() + "message: " + e.getMessage());
        } catch (ParseException e) {
            log.error("Error parsing HTML content.", e);
            throw CustomException.withCode(HttpStatus.BAD_REQUEST, "class: " + e.getClass().getName() + "message: " + e.getMessage());
        } catch (NullPointerException e) {
            log.error("NullPointerException occurred.", e);
            throw CustomException.withCode(HttpStatus.BAD_REQUEST, "class: " + e.getClass().getName() + "message: " + e.getMessage());
        } catch (PatternSyntaxException e) {
            log.error("PatternSyntaxException occurred.", e);
            throw CustomException.withCode(HttpStatus.BAD_REQUEST, "class: " + e.getClass().getName() + "message: " + e.getMessage());
        } catch (Exception e) {
            log.error("Error couldn't batchSave path={}", path, kv("path", path));
            log.error("Error AmazonOrdersTransformation.", e);
            throw CustomException.withCode(HttpStatus.INTERNAL_SERVER_ERROR, "class: " + e.getClass().getName() + "message: " + e.getMessage());
        }
    }

    public void flush() throws Exception {
        if (dbBatchSize == 0) {
            AmazonGraphDbBatch amazonGraphDbBatch = amazonGraphDbBatchRepository.findFirstByOrderById();

            if (amazonGraphDbBatch != null) {
                dbBatchSize = amazonGraphDbBatch.getBatchSize();
            } else {
                dbBatchSize = defaultBatchSize;
            }
        }

        batchOrderGraphDbRepository.flush(dbBatchSize);
    }

    private static List<String> toOrderIdsList(List<AmazonOrder> amazonOrders) {
        return amazonOrders.stream().map(AmazonOrder::getOrderNumber).collect(Collectors.toList());
    }
}
