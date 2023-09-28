package io.caden.transformers.mx.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.awspring.cloud.messaging.listener.SqsMessageDeletionPolicy;
import io.awspring.cloud.messaging.listener.annotation.SqsListener;
import io.caden.transformers.mx.dtos.MxApiTransactionDto;
import io.caden.transformers.mx.dtos.MxTransactionTransformationMessageDto;
import io.caden.transformers.mx.entities.MxTransaction;
import io.caden.transformers.mx.repositories.BatchStagingSaveRepository;
import io.caden.transformers.mx.repositories.MxTransactionRepository;
import io.caden.transformers.shared.dtos.TransformationMessageBodyDto;
import io.caden.transformers.shared.exceptions.CustomException;
import io.caden.transformers.shared.services.vault.VaultManagementService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

@Log4j2
@Service
@RequiredArgsConstructor
public class MxTransactionsTransformation {

  private final VaultManagementService vaultManagementService;
  private final MxTransactionRepository mxTransactionRepo;
  private final MxTransactionsTransformationHelper mxTransactionsTransformationHelper;
  private final ObjectMapper objectMapper;
  private final BatchStagingSaveRepository mxBatchStagingRepository;


  @SqsListener(value = "${aws.sqs.mx.transformations}", deletionPolicy = SqsMessageDeletionPolicy.ON_SUCCESS)
  public void receiveMessage(TransformationMessageBodyDto message) {
    try {
      var startTransaction  = System.currentTimeMillis();

      var messageDto = objectMapper.readValue(
              vaultManagementService.getExtractedData(message),
              MxTransactionTransformationMessageDto.class
      );
      var cadenAlias = message.getCadenAlias().toString();
      List<MxTransaction> mxTransactions = new LinkedList<>();

      String path = message.getPath();

      for (MxApiTransactionDto transactionDto : messageDto.getTransactions()) {
        MxTransaction transaction = new MxTransaction();
        mxTransactionsTransformationHelper.transformTransaction(cadenAlias, transactionDto, transaction);
        mxTransactions.add(transaction);
      }

      String[] pathParts = path.split("/");
      UUID cognitoId = UUID.fromString(pathParts[0]);
      UUID extractionId = UUID.fromString(pathParts[2]);

      mxBatchStagingRepository.queue(mxTransactions, message.getCadenAlias(), cognitoId , extractionId);
      log.info("Transaction finished for path = {}, took = {}", path, System.currentTimeMillis() - startTransaction);
    } catch (Exception e) {
      log.error("Exception occurred", e);
      throw CustomException.withCode(HttpStatus.INTERNAL_SERVER_ERROR, "class: " + e.getClass().getName() + "message: " + e.getMessage());
    }
  }
}
