package io.caden.transformers.shared.utils;

public final class CadenBaseConstants {
  private CadenBaseConstants() {
    throw new IllegalStateException("Utility class");
  }

  // Constant base Caden values that are used throughout application

  public static final String USER = "User";
  public static final String INSTANCE_USER = "user";
  public static final String UUID = "uuid";
  public static final String COGNITO_USER_NAME = "cognitoUserName";
  public static final String USER_NAME = "userName";
  public static final String CHANGE_EMAIL = "changeEmail";
  public static final String CREATED_AT = "createdAt";
  public static final String UPDATED_AT = "updatedAt";
  public static final String LAST_RECONNECTION_DATE = "lastReconnectionDate";
  public static final String IRI = "iri";
  public static final String INVITATION_CODE = "invitationCode";
  public static final String EMAIL = "email";
  public static final String REFER_CODE = "referCode";
  public static final String FIRST_NAME = "firstName";
  public static final String LAST_NAME = "lastName";
  public static final String GENDER = "gender";
  public static final String ETHNICITY = "ethnicity";
  public static final String RELATIONSHIP = "relationship";
  public static final String SEXUALITY = "sexuality";
  public static final String CHILDREN = "children";
  public static final String BIRTH_DATE = "birthDate";
  public static final String HOME_LOCATION = "homeLocation";
  public static final String EDUCATION_LEVEL = "educationLevel";
  public static final String OCCUPATION = "occupation";
  public static final String WORK_LOCATION = "workLocation";
  public static final String WORK_REMOTELY = "workRemotely";
  public static final String YEARLY_SALARY = "yearlySalary";
  public static final String YEARLY_HOUSEHOLD_INCOME = "yearlyHouseholdIncome";
  public static final String HEIGHT = "height";
  public static final String WEIGHT = "weight";
  public static final String ACTIVITY_LEVEL = "activityLevel";
  public static final String HEART_RATE_AVERAGE = "heartRateAverage";
  public static final String SLEEP_QUALITY = "sleepQuality";
  public static final String PHONE_NUMBER = "phoneNumber";
  public static final String FAVORITE_MUSIC_TYPES = "favoriteMusicTypes";
  public static final String FAVORITE_SHOW_TYPES = "favoriteShowTypes";
  public static final String CADEN_ALIAS = "cadenAlias";
  public static final String NUM_CHILDREN = "numChildren";
  public static final String ANNUAL_SALARY = "annualSalary";
  public static final String HOUSEHOLD_INCOME = "householdIncome";

  public static final String RELATIONSHIP_STATUS = "RelationshipStatus";
  public static final String EMPLOYMENT_STATUS = "EmploymentStatus";
  public static final String WATCH_ACTION = "WatchAction";
  public static final String BUY_ACTION = "BuyAction";
  public static final String PAY_ACTION = "PayAction";
  public static final String DONATE_ACTION = "DonateAction";
  public static final String MX_MERCHANT = "MXMerchant";

  public static final String HAS_EDUCATION_LEVEL = "hasEducationLevel";
  public static final String HAS_SEXUALITY = "hasSexuality";
  public static final String HAS_RELATIONSHIP_STATUS = "hasRelationshipStatus";
  public static final String HAS_EMPLOYMENT_STATUS = "hasEmploymentStatus";
  public static final String HAS_ETHNICITY = "hasEthnicity";
  public static final String HAS_GENDER = "hasGender";

  public static final String STATUS_RECORD_DATE = "statusRecordDate";
  public static final String RELATIONSHIP_STATUS_TYPE = "relationshipStatusType";
  public static final String EMPLOYMENT_STATUS_TYPE = "employmentStatusType";
  public static final String SOURCE_INTAKE = "sourceIntake";
  public static final String TRANSACTION_CATEGORY = "transactionCategory";
  public static final String TRANSACTION_CATEGORY_ID = "transactionCategoryId";
  public static final String TOP_TRANSACTION_CATEGORY = "topTransactionCategory";
  public static final String BANK_TRANSACTION_ID = "bankTransactionId";
  public static final String IS_BILL_PAY = "isBillPay";
  public static final String IS_DIRECT_DEPOSIT = "isDirectDeposit";
  public static final String IS_EXPENSE = "isExpense";
  public static final String IS_FEE = "isFee";
  public static final String IS_INCOME = "isIncome";
  public static final String IS_INTERNATIONAL = "isInternational";
  public static final String IS_OVERDRAFT_FEE = "isOverdraftFee";
  public static final String IS_PAYROLL_ADVANCE = "isPayrollAdvance";
  public static final String IS_RECURRING = "isRecurring";
  public static final String IS_SUBSCRIPTION = "isSubscription";
  public static final String PAYMENT_ID_LAST_FOUR = "paymentIdLastFour";
  public static final String LOCATION = "location";
  public static final String IS_CREDIT = "isCredit";

  public static final String CONNECTION = "Connection";
  public static final String INSTANCE_CONNECTION = "connection";
  public static final String HAS_CONNECTION = "hasConnection";
  public static final String DISABLED = "disabled";
  public static final String EXPIRATION = "expiration";
  public static final String IDENTITY = "identity";
  public static final String CODE = "code";
  public static final String USER_AGENT = "userAgent";

  public static final String CREDENTIAL = "Credential";
  public static final String INSTANCE_CREDENTIAL = "credential";
  public static final String HAS_CREDENTIAL = "hasCredential";
  public static final String EXTERNAL_ID = "externalId";
  public static final String DISCONNECT_REASON = "disconnectReason";
  public static final String OFFLINE_CODE = "offlineCode";
  public static final String TOKEN = "token";

  public static final String EXTRACTION = "Extraction";
  public static final String INSTANCE_EXTRACTION = "extraction";
  public static final String LAST_MESSAGE_READ_ID = "lastMessageReadId";
  public static final String RESULT = "result";
  public static final String NOTES = "notes";
  public static final String HAS_EXTRACTION = "hasExtraction";

  public static final String ACCOUNT_LOG = "AccountLog";
  public static final String INSTANCE_ACCOUNT_LOG = "accountLog";
  public static final String HAS_ACCOUNT_LOG = "hasAccountLog";
  public static final String ACCESS_DATE = "accessDate";
  public static final String SUCCESS = "success";

  public static final class Intake {
    private Intake() {
      throw new IllegalStateException("Utility class");
    }
    public static final String AIRBNB = "AirBnb";
    public static final String AMAZON = "Amazon";
    public static final String APPLE_HEALTH_KIT = "AppleHealthKit";
    public static final String APPLE_STK = "AppleSTK";
    public static final String MX = "MX";
    public static final String NETFLIX = "Netflix";
    public static final String SPOTIFY = "Spotify";
    public static final String UBER = "Uber";
    public static final String LOCATION = "Location";
  }
}
