package io.caden.transformers.airbnb.mappers;

import org.eclipse.rdf4j.query.BindingSet;
import org.springframework.stereotype.Component;

import io.caden.transformers.airbnb.entities.AirbnbTripReservation;
import io.caden.transformers.airbnb.entities.AirbnbTripReservationStatus;
import io.caden.transformers.airbnb.utils.AirbnbConstants;
import io.caden.transformers.shared.mappers.Mapper;
import io.caden.transformers.shared.utils.CadenBaseConstants;
import io.caden.transformers.shared.utils.RDFUtil;
import io.caden.transformers.shared.utils.SchemaBaseConstants;

@Component
public class BindingSetToAirbnbTripReservation extends Mapper<BindingSet, AirbnbTripReservation> {

  @Override
  public AirbnbTripReservation map(final BindingSet bindingSet) {
    if (bindingSet == null || bindingSet.isEmpty()) {
      return null;
    }

    AirbnbTripReservation airbnbTripReservation = new AirbnbTripReservation();
    airbnbTripReservation.setReservationId(RDFUtil.getValue(bindingSet, SchemaBaseConstants.Property.RESERVATION_ID));
    airbnbTripReservation.setCheckinTime(RDFUtil.getDate(RDFUtil.getValue(bindingSet, SchemaBaseConstants.Property.CHECKIN_TIME)));
    airbnbTripReservation.setCheckoutTime(RDFUtil.getDate(RDFUtil.getValue(bindingSet, SchemaBaseConstants.Property.CHECKOUT_TIME)));
    airbnbTripReservation.setNumAdults(RDFUtil.getInteger(RDFUtil.getValue(bindingSet, SchemaBaseConstants.Property.NUM_ADULTS)));
    airbnbTripReservation.setPriceCurrency(RDFUtil.getValue(bindingSet, SchemaBaseConstants.Property.PRICE_CURRENCY));
    airbnbTripReservation.setTotalPrice(RDFUtil.getDouble(RDFUtil.getValue(bindingSet, SchemaBaseConstants.Property.TOTAL_PRICE)));

    String reservationStatus = RDFUtil.getValue(bindingSet, SchemaBaseConstants.Relationship.RESERVATION_STATUS);
    if (reservationStatus != null) {
      String[] tokens = reservationStatus.split("/");
      String reservationStatusLabel = tokens[tokens.length - 1];

      for (AirbnbTripReservationStatus status : AirbnbTripReservationStatus.values()) {
        if (status.label.equals(reservationStatusLabel)) {
          airbnbTripReservation.setReservationStatus(status);
          break;
        }
      }
    }

    airbnbTripReservation.setPaymentIdLastFour(RDFUtil.getValue(bindingSet, CadenBaseConstants.PAYMENT_ID_LAST_FOUR));
    airbnbTripReservation.setServiceFee(RDFUtil.getDouble(RDFUtil.getValue(bindingSet, AirbnbConstants.SERVICE_FEE)));
    airbnbTripReservation.setTax(RDFUtil.getDouble(RDFUtil.getValue(bindingSet, AirbnbConstants.TAX)));

    return airbnbTripReservation;
  }

  @Override
  public BindingSet reverseMap(final AirbnbTripReservation value) {
    throw new UnsupportedOperationException("Not implemented exception");
  }
}
