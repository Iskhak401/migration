package io.caden.transformers.netflix.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.awspring.cloud.messaging.listener.SqsMessageDeletionPolicy;
import io.awspring.cloud.messaging.listener.annotation.SqsListener;
import io.caden.transformers.netflix.dtos.NetflixApiProfileInfo;
import io.caden.transformers.netflix.dtos.NetflixApiViewedItem;
import io.caden.transformers.netflix.dtos.NetflixApiViewedItemWrapper;
import io.caden.transformers.netflix.dtos.NetflixTransformationDto;
import io.caden.transformers.netflix.entities.NetflixGraphDbBatch;
import io.caden.transformers.netflix.entities.NetflixProfile;
import io.caden.transformers.netflix.entities.NetflixViewingActivity;
import io.caden.transformers.netflix.repositories.BatchViewingActivityGraphDbRepository;
import io.caden.transformers.netflix.repositories.NetflixGraphDbBatchRepository;
import io.caden.transformers.shared.dtos.DataSchemaChangeDto;
import io.caden.transformers.shared.dtos.TransformationMessageBodyDto;
import io.caden.transformers.shared.exceptions.CustomException;
import io.caden.transformers.shared.services.vault.VaultManagementService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.*;

import static net.logstash.logback.argument.StructuredArguments.kv;

@Slf4j
@Service
@RequiredArgsConstructor
public class NetflixViewingActivitiesTransformation {

  @Value("${netflix.url}")
  private String netflixUrl;
  @Value("${location.transactions.batch.size}")
  private int defaultBatchSize;
  private int dbBatchSize = 0;

  private final VaultManagementService vaultManagementService;
  private final BatchViewingActivityGraphDbRepository batchSaveRepository;
  private final NetflixGraphDbBatchRepository netflixGraphDbBatchRepository;

  @SqsListener(value = "${aws.sqs.netflix.transformations}", deletionPolicy = SqsMessageDeletionPolicy.ON_SUCCESS)
  public void receiveMessages(TransformationMessageBodyDto message) {
    try {
      long startTime = System.currentTimeMillis();
      log.info("Netflix Transformations started for path = {}, Start time = {}", message.getPath(), new DateTime(startTime));
      this.processMessage(message);
      long elapsedTime = System.currentTimeMillis() - startTime;

      log.info("Netflix Transformations finished for path = {}, Took = {}", message.getPath(), elapsedTime,
              kv("time_ms", elapsedTime),
              kv("path", message.getPath()));
    } catch (JsonProcessingException e) {
      log.error(DataSchemaChangeDto.fromPath(message.getPath()).toString(), e);
      throw CustomException.withCode(HttpStatus.BAD_REQUEST, "class: " + e.getClass().getName() + "message: " + e.getMessage());
    } catch (Exception e) {
      log.error("ERROR_VIEWING_ACTIVITIES_TRANSFORMATION_FAILED for path={}",
              kv("path", message.getPath()),
              kv("exception", e));
      throw CustomException.withCode(HttpStatus.INTERNAL_SERVER_ERROR, "class: " + e.getClass().getName() + "message: " + e.getMessage());
    }
  }

  private void processMessage(TransformationMessageBodyDto dto) throws Exception {
    List<String> extractions = this.readExtractions(dto);

    String cadenAlias = dto.getCadenAlias().toString();

    NetflixProfile netflixProfile = null;
    List<NetflixViewingActivity> viewingActivities = new LinkedList<>();

    for (String extraction : extractions) {
      NetflixTransformationDto transformationDto = this.parseExtraction(extraction);
      for (NetflixApiViewedItem netflixApiViewedItem : transformationDto.getViewedItems()) {
        if (netflixProfile == null) {
          netflixProfile = this.getOrCreateProfile(cadenAlias, transformationDto.getProfileInfo());
        }

        NetflixApiViewedItemWrapper wrapper = new NetflixApiViewedItemWrapper(netflixApiViewedItem, netflixUrl);
        if (wrapper.getFullTitle().isBlank()) {
          continue;
        }

        NetflixViewingActivity netflixViewingActivity = new NetflixViewingActivity();
        netflixViewingActivity.setCadenAlias(cadenAlias);
        netflixViewingActivity.setObject(wrapper.getFullTitle());
        netflixViewingActivity.setStartTime(wrapper.getDateObject());
        netflixViewingActivity.setNetflixId(Long.toString(wrapper.getNetflixId()));
        netflixViewingActivity.setNetflixProductionId(Long.toString(wrapper.getNetflixProductionId()));
        netflixViewingActivity.setTransformationDate(new Date());
        netflixViewingActivity.setProductionTitle(wrapper.getProductionTitle());
        netflixViewingActivity.setSerie(wrapper.isSerie());
        netflixViewingActivity.setNetflixProfile(netflixProfile);
        netflixViewingActivity.setUuid(UUID.randomUUID().toString());
        netflixViewingActivity.setEpisodeTitle(wrapper.getEpisodeTitle());
        netflixViewingActivity.setProductionUrl(wrapper.getProductionUrl());
        netflixViewingActivity.setEpisodeUrl(wrapper.getEpisodeUrl());
        netflixViewingActivity.setInstanceTimestamp(new Date());
        viewingActivities.add(netflixViewingActivity);
      }
    }

    batchSaveRepository.queue(viewingActivities, dto);
  }

  public void flush() throws Exception {
    if (dbBatchSize == 0) {
      NetflixGraphDbBatch netflixGraphDbBatch = netflixGraphDbBatchRepository.findFirstByOrderById();

      if (netflixGraphDbBatch != null) {
        dbBatchSize = netflixGraphDbBatch.getBatchSize();
      } else {
        dbBatchSize = defaultBatchSize;
      }
    }

    batchSaveRepository.flush(dbBatchSize);
  }

  private List<String> readExtractions(TransformationMessageBodyDto dto) throws Exception {
    String data = this.vaultManagementService.getExtractedData(dto);

    // First, try parsing for legacy extractions.
    // Legacy extractions will arrive bundled into a single list.
    // TODO: Remove after we make sure there are no legacy extractions in other environments
    List<String> extractions = this.readLegacyExtractions(data);
    if (extractions != null) {
      log.info("this is happening. readLegacyExtractions");
      return extractions;
    }

    // Otherwise, current extractions should arrive as a single string
    return Arrays.asList(data);
  }

  private List<String> readLegacyExtractions(String data) {
    try {
      return Arrays.asList(new ObjectMapper().readValue(data, String[].class));
    } catch (Exception e) {
      return null;
    }
  }

  private NetflixTransformationDto parseExtraction(String extraction) throws JsonProcessingException {
    return new ObjectMapper().readValue(extraction, NetflixTransformationDto.class);
  }

  private NetflixProfile getOrCreateProfile(
    String cadenAlias,
    NetflixApiProfileInfo netflixApiProfileInfo
  ) {
    NetflixProfile netflixProfile = new NetflixProfile();
    netflixProfile.setGuid(netflixApiProfileInfo.getGuid());
    netflixProfile.setCadenAlias(cadenAlias);

    netflixProfile.setName(netflixApiProfileInfo.getProfileName());

    return netflixProfile;
  }
}
