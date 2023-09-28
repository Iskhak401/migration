package io.caden.transformers.megraph.services;

import com.amazonaws.services.sqs.model.Message;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.caden.transformers.megraph.dtos.UserGraphDto;
import io.caden.transformers.megraph.entities.User;
import io.caden.transformers.megraph.mappers.UserGraphDtoToUserMapper;
import io.caden.transformers.megraph.repositories.UserRepository;
import io.caden.transformers.shared.dtos.DataSchemaChangeDto;
import io.caden.transformers.shared.dtos.TransformationMessageBodyDto;
import io.caden.transformers.shared.services.aws.AwsQueueExtendedService;
import io.caden.transformers.shared.services.vault.VaultManagementService;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Log4j2
@Service
public class MeGraphTransformation {
  private final AwsQueueExtendedService queueService;
  private final UserRepository userRepo;
  private final VaultManagementService vaultManagementService;
  private final UserGraphDtoToUserMapper userGraphDtoToUserMapper;

  // for logging purposes
  private String path;

  public MeGraphTransformation(
    @Autowired final AwsQueueExtendedService queueService,
    @Autowired final UserRepository userRepo,
    @Autowired final VaultManagementService vaultManagementService,
    @Autowired final UserGraphDtoToUserMapper userGraphDtoToUserMapper
  ) {
    this.queueService = queueService;
    this.userRepo = userRepo;
    this.vaultManagementService = vaultManagementService;
    this.userGraphDtoToUserMapper = userGraphDtoToUserMapper;
  }

  @Transactional
  public void execute() {
    for (Message sqsMessage : this.queueService.receiveMessage(AwsQueueExtendedService.megraphTransformationsQueueUrl).getMessages()) {
      try {
        TransformationMessageBodyDto dto = new ObjectMapper().readValue(sqsMessage.getBody(), TransformationMessageBodyDto.class);
        this.path = dto.getPath();

        this.processMessage(dto);
        this.queueService.deleteMessage(AwsQueueExtendedService.megraphTransformationsQueueUrl, sqsMessage.getReceiptHandle());
      } catch (JsonProcessingException e) {
        log.error(DataSchemaChangeDto.fromPath(this.path).toString(), e);
      } catch (Exception e) {
        log.error("ERROR_MEGRAPH_TRANSFORMATION_FAILED", e);
      } finally {
        this.path = null;
      }
    }
  }

  private void processMessage(TransformationMessageBodyDto dto) throws JsonProcessingException, JsonProcessingException, Exception {
    UserGraphDto userGraph = this.readExtractions(dto);
    User user = this.userGraphDtoToUserMapper.map(userGraph);
    this.userRepo.update(user);
  }

  private UserGraphDto readExtractions(TransformationMessageBodyDto dto) throws Exception {
    return new ObjectMapper().readValue(
      this.vaultManagementService.getExtractedData(dto),
      UserGraphDto.class
    );
  }
}
