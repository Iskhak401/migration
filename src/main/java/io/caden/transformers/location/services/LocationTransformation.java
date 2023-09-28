package io.caden.transformers.location.services;

import com.google.common.collect.Lists;
import io.awspring.cloud.messaging.listener.SqsMessageDeletionPolicy;
import io.awspring.cloud.messaging.listener.annotation.SqsListener;
import io.caden.transformers.location.entities.LocationGraphDbBatch;
import io.caden.transformers.location.entities.MoveAction;
import io.caden.transformers.location.repositories.BatchMoveActionGraphDbRepository;
import io.caden.transformers.location.repositories.LocationGraphDbBatchRepository;
import io.caden.transformers.shared.dtos.DataSchemaChangeDto;
import io.caden.transformers.shared.dtos.TransformationMessageBodyDto;
import io.caden.transformers.shared.exceptions.CustomException;
import io.caden.transformers.shared.services.vault.VaultManagementService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class LocationTransformation {

    private final VaultManagementService vaultManagementService;
    private final BatchMoveActionGraphDbRepository batchMoveActionGraphDbRepository;
    private final LocationGraphDbBatchRepository locationGraphDbBatchRepository;

    @Value("${location.transactions.batch.size}")
    private int defaultBatchSize;
    private int dbBatchSize = 0;

    @SqsListener(value = "${aws.sqs.location.transformations}", deletionPolicy = SqsMessageDeletionPolicy.ON_SUCCESS)
    public void receiveMessage(TransformationMessageBodyDto message) {
        try {

            JSONObject json = new JSONObject(this.vaultManagementService.getExtractedData(message));

            MoveAction moveAction = new MoveAction();
            moveAction.setCadenAlias(message.getCadenAlias());
            moveAction.setEndTime(new Date(json.getLong("date")));
            moveAction.setLatitude(json.getDouble("latitude"));
            moveAction.setLongitude(json.getDouble("longitude"));
            moveAction.setCreatedAt(new Date());
            moveAction.setUuid(UUID.randomUUID().toString());

            batchMoveActionGraphDbRepository.queue(Lists.newArrayList(moveAction), message);

        } catch (JSONException e) {
            log.error(DataSchemaChangeDto.fromPath(message.getPath()).toString(), e);
            throw CustomException.withCode(HttpStatus.INTERNAL_SERVER_ERROR, "class: " + e.getClass().getName() + "message: " + e.getMessage());
        } catch (Exception e) {
            log.error("Error LocationTransformation.", e);
            throw CustomException.withCode(HttpStatus.INTERNAL_SERVER_ERROR, "class: " + e.getClass().getName() + "message: " + e.getMessage());
        }

    }

    public void flush() throws Exception {
        if (dbBatchSize == 0) {
            LocationGraphDbBatch locationGraphDbBatch = locationGraphDbBatchRepository.findFirstByOrderById();

            if (locationGraphDbBatch != null) {
                dbBatchSize = locationGraphDbBatch.getBatchSize();
            } else {
                dbBatchSize = defaultBatchSize;
            }
        }

        batchMoveActionGraphDbRepository.flush(dbBatchSize);
    }
}
