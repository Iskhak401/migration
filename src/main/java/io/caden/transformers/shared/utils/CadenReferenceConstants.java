package io.caden.transformers.shared.utils;

public final class CadenReferenceConstants {
  private CadenReferenceConstants() {
    throw new IllegalStateException("Utility class");
  }

  //Constant Caden user values that are used throughout application

  public final class EducationLevel {
    private EducationLevel() {
      throw new IllegalStateException("Utility class");
    }
    public static final String ASSOCIATE_DEGREE = "Associate_Degree";
    public static final String BACHELOR_DEGREE = "Bachelor_Degree";
    public static final String DOCTORATE_DEGREE = "Doctorate_Degree";
    public static final String HIGH_SCHOOL_DIPLOMA_OR_EQUIVALENT = "High_School_Diploma_Or_Equivalent";
    public static final String MASTER_DEGREE = "Master_Degree";
    public static final String NO_FORMAL_SCHOOLING = "No_Formal_Schooling";
    public static final String PROFESSIONAL_DEGREE = "Professional_Degree";
    public static final String SOME_SCHOOL_GRADE = "Some_School_Grade";
    public static final String VOCATIONAL_TRAINING = "Vocational_Training";
  }

  public final class SexualOrientation {
    private SexualOrientation() {
      throw new IllegalStateException("Utility class");
    }
    public static final String ASEXUAL = "Asexual";
    public static final String BISEXUAL = "Bisexual";
    public static final String GAY = "Gay";
    public static final String LESBIAN = "Lesbian";
    public static final String PANSEXUAL = "Pansexual";
    public static final String STRAIGHT = "Straight";
  }

  public final class RelationshipStatusType {
    private RelationshipStatusType() {
      throw new IllegalStateException("Utility class");
    }
    public static final String DOMESTIC_PARTNERSHIP = "Domestic_Partnership";
    public static final String IN_RELATIONSHIP = "In_Relationship";
    public static final String MARRIED = "Married";
    public static final String SINGLE = "Single";
  }

  public final class EmploymentStatusEnumeration {
    private EmploymentStatusEnumeration() {
      throw new IllegalStateException("Utility class");
    }
    public static final String FULL_TIME_EMPLOYMENT = "Full-time_Employment";
    public static final String PART_TIME_EMPLOYMENT = "Part-time_Employment";
    public static final String SELF_EMPLOYED = "Self_Employed";
    public static final String UNEMPLOYED = "Unemployed";
  }

  public final class Ethnicity {
    private Ethnicity() {
      throw new IllegalStateException("Utility class");
    }
    public static final String AMERICAN_INDIAN_OR_ALASKA_NATIVE = "American_Indian_or_Alaska_Native";
    public static final String ASIAN = "Asian";
    public static final String BLACK_OR_AFRICAN_AMERICAN = "Black_or_African_American";
    public static final String HISPANIC_OR_LATINO = "Hispanic_or_Latino";
    public static final String NATIVE_HAWAIIAN_OR_OTHER_PACIFIC_ISLANDER = "Native_Hawaiian_or_Other_Pacific_Islander";
    public static final String WHITE = "White";
    public static final String OTHER = "Other";
  }

  public final class Gender {
    private Gender() {
      throw new IllegalStateException("Utility class");
    }
    public static final String FEMALE = "Female";
    public static final String MALE = "Male";
    public static final String NONBINARY = "Non-binary";
    public static final String NO_GENDER_SPECIFIED = "No_Gender_Specified";
  }
}
