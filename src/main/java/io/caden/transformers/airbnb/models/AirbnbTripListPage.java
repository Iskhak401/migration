package io.caden.transformers.airbnb.models;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import io.caden.transformers.airbnb.utils.AirbnbDateUtil;
import org.apache.commons.text.StringEscapeUtils;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class AirbnbTripListPage {

  private final Document document;

  public AirbnbTripListPage(final Document document) {
    this.document = document;
    this.removeCanceledTrips();
  }

  public boolean isAuthenticated() {
    return !this.document.title().toLowerCase().contains("log in");
  }

  private void removeCanceledTrips() {
    Element canceledTrips = this.document.selectFirst("[data-section-id='CANCELED_TRIPS']");
    if (canceledTrips != null) {
      canceledTrips.remove();
    }
  }

  public List<String> getTripsUrl() {
    return this.document.select("a[href^='/trips/v1/']").stream()
      .map(trip -> trip.absUrl("href"))
      .filter(url -> !url.contains("EXPERIENCE_RESERVATION"))
      .collect(Collectors.toList());
  }

  public String getSiteContentHtml() {
    return this.document.selectFirst("#site-content").html();
  }

  public Date getLastCheckinDate() throws Exception {
    return this.getTripDateRange(this.document.selectFirst("a[href^='/trips/v1/']")).get(0);
  }

  public List<Date> getTripDateRange(final String confirmationCode) throws Exception {
    return this.getTripDateRange(this.document.selectFirst("a[href$='" + confirmationCode + "']"));
  }

  public List<Date> getTripDateRange(final Element element) throws Exception {
    Element tripInfo = element.child(0).child(1);
    String text;

    if (tripInfo.childrenSize() == 1) {
      // Today box
      text = String.join(", ", tripInfo.child(0).child(1).child(0).children().stream().map(Element::text).collect(Collectors.toList()));
    } else {
      // Where you’ve been box
      text = tripInfo.child(2).text();
    }

    text = StringEscapeUtils.unescapeHtml4(text.replaceAll("[^,\\d\\w]{2,}", "–").trim());

    return AirbnbDateUtil.parseDateRange(text);
  }
}
