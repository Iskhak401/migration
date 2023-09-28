package io.caden.transformers.megraph.mappers;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import io.caden.transformers.megraph.dtos.UserGraphDto;
import io.caden.transformers.megraph.entities.User;
import io.caden.transformers.megraph.enums.EducationLevel;
import io.caden.transformers.megraph.enums.EmploymentStatus;
import io.caden.transformers.megraph.enums.Ethnicity;
import io.caden.transformers.megraph.enums.Gender;
import io.caden.transformers.megraph.enums.Relationship;
import io.caden.transformers.megraph.enums.Sexuality;
import io.caden.transformers.megraph.enums.YearlyHouseholdIncome;
import io.caden.transformers.shared.mappers.Mapper;

@Component
public class UserGraphDtoToUserMapper extends Mapper<UserGraphDto, User> {

  @Override
  public User map(UserGraphDto value) {
    try {
      if (value == null) {
        return null;
      }
  
      User user = new User();
      user.setUuid(UUID.randomUUID().toString());
      user.setCadenAlias(UUID.fromString(value.getCadenAlias()));

      if (value.getMeGraph().getGender() != null) {
        user.setGender(Gender.valueOf(value.getMeGraph().getGender()));
      }

      if (value.getMeGraph().getBirthDate() != null) {
        user.setBirthDate(new SimpleDateFormat("dd-MM-yyyy").parse(value.getMeGraph().getBirthDate()));
      }

      if (value.getMeGraph().getHomeLocation() != null) {
        user.setHomeLocation(value.getMeGraph().getHomeLocation());
      }

      if (value.getMeGraph().getEthnicities() != null) {
        user.setEthnicities(
          Arrays.stream(value.getMeGraph().getEthnicities())
            .map(ethnicity -> Ethnicity.valueOf(ethnicity))
            .collect(Collectors.toSet())
        );
      }

      if (value.getMeGraph().getHispanicLatinoOrigin() != null) {
        user.setHispanicLatinoOrigin(value.getMeGraph().getHispanicLatinoOrigin());
      }

      if (value.getMeGraph().getRelationshipStatus() != null) {
        user.setRelationship(Relationship.valueOf(value.getMeGraph().getRelationshipStatus()));
      }

      if (value.getMeGraph().getSexualOrientation() != null) {
        user.setSexuality(Sexuality.valueOf(value.getMeGraph().getSexualOrientation()));
      }

      if (value.getMeGraph().getHighestEducation() != null) {
        user.setEducationLevel(EducationLevel.valueOf(value.getMeGraph().getHighestEducation()));
      }

      if (value.getMeGraph().getEmploymentStatus() != null) {
        user.setEmploymentStatus(EmploymentStatus.valueOf(value.getMeGraph().getEmploymentStatus()));
      }

      if (value.getMeGraph().getHouseholdIncome() != null) {
        user.setYearlyHouseholdIncome(YearlyHouseholdIncome.valueOf(value.getMeGraph().getHouseholdIncome()));
      }

      if (value.getMeGraph().getAnnualSalary() != null && value.getMeGraph().getAnnualSalary().matches("-?\\d+(\\.\\d+)?")) {
        user.setYearlySalary(Double.valueOf(value.getMeGraph().getAnnualSalary()));
      }

      if (value.getMeGraph().getWorkLocation() != null) {
        user.setWorkLocation(value.getMeGraph().getWorkLocation());
      }

      if (value.getMeGraph().getNumChildren() != null && value.getMeGraph().getNumChildren().matches("-?\\d+(\\.\\d+)?")) {
        user.setChildren(Integer.valueOf(value.getMeGraph().getNumChildren()));
      }

      return user;
    } catch (Exception e) {
      return null;
    }
  }

  @Override
  public UserGraphDto reverseMap(User value) {
    throw new UnsupportedOperationException("Not implemented exception");
  }
}
