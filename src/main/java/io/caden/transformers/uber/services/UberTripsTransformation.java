package io.caden.transformers.uber.services;

import java.util.Date;

import lombok.extern.log4j.Log4j2;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.amazonaws.services.sqs.model.Message;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.caden.transformers.shared.dtos.TransformationMessageBodyDto;
import io.caden.transformers.shared.services.aws.AwsQueueExtendedService;
import io.caden.transformers.shared.services.vault.VaultManagementService;
import io.caden.transformers.shared.utils.RDFUtil;
import io.caden.transformers.uber.entities.UberTrip;
import io.caden.transformers.uber.repositories.UberTripRepository;
import io.caden.transformers.uber.utils.UberConstants;

@Log4j2
@Service
public class UberTripsTransformation {
  private String path;

  private final VaultManagementService vaultManagementService;
  private final AwsQueueExtendedService queueService;
  private final UberTripRepository uberTripRepo;
  private final UberTripTransformationHelper transformationHelper;

  @Value("${uber.permitted.taxes}")
  private String[] permittedTaxes;

  @Value("${uber.benefits}")
  private String[] benefits;

  public UberTripsTransformation(
    @Autowired final VaultManagementService vaultManagementService,
    @Autowired final AwsQueueExtendedService queueService,
    @Autowired final UberTripTransformationHelper transformationHelper,
    @Autowired final UberTripRepository uberTripRepo
  ) {
    this.vaultManagementService = vaultManagementService;
    this.queueService = queueService;
    this.uberTripRepo = uberTripRepo;
    this.transformationHelper = transformationHelper;
  }

  @Transactional
  public void execute() throws Exception {
    for (Message sqsMessage : this.queueService.receiveMessage(AwsQueueExtendedService.uberTransformationsQueueUrl).getMessages()) {
      try {
        TransformationMessageBodyDto dto = new ObjectMapper().readValue(sqsMessage.getBody(), TransformationMessageBodyDto.class);
        this.path = dto.getPath();

        JSONArray trips = new JSONArray(this.vaultManagementService.getExtractedData(dto));

        for (int i = 0; i < trips.length(); i++) {
          JSONObject data = new JSONObject(trips.getString(i));

          String uuid = data.getJSONObject("trip").getString("uuid");
          String cadenAlias = dto.getCadenAlias().toString();
          Double calculatedDistance = null;

          if (uberTripRepo.findByIdentifier("https://riders.uber.com/trips/" + uuid, cadenAlias) == null) {
            UberTrip uberTrip = new UberTrip();
            uberTrip.setCadenAlias(cadenAlias);
            uberTrip.setUuid(uuid);
            uberTrip.setCreatedAt(new Date());

            JSONObject trip = data.getJSONObject("trip");
            JSONObject receipt = data.getJSONObject("receipt");
            JSONArray waypoints = trip.getJSONArray("waypoints");
            String fare = trip.getString("fare");

            if (fare.isBlank()) {
              log.error("fare is blank: {}", fare);
            } else {
              transformationHelper.setTripPriceDetails(uberTrip, fare);
            }

            try {
              calculatedDistance = transformationHelper.calculateDistance(receipt);
            } catch (NullPointerException e) {
              log.error("distanceLabel is null: {}", receipt.opt(UberConstants.DISTANCE_LABEL));
            } catch (NumberFormatException e) {
              log.error("distance is not a valid double: {}", receipt.opt(UberConstants.DISTANCE));
            } catch (Exception e) {
              log.error("An error occurred while calculating the distance: {}", e.getMessage());
            }

            transformationHelper.setTripDetails(uberTrip, cadenAlias, waypoints, data, receipt, trip, calculatedDistance);

            String duration = receipt.optString("duration");
            Date pickupTime = RDFUtil.getDate(trip.getString("beginTripTime"));
            double calculatedDuration = this.transformationHelper.calculateDurationInMinutes(uberTrip, duration, pickupTime);

            uberTrip.setDuration(calculatedDuration);
            uberTrip.setPickupTime(this.transformationHelper.calculatePickupTime(uberTrip, duration, pickupTime, calculatedDuration));

            if (data.has("uberReceiptHtml")) {
              this.transformationHelper.setTaxAndServiceFee(uberTrip, data, permittedTaxes, benefits);
            } else {
              log.error("uberReceiptHtml not found: {}", uuid);
            }

            this.uberTripRepo.update(uberTrip);  
          }
        }

        this.queueService.deleteMessage(AwsQueueExtendedService.uberTransformationsQueueUrl, sqsMessage.getReceiptHandle());
      } catch (Exception e) {
        log.error("Error UberTripsTransformation.", e);
      } finally {
        this.path = null;
      }
    }
  }
}
