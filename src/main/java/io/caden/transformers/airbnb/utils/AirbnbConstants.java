package io.caden.transformers.airbnb.utils;

public final class AirbnbConstants {

  private AirbnbConstants() {
    throw new IllegalStateException("Utility class");
  }

  public static final String AIRBNB_EXTRACTION = "AirbnbExtraction";
  public static final String INSTANCE_AIRBNB_EXTRACTION = "airbnbExtraction";
  public static final String LODGING_RESERVATION = "LodgingReservation";
  public static final String LODGING_BUSINESS = "LodgingBusiness";
  
  public static final String EXTRACTION_DATE = "extractionDate";
  public static final String TRANSFORMATION_DATE = "transformationDate";
  public static final String IS_CERTIFIED_HOST = "isCertifiedHost";
  public static final String NUM_GUESTS_ALLOWED = "numGuestsAllowed";
  public static final String SMOKING_ALLOWED = "smokingAllowed";
  public static final String PARTIES_ALLOWED = "partiesAllowed";
  public static final String CHILDREN_ALLOWED = "childrenAllowed";
  public static final String TAX = "tax";
  public static final String SERVICE_FEE = "serviceFee";

  public static final String HAS_AIRBNB_EXTRACTION = "hasAirbnbExtraction";  
}
