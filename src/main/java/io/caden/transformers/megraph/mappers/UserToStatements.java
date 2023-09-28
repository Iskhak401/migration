package io.caden.transformers.megraph.mappers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.eclipse.rdf4j.model.Statement;
import org.eclipse.rdf4j.model.impl.SimpleValueFactory;
import org.eclipse.rdf4j.model.util.Values;
import org.eclipse.rdf4j.model.vocabulary.RDF;
import org.eclipse.rdf4j.model.vocabulary.RDFS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import io.caden.transformers.config.RDFConfiguration;
import io.caden.transformers.megraph.entities.User;
import io.caden.transformers.megraph.enums.EducationLevel;
import io.caden.transformers.megraph.enums.Sexuality;
import io.caden.transformers.megraph.enums.Relationship;
import io.caden.transformers.megraph.enums.EmploymentStatus;
import io.caden.transformers.megraph.enums.Ethnicity;
import io.caden.transformers.megraph.enums.Gender;
import io.caden.transformers.shared.mappers.Mapper;
import io.caden.transformers.shared.utils.CadenBaseConstants;
import io.caden.transformers.shared.utils.CadenReferenceConstants;
import io.caden.transformers.shared.utils.OwlConstants;
import io.caden.transformers.shared.utils.RDFNamingConventionUtil;
import io.caden.transformers.shared.utils.SchemaBaseConstants;

@Component
public class UserToStatements extends Mapper<User, Collection<Statement>> {

  private final RDFConfiguration config;
  private final SimpleValueFactory svf = SimpleValueFactory.getInstance();

  public UserToStatements(
    @Autowired final RDFConfiguration config
  ) {
    this.config = config;
  }

  @Override
  public Collection<Statement> map(final User user) {
    String cadenAlias = user.getCadenAlias().toString();
    String graphName = RDFNamingConventionUtil.generateUserGraphName(cadenAlias);

    List<Statement> statements =  new ArrayList<>(Arrays.asList(
      svf.createStatement(config.getCadenBaseDataIRI(graphName), RDF.TYPE, config.getCadenBaseIRI(CadenBaseConstants.USER)),
      svf.createStatement(config.getCadenBaseDataIRI(graphName), RDF.TYPE, config.getOwlIRI(OwlConstants.NAMED_INDIVIDUAL)),
      svf.createStatement(config.getCadenBaseDataIRI(graphName), RDFS.LABEL, Values.literal(cadenAlias)),
      svf.createStatement(config.getCadenBaseDataIRI(graphName), config.getCadenBaseIRI(CadenBaseConstants.CADEN_ALIAS), Values.literal(cadenAlias))
    ));

    if (user.getBirthDate() != null) {
      statements.add(svf.createStatement(
        config.getCadenBaseDataIRI(graphName),
        config.getSchemaIRI(SchemaBaseConstants.Property.BIRTH_DATE),
        Values.literal(user.getBirthDate())
      ));
    }

    if (user.getHomeLocation() != null) {
      statements.add(svf.createStatement(
        config.getCadenBaseDataIRI(graphName),
        config.getCadenBaseIRI(CadenBaseConstants.HOME_LOCATION),
        Values.literal(user.getHomeLocation())
      ));
    }

    if (user.getWorkLocation() != null) {
      statements.add(svf.createStatement(
        config.getCadenBaseDataIRI(graphName),
        config.getCadenBaseIRI(CadenBaseConstants.WORK_LOCATION),
        Values.literal(user.getWorkLocation())
      ));
    }

    if (user.getChildren() != null) {
      statements.add(svf.createStatement(
        config.getCadenBaseDataIRI(graphName),
        config.getCadenBaseIRI(CadenBaseConstants.NUM_CHILDREN),
        Values.literal(user.getChildren())
      ));
    }

    if (user.getYearlySalary() != null) {
      statements.add(svf.createStatement(
        config.getCadenBaseDataIRI(graphName),
        config.getCadenBaseIRI(CadenBaseConstants.ANNUAL_SALARY),
        Values.literal(user.getYearlySalary())
      ));
    }

    if (user.getYearlyHouseholdIncome() != null) {
      statements.add(svf.createStatement(
        config.getCadenBaseDataIRI(graphName),
        config.getCadenBaseIRI(CadenBaseConstants.HOUSEHOLD_INCOME),
        Values.literal(user.getYearlyHouseholdIncome().label)
      ));
    }

    if (user.getEducationLevel() != null) {
      String educationLevelInstance = this.mapEducationLevel(user.getEducationLevel());

      if (educationLevelInstance != null) {
        statements.add(svf.createStatement(
          config.getCadenBaseDataIRI(graphName),
          config.getCadenBaseIRI(CadenBaseConstants.HAS_EDUCATION_LEVEL),
          config.getCadenReferenceIRI(educationLevelInstance)
        ));  
      }
    }

    if (user.getSexuality() != null) {
      String sexualityInstance = this.mapSexuality(user.getSexuality());

      if (sexualityInstance != null) {
        statements.add(svf.createStatement(
          config.getCadenBaseDataIRI(graphName),
          config.getCadenBaseIRI(CadenBaseConstants.HAS_SEXUALITY),
          config.getCadenReferenceIRI(sexualityInstance)
        ));
      }
    }

    if (user.getRelationship() != null) {
      String relationshipStatusTypeInstance = this.mapRelationship(user.getRelationship());
      Date currentDate = new Date();
      String relationshipStatusInstance = RDFNamingConventionUtil.generateStatusName(
        cadenAlias,
        CadenBaseConstants.RELATIONSHIP_STATUS,
        currentDate
      );

      if (relationshipStatusTypeInstance != null) {
        statements.add(svf.createStatement(
          config.getCadenBaseDataIRI(relationshipStatusInstance),
          RDF.TYPE,
          config.getCadenBaseIRI(CadenBaseConstants.RELATIONSHIP_STATUS)
        ));
        statements.add(svf.createStatement(
          config.getCadenBaseDataIRI(relationshipStatusInstance),
          RDF.TYPE,
          config.getOwlIRI(OwlConstants.NAMED_INDIVIDUAL)
        ));

        statements.add(svf.createStatement(
          config.getCadenBaseDataIRI(relationshipStatusInstance),
          config.getCadenBaseIRI(CadenBaseConstants.STATUS_RECORD_DATE),
          Values.literal(currentDate)
        ));

        statements.add(svf.createStatement(
          config.getCadenBaseDataIRI(relationshipStatusInstance),
          config.getCadenBaseIRI(CadenBaseConstants.RELATIONSHIP_STATUS_TYPE),
          config.getCadenReferenceIRI(relationshipStatusTypeInstance)
        ));

        statements.add(svf.createStatement(
          config.getCadenBaseDataIRI(graphName),
          config.getCadenBaseIRI(CadenBaseConstants.HAS_RELATIONSHIP_STATUS),
          config.getCadenBaseDataIRI(relationshipStatusInstance)
        ));
      }
    }

    if (user.getEmploymentStatus() != null) {
      String employmentStatusEnumerationInstance = this.mapEmployment(user.getEmploymentStatus());
      Date currentDate = new Date();
      String employmentStatusInstance = RDFNamingConventionUtil.generateStatusName(
        cadenAlias,
        CadenBaseConstants.EMPLOYMENT_STATUS,
        currentDate
      );

      if (employmentStatusEnumerationInstance != null) {
        statements.add(svf.createStatement(
          config.getCadenBaseDataIRI(employmentStatusInstance),
          RDF.TYPE,
          config.getCadenBaseIRI(CadenBaseConstants.EMPLOYMENT_STATUS)
        ));
        statements.add(svf.createStatement(
          config.getCadenBaseDataIRI(employmentStatusInstance),
          RDF.TYPE,
          config.getOwlIRI(OwlConstants.NAMED_INDIVIDUAL)
        ));

        statements.add(svf.createStatement(
          config.getCadenBaseDataIRI(employmentStatusInstance),
          config.getCadenBaseIRI(CadenBaseConstants.STATUS_RECORD_DATE),
          Values.literal(currentDate)
        ));

        statements.add(svf.createStatement(
          config.getCadenBaseDataIRI(employmentStatusInstance),
          config.getCadenBaseIRI(CadenBaseConstants.EMPLOYMENT_STATUS_TYPE),
          config.getCadenReferenceIRI(employmentStatusEnumerationInstance)
        ));

        statements.add(svf.createStatement(
          config.getCadenBaseDataIRI(graphName),
          config.getCadenBaseIRI(CadenBaseConstants.HAS_EMPLOYMENT_STATUS),
          config.getCadenBaseDataIRI(employmentStatusInstance)
        ));
      }
    }

    if (user.getEthnicities() != null) {
      user.getEthnicities().forEach(ethnicity -> {
        String ethnicityInstance = this.mapEthnicity(ethnicity);

        if (ethnicityInstance != null) {
          statements.add(svf.createStatement(
            config.getCadenBaseDataIRI(graphName),
            config.getCadenBaseIRI(CadenBaseConstants.HAS_ETHNICITY),
            config.getCadenReferenceIRI(ethnicityInstance)
          ));
        }
      });
    }

    if (user.isHispanicLatinoOrigin() != null && user.isHispanicLatinoOrigin() == true) {
      String ethnicityInstance = CadenReferenceConstants.Ethnicity.HISPANIC_OR_LATINO;

      statements.add(svf.createStatement(
        config.getCadenBaseDataIRI(graphName),
        config.getCadenBaseIRI(CadenBaseConstants.HAS_ETHNICITY),
        config.getCadenReferenceIRI(ethnicityInstance)
      ));
    }

    if (user.getGender() != null) {
      String genderInstance = this.mapGender(user.getGender());

      if (genderInstance != null) {
        statements.add(svf.createStatement(
          config.getCadenBaseDataIRI(graphName),
          config.getCadenBaseIRI(CadenBaseConstants.HAS_GENDER),
          config.getCadenReferenceIRI(genderInstance)
        ));
      }
    }

    return statements;
  }

  @Override
  public User reverseMap(final Collection<Statement> value) {
    throw new UnsupportedOperationException("Not implemented exception");
  }

  private String mapEducationLevel(final EducationLevel educationLevel) {
    switch(educationLevel) {
      case someSchool:
        return CadenReferenceConstants.EducationLevel.SOME_SCHOOL_GRADE;
      case highSchool:
        return CadenReferenceConstants.EducationLevel.HIGH_SCHOOL_DIPLOMA_OR_EQUIVALENT;
      case associatesDegree:
        return CadenReferenceConstants.EducationLevel.ASSOCIATE_DEGREE;
      case undergraduateDegree:
        return CadenReferenceConstants.EducationLevel.BACHELOR_DEGREE;
      case master:
        return CadenReferenceConstants.EducationLevel.MASTER_DEGREE;
      case professionalDegree:
        return CadenReferenceConstants.EducationLevel.PROFESSIONAL_DEGREE;
      case doctorate:
        return CadenReferenceConstants.EducationLevel.DOCTORATE_DEGREE;
      case technical:
        return CadenReferenceConstants.EducationLevel.VOCATIONAL_TRAINING;
      default:
        return null;
    }
  }

  private String mapSexuality(final Sexuality educationLevel) {
    switch(educationLevel) {
      case straight:
        return CadenReferenceConstants.SexualOrientation.STRAIGHT;
      case gay:
        return CadenReferenceConstants.SexualOrientation.GAY;
      case lesbian:
        return CadenReferenceConstants.SexualOrientation.LESBIAN;
      case bisexual:
        return CadenReferenceConstants.SexualOrientation.BISEXUAL;
      case pansexual:
        return CadenReferenceConstants.SexualOrientation.PANSEXUAL;
      case asexual:
        return CadenReferenceConstants.SexualOrientation.ASEXUAL;
      default:
        return null;
    }
  }

  private String mapRelationship(final Relationship relationship) {
    switch(relationship) {
      case single:
        return CadenReferenceConstants.RelationshipStatusType.SINGLE;
      case inRelationship:
        return CadenReferenceConstants.RelationshipStatusType.IN_RELATIONSHIP;
      case married:
        return CadenReferenceConstants.RelationshipStatusType.MARRIED;
      case inDomesticPartnership:
        return CadenReferenceConstants.RelationshipStatusType.DOMESTIC_PARTNERSHIP;
      default:
        return null;
    }
  }

  private String mapEmployment(final EmploymentStatus employmentStatus) {
    switch(employmentStatus) {
      case unemployed:
        return CadenReferenceConstants.EmploymentStatusEnumeration.UNEMPLOYED;
      case selfEmployed:
        return CadenReferenceConstants.EmploymentStatusEnumeration.SELF_EMPLOYED;
      case partTime:
        return CadenReferenceConstants.EmploymentStatusEnumeration.PART_TIME_EMPLOYMENT;
      case fullTime:
        return CadenReferenceConstants.EmploymentStatusEnumeration.FULL_TIME_EMPLOYMENT;
      default:
        return null;
    }
  }

  private String mapEthnicity(final Ethnicity ethnicity) {
    switch (ethnicity) {
      case americanIndian:
        return CadenReferenceConstants.Ethnicity.AMERICAN_INDIAN_OR_ALASKA_NATIVE;
      case pacificIslander:
        return CadenReferenceConstants.Ethnicity.NATIVE_HAWAIIAN_OR_OTHER_PACIFIC_ISLANDER;
      case black:
        return CadenReferenceConstants.Ethnicity.BLACK_OR_AFRICAN_AMERICAN;
      case white:
        return CadenReferenceConstants.Ethnicity.WHITE;
      case other:
        return CadenReferenceConstants.Ethnicity.OTHER;
      case asian:
        return CadenReferenceConstants.Ethnicity.ASIAN;
      default:
        return null;
    }
  }

  private String mapGender(final Gender gender) {
    switch(gender) {
      case male:
        return CadenReferenceConstants.Gender.MALE;
      case female:
        return CadenReferenceConstants.Gender.FEMALE;
      case nonBinary:
        return CadenReferenceConstants.Gender.NONBINARY;
      case preferNotToSay:
        return CadenReferenceConstants.Gender.NO_GENDER_SPECIFIED;
      default:
        return null;
    }
  }
}
