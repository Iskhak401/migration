package io.caden.transformers.uber.mappers;

import org.eclipse.rdf4j.query.BindingSet;
import org.springframework.stereotype.Component;

import io.caden.transformers.shared.mappers.Mapper;
import io.caden.transformers.shared.utils.CadenBaseConstants;
import io.caden.transformers.shared.utils.RDFUtil;
import io.caden.transformers.uber.entities.UberTrip;
import io.caden.transformers.uber.utils.UberConstants;

@Component
public class BindingSetToUberTrip extends Mapper<BindingSet, UberTrip> {

  @Override
  public UberTrip map(final BindingSet bindingSet) {
    if (bindingSet == null) {
      return null;
    }

    // Building out the UberTrip object with pre-set base constants

    UberTrip uberTrip = new UberTrip();
    uberTrip.setCadenAlias(RDFUtil.getValue(bindingSet, CadenBaseConstants.CADEN_ALIAS));
    uberTrip.setUuid(RDFUtil.getValue(bindingSet, UberConstants.RESERVATION_ID));
    uberTrip.setRequestTime(RDFUtil.getDate(RDFUtil.getValue(bindingSet, UberConstants.REQUEST_TIME)));
    uberTrip.setIdentifier(RDFUtil.getValue(bindingSet, UberConstants.IDENTIFIER));
    uberTrip.setPickupTime(RDFUtil.getDate((RDFUtil.getValue(bindingSet, UberConstants.PICKUP_TIME))));
    uberTrip.setPriceCurrency(RDFUtil.getValue(bindingSet, UberConstants.PRICE_CURRENCY));
    uberTrip.setTotalPrice(RDFUtil.getDouble(RDFUtil.getValue(bindingSet, UberConstants.TOTAL_PRICE)));
    uberTrip.setDropOffTime(RDFUtil.getDate(RDFUtil.getValue(bindingSet, UberConstants.DROP_OFF_TIME)));
    uberTrip.setDuration(RDFUtil.getDouble(RDFUtil.getValue(bindingSet, UberConstants.DURATION)));
    uberTrip.setDistance(RDFUtil.getDouble(RDFUtil.getValue(bindingSet, UberConstants.DISTANCE)));
    uberTrip.setCity(RDFUtil.getValue(bindingSet, UberConstants.CITY));
    uberTrip.setUberType(RDFUtil.getValue(bindingSet, UberConstants.RIDE_TYPE));
    uberTrip.setPaymentIdLastFour(RDFUtil.getValue(bindingSet, UberConstants.PAYMENT_ID_LAST_FOUR));
    uberTrip.setServiceFee(RDFUtil.getDouble(RDFUtil.getValue(bindingSet, UberConstants.SERVICE_FEE)));
    uberTrip.setTax(RDFUtil.getDouble(RDFUtil.getValue(bindingSet, UberConstants.TAX)));
    return uberTrip;
  }

  @Override
  public BindingSet reverseMap(final UberTrip value) {
    throw new UnsupportedOperationException("Not implemented exception");
  }
}
