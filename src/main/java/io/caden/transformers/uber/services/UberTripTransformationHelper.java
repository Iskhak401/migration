package io.caden.transformers.uber.services;

import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.springframework.stereotype.Service;

import io.caden.transformers.uber.services.UberTripTransformationHelper;
import io.caden.transformers.shared.utils.RDFUtil;
import io.caden.transformers.uber.entities.UberTrip;
import io.caden.transformers.uber.models.PostalAddressExtraction;
import io.caden.transformers.uber.models.UberTripReceipt;
import io.caden.transformers.uber.utils.UberConstants;

@Service
public class UberTripTransformationHelper
{
    private static final double ONE_KILOMETER_IN_MILE = 0.621371;

    public void setTripPriceDetails(UberTrip uberTrip, String fare)
    {
        if (NumberUtils.isCreatable(fare.substring(1))) {
        uberTrip.setPriceCurrency(StringUtils.substring(fare, 0, 1));
        uberTrip.setTotalPrice(Double.parseDouble(StringUtils.substring(fare, 1, fare.length())
                .replace(",", "")));
        } else {
        uberTrip.setPriceCurrency(StringUtils.substring(fare, 0, 3));
        uberTrip.setTotalPrice(Double.parseDouble(StringUtils.substring(fare, 4, fare.length())
                .replace(",", "")));
        }
    }

    public void setTripDetails(UberTrip uberTrip, String cadenAlias, JSONArray waypoints, JSONObject data, JSONObject receipt, JSONObject trip, Double calculatedDistance)
  {
    uberTrip.setPaymentIdLastFour(this.calculatePaymentId(data));
    uberTrip.setIdentifier(data.getString(UberConstants.IDENTIFIER));
    uberTrip.setCadenAlias(cadenAlias);
    uberTrip.setPickupLocation(PostalAddressExtraction.getPostalAddress(waypoints.get(0).toString()));
    uberTrip.setDropOffLocation(PostalAddressExtraction.getPostalAddress(waypoints.get(waypoints.length() - 1).toString()));
    uberTrip.setDropOffTime(RDFUtil.getDate(trip.getString("dropoffTime")));
    uberTrip.setRequestTime(RDFUtil.getDate(trip.getString("beginTripTime")));
    uberTrip.setUberType(receipt.optString(UberConstants.VEHICLE_TYPE));
    uberTrip.setTransformationDate(new Date());
    uberTrip.setDistance(calculatedDistance);
  }

  public void setTaxAndServiceFee(UberTrip uberTrip, JSONObject data, String[] permittedTaxes, String[] benefits) {
    UberTripReceipt uberTripReceipt = new UberTripReceipt(
      Jsoup.parse(data.getString("uberReceiptHtml")),
      permittedTaxes,
      benefits
    );
    uberTripReceipt.setTaxAndServiceFee(uberTrip);
  }

  public double calculateDurationInMinutes(UberTrip uberTrip, String duration, Date pickupTime) {
    if (duration.isEmpty()) {
      return TimeUnit.MILLISECONDS.toMinutes(uberTrip.getDropOffTime().getTime() - pickupTime.getTime());
    } else {
      int hours = 0;
      double minutes = 0;
  
      Matcher hoursMatcher = Pattern.compile("([\\d\\s]+?(?=h)|(^\\d+))").matcher(duration);
      Matcher minutesMatcher = Pattern.compile("([\\d\\s]+?(?=min)|(\\d+:\\d+$))").matcher(duration);
  
      if (hoursMatcher.find() && duration.contains("h")) {
        hours = Integer.parseInt(hoursMatcher.group().trim());
      }
  
      if (minutesMatcher.find()) {
        minutes = Double.parseDouble(minutesMatcher.group().replace(":", ".").trim());
      }
  
      minutes += hours * 60;

      return minutes;
    }
  }

  public Date calculatePickupTime(UberTrip uberTrip, String duration, Date pickupTime, double calculatedDuration) {
    if (duration.isEmpty()) {
      return pickupTime;
    } else {
      return DateUtils.addMinutes(uberTrip.getDropOffTime(), -1 * (int) calculatedDuration);
    }
  }

  public Double calculateDistance(JSONObject receipt) {

    String distanceLabel = receipt.getString(UberConstants.DISTANCE_LABEL).toLowerCase().trim();
    double distance = receipt.getDouble(UberConstants.DISTANCE);

    if (distanceLabel.equals(UberConstants.DISTANCE_KILOMETERS)) {
      distance = distance * ONE_KILOMETER_IN_MILE;
    }

    return distance;
  }

  public String calculatePaymentId(JSONObject data) {
    if (data.has(UberConstants.PAYMENT_DISPLAY_NAME)) {
      String paymentIDLastFour = StringUtils.right(data.getString(UberConstants.PAYMENT_DISPLAY_NAME), 4);
      if (StringUtils.isNumeric(paymentIDLastFour)) {
        return paymentIDLastFour;
      }
    }

    return null;
  }
}
