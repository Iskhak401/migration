package io.caden.transformers.location.repositories.impl;

import io.caden.transformers.config.RDFConfiguration;
import io.caden.transformers.location.entities.MoveAction;
import io.caden.transformers.location.mappers.MoveActionToStatements;
import io.caden.transformers.location.repositories.MoveActionRepository;
import io.caden.transformers.shared.repositories.BaseRepository;
import io.caden.transformers.shared.utils.RDFNamingConventionUtil;
import io.caden.transformers.shared.utils.RDFUtil;
import org.eclipse.rdf4j.query.BindingSet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Repository("moveActionRepo")
public class MoveActionRepositoryImpl extends BaseRepository<MoveAction> implements MoveActionRepository {
  private static final Logger LOGGER = LoggerFactory.getLogger(MoveActionRepositoryImpl.class);

  private final RDFConfiguration config;
  private final MoveActionToStatements moveActionToStatements;

  public MoveActionRepositoryImpl(
    @Autowired final RDFConfiguration config,
    @Autowired final MoveActionToStatements moveActionToStatements
  ) {
    super(config);
    this.config = config;
    this.moveActionToStatements = moveActionToStatements;
  }

  @Override
  public MoveAction find(final String filter) throws Exception {
    throw new UnsupportedOperationException("Not implemented exception");
  }

  @Override
  public Map<Date, String> findPreviousLocationByCadenAlias(final String cadenAlias) throws Exception {
    String queryString =
      "PREFIX sch: <" + config.getSchemaNamespace() + "> " +
      "SELECT * { "
        + "GRAPH ?g { "
          + "?s a sch:MoveAction . "
          + "?s sch:endTime ?endTime . "
          + "?s sch:toLocation ?toLocation . "
        + "} "
      + "} "
      + "ORDER BY DESC(?endTime) "
      + "LIMIT 1";

    Map<String, Object> params = new HashMap<>();
    params.put("g", config.getCadenBaseDataIRI(RDFNamingConventionUtil.generateUserGraphName(cadenAlias)));

    try {
      BindingSet bindingSet = this.executeQuery(queryString, params);

      if (bindingSet != null && !bindingSet.isEmpty()) {
        Date lastLocationDate = RDFUtil.getDate(RDFUtil.getValue(bindingSet, "endTime"));
        String lastCoordinatesInstance = RDFUtil.getValue(bindingSet, "toLocation");

        return new HashMap<Date, String>() {{
          put(lastLocationDate, lastCoordinatesInstance.substring(lastCoordinatesInstance.lastIndexOf("/") + 1));
        }};
      }
    } catch (Exception e) {
      LOGGER.error("Error findPreviousLocationByCadenAlias.", e);
    }

    return null;
  }
}
