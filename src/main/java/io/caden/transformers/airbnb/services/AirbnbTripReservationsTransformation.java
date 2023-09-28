package io.caden.transformers.airbnb.services;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import lombok.extern.log4j.Log4j2;
import org.jsoup.parser.Parser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.amazonaws.services.sqs.model.Message;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.caden.transformers.airbnb.dtos.AirbnbTransformationDto;
import io.caden.transformers.airbnb.entities.AirbnbTripReservation;
import io.caden.transformers.airbnb.entities.AirbnbTripReservationPlace;
import io.caden.transformers.airbnb.entities.AirbnbTripReservationPlaceRating;
import io.caden.transformers.airbnb.entities.AirbnbTripReservationStatus;
import io.caden.transformers.airbnb.models.AirbnbRoomPage;
import io.caden.transformers.airbnb.models.AirbnbTripDetailsPage;
import io.caden.transformers.airbnb.models.AirbnbTripListPage;
import io.caden.transformers.airbnb.repositories.AirbnbTripReservationPlaceRatingRepository;
import io.caden.transformers.airbnb.repositories.AirbnbTripReservationPlaceRepository;
import io.caden.transformers.airbnb.repositories.AirbnbTripReservationRepository;
import io.caden.transformers.shared.dtos.DataSchemaChangeDto;
import io.caden.transformers.shared.dtos.TransformationMessageBodyDto;
import io.caden.transformers.shared.exceptions.DataSchemaException;
import io.caden.transformers.shared.services.aws.AwsQueueExtendedService;
import io.caden.transformers.shared.services.vault.VaultManagementService;

@Log4j2
@Service
public class AirbnbTripReservationsTransformation {
  private VaultManagementService vaultManagementService;

  private AwsQueueExtendedService queueService;

  private AirbnbTripReservationRepository airbnbTripReservationRepo;

  private AirbnbTripReservationPlaceRatingRepository airbnbTripReservationPlaceRatingRepo;

  private AirbnbTripReservationPlaceRepository airbnbTripReservationPlaceRepo;

  @Value("${airbnb.url}")
  private String airbnbUrl;

  // for logging purposes
  private String path;

  public AirbnbTripReservationsTransformation(
    @Autowired final VaultManagementService vaultManagementService,
    @Autowired final AwsQueueExtendedService queueService,
    @Autowired final AirbnbTripReservationRepository airbnbTripReservationRepo,
    @Autowired final AirbnbTripReservationPlaceRatingRepository airbnbTripReservationPlaceRatingRepo,
    @Autowired final AirbnbTripReservationPlaceRepository airbnbTripReservationPlaceRepo
  ) {
    this.vaultManagementService = vaultManagementService;
    this.queueService = queueService;
    this.airbnbTripReservationRepo = airbnbTripReservationRepo;
    this.airbnbTripReservationPlaceRatingRepo = airbnbTripReservationPlaceRatingRepo;
    this.airbnbTripReservationPlaceRepo = airbnbTripReservationPlaceRepo;
  }

  @Transactional
  public void execute() throws Exception {
    for (Message sqsMessage : this.queueService.receiveMessage(AwsQueueExtendedService.airbnbTransformationsQueueUrl).getMessages()) {
      try {
        TransformationMessageBodyDto dto = new ObjectMapper().readValue(sqsMessage.getBody(), TransformationMessageBodyDto.class);
        this.path = dto.getPath();

        AirbnbTransformationDto transformationDto = new ObjectMapper().readValue(this.vaultManagementService.getExtractedData(dto), AirbnbTransformationDto.class);
  
        AirbnbTripDetailsPage airbnbTripDetailsPage = new AirbnbTripDetailsPage(
          Parser.parse(transformationDto.getTripDetails(), this.airbnbUrl)
        );

        if (this.airbnbTripReservationRepo.find(airbnbTripDetailsPage.getConfirmationCode(), dto.getCadenAlias().toString()) == null) {
          AirbnbTripReservation mappedTripReservation = airbnbTripDetailsPage.getAirbnbTripReservation();

          AirbnbTripReservation airbnbTripReservation = this.extractTripReservation(dto, transformationDto, airbnbTripDetailsPage, mappedTripReservation);
          AirbnbTripReservationPlace airbnbTripReservationPlace = this.extractPlace(transformationDto, airbnbTripDetailsPage, airbnbTripReservation, mappedTripReservation);
          List<AirbnbTripReservationPlaceRating> airbnbTripReservationPlaceRatings = this.extractRating(airbnbTripReservationPlace);

          // We cannot have a place without address
          if (airbnbTripReservationPlace.getAddress() == null || airbnbTripReservationPlace.getAddress().isBlank()) {
            throw new DataSchemaException(DataSchemaChangeDto.fromPath(this.path));
          }

          this.airbnbTripReservationRepo.update(airbnbTripReservation);
          this.airbnbTripReservationPlaceRepo.update(airbnbTripReservationPlace);

          for (AirbnbTripReservationPlaceRating airbnbTripReservationPlaceRating : airbnbTripReservationPlaceRatings) {
            this.airbnbTripReservationPlaceRatingRepo.update(airbnbTripReservationPlaceRating);
          }
        }

        this.queueService.deleteMessage(AwsQueueExtendedService.airbnbTransformationsQueueUrl, sqsMessage.getReceiptHandle());
      } catch (JsonProcessingException e) {
        log.error(DataSchemaChangeDto.fromPath(this.path).toString(), e);
      } catch (DataSchemaException e) {
        log.error(e.getMessage(), e);
      } catch (Exception e) {
        log.error("Error AirbnbTripReservationsTransformation.", e);
      } finally {
        this.path = null;
      }
    }
  }

  private AirbnbTripReservation extractTripReservation(
    TransformationMessageBodyDto dto,
    AirbnbTransformationDto transformationDto,
    AirbnbTripDetailsPage airbnbTripDetailsPage,
    AirbnbTripReservation mappedTripReservation
  ) throws Exception {
    AirbnbTripReservation airbnbTripReservation = new AirbnbTripReservation();

    airbnbTripReservation.setCadenAlias(dto.getCadenAlias().toString());
    airbnbTripReservation.setReservationId(airbnbTripDetailsPage.getConfirmationCode());

    if (mappedTripReservation.getCheckinTime() == null) {
      AirbnbTripListPage airbnbTripListPage = new AirbnbTripListPage(Parser.parse(
        transformationDto.getTripList(),
        this.airbnbUrl
      ));

      List<Date> dates = airbnbTripListPage.getTripDateRange(airbnbTripReservation.getReservationId());

      airbnbTripReservation.setCheckinTime(dates.get(0));
      airbnbTripReservation.setCheckoutTime(dates.get(1));
    } else {
      airbnbTripReservation.setCheckinTime(mappedTripReservation.getCheckinTime());
      airbnbTripReservation.setCheckoutTime(mappedTripReservation.getCheckoutTime());
    }

    Date currentDate = new Date();

    airbnbTripReservation.setNumAdults(mappedTripReservation.getNumAdults());
    airbnbTripReservation.setPriceCurrency(mappedTripReservation.getPriceCurrency());
    airbnbTripReservation.setTotalPrice(mappedTripReservation.getTotalPrice());
    airbnbTripReservation.setReservationFor(mappedTripReservation.getReservationFor());
    airbnbTripReservation.setReservationStatus(
      currentDate.after(airbnbTripReservation.getCheckinTime()) ? AirbnbTripReservationStatus.RESERVATION_CONFIRMED : AirbnbTripReservationStatus.RESERVATION_PENDING
    );
    airbnbTripReservation.setTransformationDate(currentDate);

    if (transformationDto.getReceiptInfo() != null) {
      Object paymentIdLastFour = transformationDto.getReceiptInfo().get("paymentIdLastFour");
      Object tax = transformationDto.getReceiptInfo().get("tax");
      Object serviceFee = transformationDto.getReceiptInfo().get("serviceFee");

      airbnbTripReservation.setPaymentIdLastFour(paymentIdLastFour == null ? null : paymentIdLastFour.toString());
      airbnbTripReservation.setTax(tax == null ? null : Double.parseDouble(tax.toString()));
      airbnbTripReservation.setServiceFee(serviceFee == null ? null : Double.parseDouble(serviceFee.toString()));
    }

    return airbnbTripReservation;
  }

  private AirbnbTripReservationPlace extractPlace(
    AirbnbTransformationDto transformationDto,
    AirbnbTripDetailsPage airbnbTripDetailsPage,
    AirbnbTripReservation airbnbTripReservation,
    AirbnbTripReservation mappedTripReservation
  ) {
    AirbnbTripReservationPlace place = new AirbnbTripReservationPlace();

    if (transformationDto.getTripRoom() == null) {
      place.setIdentifier(airbnbTripDetailsPage.getRoomId());
      place.setSameAs(airbnbTripDetailsPage.getRoomUrl());
      place.setAddress(mappedTripReservation.getReservationFor());
    } else {
      place = new AirbnbRoomPage(transformationDto.getTripRoom()).getAirbnbTripReservationPlace();

      if (place.getAddress() == null || place.getAddress().isBlank()) {
        place.setAddress(mappedTripReservation.getReservationFor());
      }
    }

    place.setAirbnbTripReservation(airbnbTripReservation);
    place.setCreatedAt(airbnbTripReservation.getCreatedAt());

    return place;
  }

  private List<AirbnbTripReservationPlaceRating> extractRating(AirbnbTripReservationPlace place) {
    if (place.getAirbnbTripReservationPlaceRatings() != null) {
      return place.getAirbnbTripReservationPlaceRatings().stream().filter(rating -> rating != null).map(rating -> {
        rating.setAirbnbTripReservationPlace(place);
        return rating;
      }).collect(Collectors.toList());
    }

    return Collections.emptyList();
  }
}
