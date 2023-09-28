package io.caden.transformers.shared.utils;

public final class SchemaBaseConstants {
  private SchemaBaseConstants() {
    throw new IllegalStateException("Utility class");
  }

  public final class Class {
    private Class() {
      throw new IllegalStateException("Utility class");
    }
    public static final String WATCH_ACTION = "WatchAction";
    public static final String MOVIE = "Movie";
    public static final String TV_EPISODE = "TVEpisode";
    public static final String TV_SERIES = "TVSeries";
    public static final String AGGREGATE_RATING = "AggregateRating";
    public static final String MUSIC_GROUP = "MusicGroup";
    public static final String MUSIC_ALBUM = "MusicAlbum";
    public static final String MUSIC_RECORDING = "MusicRecording";
    public static final String PODCAST_EPISODE = "PodcastEpisode";
    public static final String GEO_COORDINATES = "GeoCoordinates";
    public static final String POSTAL_ADDRESS = "PostalAddress";
    public static final String STORE = "Store";
    public static final String TAXI_RESERVATION = "TaxiReservation";
    public static final String ORDER_ITEM = "OrderItem";
    public static final String PARCEL_DELIVERY = "ParcelDelivery";
    public static final String PRODUCT = "Product";
    public static final String CATEGORY_CODE = "CategoryCode";
    public static final String MOVE_ACTION = "MoveAction";
  }

  public final class Property {
    private Property() {
      throw new IllegalStateException("Utility class");
    }
    public static final String BIRTH_DATE = "birthDate";
    public static final String START_TIME = "startTime";
    public static final String NAME = "name";
    public static final String IDENTIFIER = "identifier";
    public static final String CHECKIN_TIME = "checkinTime";
    public static final String CHECKOUT_TIME = "checkoutTime";
    public static final String NUM_ADULTS = "numAdults";
    public static final String PRICE_CURRENCY = "priceCurrency";
    public static final String RESERVATION_ID = "reservationId";
    public static final String ADDRESS = "address";
    public static final String AMENITY_FEATURE = "amenityFeature";
    public static final String NUMBER_OF_ROOMS = "numberOfRooms";
    public static final String PETS_ALLOWED = "petsAllowed";
    public static final String TOTAL_PRICE = "totalPrice";
    public static final String RATING_VALUE = "ratingValue";
    public static final String RATING_COUNT = "ratingCount";
    public static final String SAME_AS = "sameAs";
    public static final String PRICE = "price";
    public static final String DURATION = "duration";
    public static final String IS_FAMILY_FRIENDLY = "isFamilyFriendly";
    public static final String NUM_TRACKS = "numTracks";
    public static final String GENRE = "genre";
    public static final String INTERACTION_STATISTIC = "interactionStatistic";
    public static final String DESCRIPTION = "description";
    public static final String IMAGE = "image";
    public static final String SD_DATE_PUBLISHED = "sdDatePublished";
    public static final String DISTANCE = "distance";
    public static final String EXERCISE_TYPE = "exerciseType";
    public static final String END_TIME = "endTime";
    public static final String LATITUDE = "latitude";
    public static final String LONGITUDE = "longitude";
    public static final String URL = "url";
    public static final String LOGO = "logo";
    public static final String TELEPHONE = "telephone";
    public static final String STREET_ADDRESS = "streetAddress";
    public static final String ADDRESS_LOCALITY = "addressLocality";
    public static final String ADDRESS_REGION = "addressRegion";
    public static final String ADDRESS_COUNTRY = "addressCountry";
    public static final String POSTAL_CODE = "postalCode";
    public static final String ORDER_NUMBER = "orderNumber";
    public static final String ORDER_DATE = "orderDate";
    public static final String BILLING_ADDRESS = "billingAddress";
    public static final String ORDER_QUANTITY = "orderQuantity";
    public static final String DELIVERY_ADDRESS = "deliveryAddress";
    public static final String TRACKING_NUMBER = "trackingNumber";
    public static final String RATING = "aggregateRating";
    public static final String RATINGS_TOTAL = "ratingCount";
    public static final String BRAND = "brand";
    public static final String COLOR = "color";
    public static final String GTIN = "gtin";
    public static final String MANUFACTURER = "manufacturer";
    public static final String MODEL = "model";
    public static final String WEIGHT = "weight";
    public static final String FIRST_AVAILABLE = "releaseDate";
    public static final String SIZE = "size";
    public static final String IS_RELATED_TO = "isSimilarTo";
    public static final String KEYWORDS = "keywords";
    public static final String HAS_ENERGY_CONSUMPTION_DETAILS = "hasEnergyConsumptionDetails";
    public static final String CODE_VALUE = "codeValue";
    public static final String POPULARITY = "popularity";
  }

  public final class Relationship {
    private Relationship() {
      throw new IllegalStateException("Utility class");
    }
    public static final String RESERVATION_FOR = "reservationFor";
    public static final String AGGREGATE_RATING = "aggregateRating";
    public static final String RESERVATION_STATUS = "reservationStatus";
    public static final String UNDER_NAME = "underName";
    public static final String AGENT = "agent";
    public static final String OBJECT = "object";
    public static final String BY_ARTIST = "byArtist";
    public static final String IN_ALBUM = "inAlbum";
    public static final String ALBUM_RELEASE_TYPE = "albumReleaseType";
    public static final String SELLER = "seller";
    public static final String RECIPIENT = "recipient";
    public static final String ADDRESS = "address";
    public static final String PARENT_ORGANIZATION = "parentOrganization";
    public static final String SUB_ORGANIZATION = "subOrganization";
    public static final String PICKUP_LOCATION = "pickupLocation";
    public static final String DROPOFF_LOCATION = "dropoffLocation";
    public static final String PART_OF_SERIES = "partOfSeries";
    public static final String EPISODE = "episode";
    public static final String BROKER = "broker";
    public static final String ORDER_STATUS = "orderStatus";
    public static final String ORDERED_ITEM = "orderedItem";
    public static final String ORDER_DELIVERY = "orderDelivery";
    public static final String HAS_DELIVERY_METHOD = "hasDeliveryMethod";
    public static final String CUSTOMER = "customer";
    public static final String CATEGORY = "category";
    public static final String FROM_LOCATION = "fromLocation";
    public static final String TO_LOCATION = "toLocation";
  }

  public final class AmazonOrderStatus {
    private AmazonOrderStatus() {
      throw new IllegalStateException("Utility class");
    }
    public static final String ORDER_CANCELLED = "OrderCancelled";
    public static final String ORDER_DELIVERED = "OrderDelivered";
    public static final String ORDER_IN_TRANSIT = "OrderInTransit";
    public static final String ORDER_PAYMENT_DUE = "OrderPaymentDue";
    public static final String ORDER_PICKUP_AVAILABLE = "OrderPickupAvailable";
    public static final String ORDER_PROBLEM = "OrderProblem";
    public static final String ORDER_PROCESSING = "OrderProcessing";
    public static final String ORDER_RETURNED = "OrderReturned";
    public static final String ORDER_REFUNDED = "OrderRefunded";
    public static final String ORDER_EXCHANGED = "OrderExchanged";
  }
}
