package io.caden.transformers.airbnb.models;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONArray;
import org.json.JSONObject;

import io.caden.transformers.airbnb.entities.AirbnbTripReservationPlace;
import io.caden.transformers.airbnb.entities.AirbnbTripReservationPlaceRating;

public class AirbnbRoomPage {
  private final JSONObject jsonObject;
  private static final String SECTION_KEY = "section";
  private static final String METADATA_KEY = "metadata";
  private static final String TITLE_KEY = "title";

  public AirbnbRoomPage(final String jsonData) {
    this.jsonObject = new JSONObject(jsonData);
  }

  public boolean isAuthenticated() {
    return getSections() != null;
  }

  private JSONObject getSections() {
    return this.jsonObject.optJSONObject("data")
            .optJSONObject("presentation")
            .optJSONObject("stayProductDetailPage")
            .optJSONObject("sections");
  }

  public String getData() {
    return this.jsonObject.toString();
  }

  public JSONObject getSection(final String sectionId) {
    JSONArray sections = getSections().optJSONArray("sections");

    if (sections != null) {
      for (int i = 0; i < sections.length(); i++) {
        JSONObject section = sections.getJSONObject(i);
  
        if (section.getString("sectionId").equals(sectionId)) {
          return section;
        }
      }
    }

    return null;
  }

  public List<String> getAmenities() {
    JSONObject section = this.getSection("AMENITIES_DEFAULT");

    if (section != null) {
      List<String> result = new ArrayList<>();
      
      JSONArray amenities = section.getJSONObject(SECTION_KEY)
        .getJSONArray("previewAmenitiesGroups")
        .getJSONObject(0)
        .getJSONArray("amenities");

      for (int i = 0; i < amenities.length(); i++) {
        result.add(amenities.getJSONObject(i).getString(TITLE_KEY));
      }

      return result;
    }

    return Collections.emptyList();
  }

  public List<String> getHouseRules() {
    List<String> result = new ArrayList<>();
    JSONObject section = this.getSection("POLICIES_DEFAULT");

    if (section != null) {
      JSONArray houseRules = section.getJSONObject(SECTION_KEY).getJSONArray("houseRules");
      JSONArray houseRulesSections = section.getJSONObject(SECTION_KEY).optJSONArray("houseRulesSections");

      if (houseRules != null) {
        for (int i = 0; i < houseRules.length(); i++) {
          result.add(houseRules.getJSONObject(i).getString(TITLE_KEY));
        }
      }

      if (houseRulesSections != null) {
        for (int i = 0; i < houseRulesSections.length(); i++) {
          JSONArray items = houseRulesSections.getJSONObject(i).getJSONArray("items");

          if (items != null) {
            for (int j = 0; j < items.length(); j++) {
              result.add(items.getJSONObject(j).getString(TITLE_KEY));
            }
          }
        }
      }
    }

    return result;
  }

  public Date getCheckInTime(final List<String> houseRules) {
    return this.getTime(houseRules.stream().filter(x -> x.toLowerCase().contains("check-in")).findFirst());
  }

  public Date getCheckoutTime(final List<String> houseRules) {
    return this.getTime(houseRules.stream().filter(x -> x.toLowerCase().contains("checkout")).findFirst()); 
  }

  private Date getTime(final Optional<String> time) {
    try {
      Matcher matcher = Pattern.compile("\\d+:\\d+\\s(PM|AM)").matcher(time.get());

      if (matcher.find()) {
        return new SimpleDateFormat("hh:mm a").parse(matcher.group(0).trim());
      }
    } catch (Exception e) {
      // TODO: handle exception
    }

    return null;
  }

  public Integer getNumberOfRooms() {
    JSONObject section = this.getSection("OVERVIEW_DEFAULT");

    if (section != null) {
      JSONArray detailItems = section.getJSONObject(SECTION_KEY).getJSONArray("detailItems");

      if (detailItems != null) {
        for (int i = 0; i < detailItems.length(); i++) {
          String item = detailItems.getJSONObject(i).getString(TITLE_KEY);

          if (item.toLowerCase().contains("bedroom")) {
            return Integer.parseInt(item.replaceAll("\\D", "").trim());
          }
        }
      }
    }

    return null;
  }

  public Boolean arePetsAllowed(final List<String> houseRules) {
    return houseRules.stream().noneMatch(x -> x.toLowerCase().contains("no pets"));
  }

  public Boolean isSmokingAllowed(final List<String> houseRules) {
    return houseRules.stream().noneMatch(x -> x.toLowerCase().contains("no smoking"));
  }

  public Boolean arePartiesAllowed(final List<String> houseRules) {
    return houseRules.stream().noneMatch(x -> x.toLowerCase().contains("no parties"));
  }

  public Boolean areChildrenAllowed(final List<String> houseRules) {
    return houseRules.stream().noneMatch(x -> x.toLowerCase().matches("not suitable for (infants|children)"));
  }

  public AirbnbTripReservationPlaceRating getAirbnbTripReservationPlaceRating() {
    JSONObject section = this.getSection("REVIEWS_DEFAULT");

    if (section != null) {
      AirbnbTripReservationPlaceRating rating = new AirbnbTripReservationPlaceRating();

      Double overallRating = section.getJSONObject(SECTION_KEY).optDouble("overallRating");

      rating.setRatingValue(overallRating == null || Double.isNaN(overallRating) ? 0D : overallRating);
      rating.setRatingCount(section.getJSONObject(SECTION_KEY).getInt("overallCount"));

      return rating;
    }

    return null;
  }

  public String getName() {
    JSONObject section = this.getSection("TITLE_DEFAULT");

    if (section != null) {
      return section.getJSONObject(SECTION_KEY).getString(TITLE_KEY).trim();
    }

    return null;
  }

  public String getUrl() {
    return this.getSections()
      .optJSONObject(METADATA_KEY)
      .optJSONObject("sharingConfig")
      .optString("pdpLink");
  }

  public String getIdentifier() {
    try {
      return this.getSections()
        .optJSONObject(METADATA_KEY)
        .optJSONObject("clientLoggingContext")
        .optString("productId");
    } catch (Exception ex) {
      String[] tokens = this.getUrl().split("/");
      return tokens[tokens.length - 1];
    }
  }

  public Boolean isSuperhost() {
    return this.getSections()
      .optJSONObject(METADATA_KEY)
      .optJSONObject("loggingContext")
      .optJSONObject("eventDataLogging")
      .optBoolean("isSuperhost");
  }

  public Integer getNumberOfAllowedGuests() {
    JSONObject section = this.getSection("OVERVIEW_DEFAULT");

    if (section != null) {
      JSONArray detailItems = section.getJSONObject(SECTION_KEY).getJSONArray("detailItems");

      if (detailItems != null) {
        for (int i = 0; i < detailItems.length(); i++) {
          String item = detailItems.getJSONObject(i).getString(TITLE_KEY);

          if (item.toLowerCase().contains("guest")) {
            return Integer.parseInt(item.replaceAll("\\D", "").trim());
          }
        }
      }
    }

    return null;
  }

  public String getAddress() {
    JSONObject section = this.getSection("LOCATION_DEFAULT");

    if (section != null) {
      String address = section.getJSONObject(SECTION_KEY).optString("subtitle");

      if (!address.isBlank()) {
        return address.trim();
      }

      JSONObject titleSection = this.getSection("TITLE_DEFAULT");

      if (titleSection != null) {
        JSONArray overviewItems = titleSection.getJSONObject(SECTION_KEY).getJSONArray("overviewItems");
        
        for (int i = 0; i < overviewItems.length(); i++) {
          JSONObject overviewItem = overviewItems.getJSONObject(i);
          JSONObject action = overviewItem.optJSONObject("action");

          if (action != null && action.getString("__typename").equalsIgnoreCase("NavigateToMap")) {
            return overviewItem.getString(TITLE_KEY).trim();
          }
        }
      }
    }

    return null;
  }

  public AirbnbTripReservationPlace getAirbnbTripReservationPlace() {
    if (!this.isAuthenticated()) {
      return null;  
    }

    AirbnbTripReservationPlace airbnbTripReservationPlace = new AirbnbTripReservationPlace();

    List<String> houseRules = this.getHouseRules();

    airbnbTripReservationPlace.setAmenityFeature(String.join("þ", this.getAmenities()));
    airbnbTripReservationPlace.setCheckinTime(this.getCheckInTime(houseRules));
    airbnbTripReservationPlace.setCheckoutTime(this.getCheckoutTime(houseRules));
    airbnbTripReservationPlace.setNumberOfRooms(this.getNumberOfRooms());
    airbnbTripReservationPlace.setPetsAllowed(this.arePetsAllowed(houseRules));
    airbnbTripReservationPlace.setAirbnbTripReservationPlaceRatings(
      new HashSet<AirbnbTripReservationPlaceRating>(Arrays.asList(this.getAirbnbTripReservationPlaceRating()))
    );
    airbnbTripReservationPlace.setAddress(this.getAddress());
    airbnbTripReservationPlace.setName(this.getName());
    airbnbTripReservationPlace.setIdentifier(this.getIdentifier());
    airbnbTripReservationPlace.setSameAs(this.getUrl());
    airbnbTripReservationPlace.setCertifiedHost(this.isSuperhost());
    airbnbTripReservationPlace.setNumberOfAllowedGuests(this.getNumberOfAllowedGuests());
    airbnbTripReservationPlace.setSmokingAllowed(this.isSmokingAllowed(houseRules));
    airbnbTripReservationPlace.setPartiesAllowed(this.arePartiesAllowed(houseRules));
    airbnbTripReservationPlace.setChildrenAllowed(this.areChildrenAllowed(houseRules));

    return airbnbTripReservationPlace;
  }
}
