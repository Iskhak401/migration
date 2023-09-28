package io.caden.transformers.megraph.repositories.impl;


import io.caden.transformers.config.RDFConfiguration;
import io.caden.transformers.megraph.entities.User;
import io.caden.transformers.megraph.mappers.UserToStatements;
import io.caden.transformers.megraph.repositories.UserRepository;
import io.caden.transformers.shared.repositories.BaseRepository;
import io.caden.transformers.shared.utils.CadenBaseConstants;
import io.caden.transformers.shared.utils.RDFNamingConventionUtil;
import io.caden.transformers.shared.utils.SchemaBaseConstants;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Arrays;

@Repository("userRepo")
public class UserRepositoryImpl extends BaseRepository<User> implements UserRepository {
  private final RDFConfiguration config;
  private final UserToStatements userToStatements;

  public UserRepositoryImpl(
    @Autowired final RDFConfiguration config,
    @Autowired final UserToStatements userToStatements
  ) {
    super(config);
    this.config = config;
    this.userToStatements = userToStatements;
  }

  @Override
  public User find(final String cognitoUserName) throws Exception {
    // TODO: implement when needed
    return null;
  }

  @Override
  public void update(final User user) throws Exception {
    List<String> predicateWithNamespaces = Arrays.asList(
      config.getSchemaBase(SchemaBaseConstants.Property.BIRTH_DATE),
      config.getCadenBase(CadenBaseConstants.HOME_LOCATION),
      config.getCadenBase(CadenBaseConstants.WORK_LOCATION),
      config.getCadenBase(CadenBaseConstants.NUM_CHILDREN),
      config.getCadenBase(CadenBaseConstants.ANNUAL_SALARY),
      config.getCadenBase(CadenBaseConstants.HOUSEHOLD_INCOME),
      config.getCadenBase(CadenBaseConstants.HAS_EDUCATION_LEVEL),
      config.getCadenBase(CadenBaseConstants.HAS_SEXUALITY),
      config.getCadenBase(CadenBaseConstants.HAS_RELATIONSHIP_STATUS),
      config.getCadenBase(CadenBaseConstants.HAS_EMPLOYMENT_STATUS),
      config.getCadenBase(CadenBaseConstants.HAS_ETHNICITY),
      config.getCadenBase(CadenBaseConstants.HAS_GENDER)
    );

    super.delete(
      config.getCadenBaseDataBase(RDFNamingConventionUtil.generateUserGraphName(user.getCadenAlias().toString())),
      predicateWithNamespaces,
      config.getCadenBaseDataIRI(RDFNamingConventionUtil.generateUserGraphName(user.getCadenAlias().toString()))
    );
    this.save(user);
  }

  public void save(User user) throws Exception {
    super.save(
      this.userToStatements.map(user),
      config.getCadenBaseDataIRI(RDFNamingConventionUtil.generateUserGraphName(user.getCadenAlias().toString()))
    );
  }
}
