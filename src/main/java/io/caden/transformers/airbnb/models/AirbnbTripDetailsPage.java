package io.caden.transformers.airbnb.models;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.text.StringEscapeUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import io.caden.transformers.airbnb.entities.AirbnbTripReservation;

public class AirbnbTripDetailsPage {
  private final Document document;

  public AirbnbTripDetailsPage(final Document document) {
    this.document = document;
  }

  public String getSiteContentHtml() {
    return this.document.selectFirst("#site-content").html();
  }

  public String getConfirmationCode() {
    return this.document.select("[data-testid='reservation-title-subtitle']")
        .stream().filter(node -> node.text().toLowerCase().contains("confirmation code"))
        .collect(Collectors.toList())
        .get(0)
        .child(0)
        .child(1)
        .text()
        .trim();
  }

  public Element getReservationsDate() {
    return this.document.selectFirst("[data-testid='reservations-split-title-subtitle-kicker-row']")
      .child(0)
      .child(0)
      .child(0);
  }

  public Element getCheckInReservation(final Element reservationsDate) {
    return reservationsDate.child(0).child(1).child(0);
  }

  public Element getCheckOutReservation(final Element reservationsDate) {
    return reservationsDate.child(1).child(1).child(0);
  }

  public Date getCheckInTime(final Element checkInReservation) throws ParseException {
    return new SimpleDateFormat("E, MMM d, yyyy h:m a").parse(
      String.format("%s %s", checkInReservation.child(0).text().trim(), checkInReservation.child(1).text().trim())
    );
  }

  public Date getCheckoutTime(final Element checkOutReservation) throws ParseException {
    return new SimpleDateFormat("E, MMM d, yyyy h:m a").parse(
      String.format("%s %s", checkOutReservation.child(0).text().trim(), checkOutReservation.child(1).text().trim())
    );
  }

  public AirbnbTripReservation getAirbnbTripReservation() throws Exception {
    AirbnbTripReservation airbnbTripReservation = new AirbnbTripReservation();

    Element avatarList = this.document.selectFirst("[data-testid='avatar-list-row']")
      .child(0)
      .child(0)
      .child(0)
      .child(1);

    List<Element> reservationSubtitles = this.document.select("[data-testid='reservation-title-subtitle']")
      .stream().filter(node -> node.text().toLowerCase().contains("cost"))
      .collect(Collectors.toList());

    if (!reservationSubtitles.isEmpty()) {
      Element totalCost = reservationSubtitles.get(0).child(0).child(1);

      String[] priceCurrencyTokens = totalCost.text().replaceAll("[0-9\\.]", "").trim().split(" ");

      airbnbTripReservation.setPriceCurrency(priceCurrencyTokens[priceCurrencyTokens.length - 1]);
      airbnbTripReservation.setTotalPrice(Double.parseDouble(totalCost.text().replaceAll("[^0-9\\.]", "").trim()));
    }

    airbnbTripReservation.setNumAdults(Integer.parseInt(
      avatarList.text().replaceAll("\\d+\\s*pet(s)*", "").replaceAll("\\D", "").trim()
    ));
    airbnbTripReservation.setReservationFor(this.getAddress());

    try {
      Element reservationsDate = this.getReservationsDate();

      Element checkInReservation = this.getCheckInReservation(reservationsDate);
      Element checkOutReservation = this.getCheckOutReservation(reservationsDate);

      airbnbTripReservation.setCheckinTime(this.getCheckInTime(checkInReservation));
      airbnbTripReservation.setCheckoutTime(this.getCheckoutTime(checkOutReservation));
    } catch (Exception e) {
      // TODO: handle exception
    }

    return airbnbTripReservation;
  }

  public String getRoomUrl() {
    return this.document.selectFirst("a[href^='/rooms/']").absUrl("href");
  }

  public String getRoomId() {
    String[] roomTokens = this.getRoomUrl().split("/");
    return roomTokens[roomTokens.length - 1];
  }

  public String getApiKey() {
    return getJSONData()
      .getJSONObject("layout-init")
      .getJSONObject("api_config")
      .getString("key");
  }

  public void fillFullAddress() {
    try {
      JSONObject reservations = getJSONData()
        .getJSONObject("reduxData")
        .getJSONObject("reservations");

      if (reservations != null) {
        JSONArray rows = reservations.getJSONObject(JSONObject.getNames(reservations)[0]).getJSONArray("rows");

        if (rows != null) {
          for (int i = 0; i < rows.length(); i++) {
            JSONObject row = rows.getJSONObject(i);
            String address = row.optString("address");
            if (!address.isBlank()) {
              this.document.select("[data-testid='reservation-map-row']")
                .stream().filter(node -> node.text().toLowerCase().contains("address"))
                .collect(Collectors.toList())
                .get(0)
                .child(0)
                .child(1)
                .text(address);
            }
          }
        }
      }
    } catch (Exception e) {
      // TODO: handle exception
    }
  }

  public void fillFullDates() {
    JSONObject reservations = getJSONData()
      .getJSONObject("reduxData")
      .getJSONObject("reservations");

    JSONObject metadata = reservations.getJSONObject(JSONObject.getNames(reservations)[0]).getJSONObject("metadata");

    String checkInYear = metadata.getString("check_in_date").split("-")[0];
    String checkOutYear = metadata.getString("check_out_date").split("-")[0];

    this.fillCheckInYear(checkInYear);
    this.fillCheckOutYear(checkOutYear);
  }

  private void fillCheckInYear(final String checkInYear) {
    Element checkInDate = this.getCheckInReservation(this.getReservationsDate()).child(0);
    checkInDate.text(checkInDate.text().trim() + ", " + checkInYear);
  }

  private void fillCheckOutYear(final String checkOutYear) {
    Element checkOutDate = this.getCheckOutReservation(this.getReservationsDate()).child(0);
    checkOutDate.text(checkOutDate.text().trim() + ", " + checkOutYear);
  }

  public String getAddress() {
    try {
      // As of 2023-3-7, Airbnb does not provide the adress on the reservation page
      // if the reservation is not an upcoming reservation
      Element reservation = this.document.select("[data-testid='reservation-map-row']")
        .stream().filter(node -> node.text().toLowerCase().contains("address"))
        .collect(Collectors.toList())
        .get(0)
        .child(0)
        .child(1);

      return StringEscapeUtils.unescapeHtml4(reservation.text().trim());
    } catch (Exception e) {
      return "";
    }
  }

  // TODO: handle schema changes
  private JSONObject getJSONData() {
    return new JSONObject(this.document.selectFirst("#data-state").html())
            .getJSONObject("bootstrapData");
  }
}
