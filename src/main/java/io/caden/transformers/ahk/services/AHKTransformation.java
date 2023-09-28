package io.caden.transformers.ahk.services;

import lombok.extern.log4j.Log4j2;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.amazonaws.services.sqs.model.Message;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.caden.transformers.ahk.repositories.AHKActivitySummaryRepository;
import io.caden.transformers.ahk.repositories.AHKWorkoutRepository;
import io.caden.transformers.shared.dtos.DataSchemaChangeDto;
import io.caden.transformers.shared.dtos.TransformationMessageBodyDto;
import io.caden.transformers.shared.services.aws.AwsQueueExtendedService;
import io.caden.transformers.shared.services.vault.VaultManagementService;

@Log4j2
@Service
public class AHKTransformation {
  private final AwsQueueExtendedService queueService;
  private final AHKActivitySummaryRepository ahkActivitySummaryRepo;
  private final AHKWorkoutRepository ahkWorkoutRepo;
  private final VaultManagementService vaultManagementService;
  private final AHKTransformationHelper ahkTransformationHelper;

  // for logging purposes
  private String path;

  public AHKTransformation(
    @Autowired final AwsQueueExtendedService queueService,
    @Autowired final AHKActivitySummaryRepository ahkActivitySummaryRepo,
    @Autowired final AHKWorkoutRepository ahkWorkoutRepo,
    @Autowired final VaultManagementService vaultManagementService,
    @Autowired final AHKTransformationHelper ahkTransformationHelper
  ) {
    this.queueService = queueService;
    this.ahkActivitySummaryRepo = ahkActivitySummaryRepo;
    this.ahkWorkoutRepo = ahkWorkoutRepo;
    this.vaultManagementService = vaultManagementService;
    this.ahkTransformationHelper = ahkTransformationHelper;
  }

  @Transactional
  public void execute() {
    for (Message sqsMessage : this.queueService.receiveMessage(AwsQueueExtendedService.ahkTransformationQueueUrl).getMessages()) {
      try {
        TransformationMessageBodyDto dto = new ObjectMapper().readValue(sqsMessage.getBody(), TransformationMessageBodyDto.class);
        this.path = dto.getPath();

        JSONObject json = new JSONObject(this.vaultManagementService.getExtractedData(dto));

        ahkTransformationHelper.transformWorkouts(dto.getCadenAlias(), json.optJSONArray("workouts"), ahkWorkoutRepo);
        ahkTransformationHelper.transformActivities(dto.getCadenAlias(), json.optJSONArray("activity_summary"), ahkTransformationHelper.getSteps(json.optJSONArray("user_steps")), ahkActivitySummaryRepo);

        this.queueService.deleteMessage(AwsQueueExtendedService.ahkTransformationQueueUrl, sqsMessage.getReceiptHandle());
      } catch(JSONException e) {
        log.error(DataSchemaChangeDto.fromPath(this.path).toString(), e);
      } catch (Exception e) {
        log.error("Error AHKTransformation.", e);
      } finally {
        this.path = null;
      }
    }
  }
}
