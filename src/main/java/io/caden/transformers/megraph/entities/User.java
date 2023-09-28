package io.caden.transformers.megraph.entities;

import java.util.Collection;
import java.util.Date;
import java.util.Set;
import java.util.UUID;

import io.caden.transformers.megraph.enums.EducationLevel;
import io.caden.transformers.megraph.enums.EmploymentStatus;
import io.caden.transformers.megraph.enums.Ethnicity;
import io.caden.transformers.megraph.enums.Gender;
import io.caden.transformers.megraph.enums.Relationship;
import io.caden.transformers.megraph.enums.Sexuality;
import io.caden.transformers.megraph.enums.YearlyHouseholdIncome;
import io.caden.transformers.shared.entities.RDFAbstractEntity;

public class User extends RDFAbstractEntity {
  private UUID cadenAlias;

  private Gender gender;

  private Date birthDate;

  private String homeLocation;

  private Set<Ethnicity> ethnicities;

  private Boolean hispanicLatinoOrigin;

  private Relationship relationship;

  private Sexuality sexuality;

  private EducationLevel educationLevel;

  private EmploymentStatus employmentStatus;

  private YearlyHouseholdIncome yearlyHouseholdIncome;

  private Double yearlySalary;

  private String workLocation;

  private Integer children;

  public UUID getCadenAlias() {
    return cadenAlias;
  }

  public void setCadenAlias(UUID cadenAlias) {
    this.cadenAlias = cadenAlias;
  }

  public Gender getGender() {
    return this.gender;
  }

  public void setGender(final Gender gender) {
    this.gender = gender;
  }

  public Date getBirthDate() {
    return this.birthDate;
  }

  public void setBirthDate(final Date birthDate) {
    this.birthDate = birthDate;
  }

  public String getHomeLocation() {
    return this.homeLocation;
  }

  public void setHomeLocation(final String homeLocation) {
    this.homeLocation = homeLocation;
  }

  public Collection<Ethnicity> getEthnicities() {
    return this.ethnicities;
  }

  public void setEthnicities(final Set<Ethnicity> ethnicities) {
    this.ethnicities = ethnicities;
  }

  public Boolean isHispanicLatinoOrigin() {
    return this.hispanicLatinoOrigin;
  }

  public void setHispanicLatinoOrigin(final Boolean hispanicLatinoOrigin) {
    this.hispanicLatinoOrigin = hispanicLatinoOrigin;
  }

  public Relationship getRelationship() {
    return this.relationship;
  }

  public void setRelationship(final Relationship relationship) {
    this.relationship = relationship;
  }

  public Sexuality getSexuality() {
    return this.sexuality;
  }

  public void setSexuality(final Sexuality sexuality) {
    this.sexuality = sexuality;
  }

  public Integer getChildren() {
    return this.children;
  }

  public void setChildren(final Integer children) {
    this.children = children;
  }

  public EducationLevel getEducationLevel() {
    return this.educationLevel;
  }

  public void setEducationLevel(final EducationLevel educationLevel) {
    this.educationLevel = educationLevel;
  }

  public EmploymentStatus getEmploymentStatus() {
    return this.employmentStatus;
  }

  public void setEmploymentStatus(final EmploymentStatus employmentStatus) {
    this.employmentStatus = employmentStatus;
  }

  public String getWorkLocation() {
    return this.workLocation;
  }

  public void setWorkLocation(final String workLocation) {
    this.workLocation = workLocation;
  }

  public YearlyHouseholdIncome getYearlyHouseholdIncome() {
    return this.yearlyHouseholdIncome;
  }

  public void setYearlyHouseholdIncome(final YearlyHouseholdIncome yearlyHouseholdIncome) {
    this.yearlyHouseholdIncome = yearlyHouseholdIncome;
  }

  public Double getYearlySalary() {
    return this.yearlySalary;
  }

  public void setYearlySalary(final Double yearlySalary) {
    this.yearlySalary = yearlySalary;
  }
}
