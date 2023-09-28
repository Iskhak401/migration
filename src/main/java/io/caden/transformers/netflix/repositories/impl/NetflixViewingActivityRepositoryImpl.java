package io.caden.transformers.netflix.repositories.impl;


import io.caden.transformers.config.RDFConfiguration;
import io.caden.transformers.netflix.entities.NetflixViewingActivity;
import io.caden.transformers.netflix.mappers.NetflixViewingActivityToStatements;
import io.caden.transformers.netflix.repositories.NetflixViewingActivityRepository;
import io.caden.transformers.shared.repositories.BaseRepository;
import io.caden.transformers.shared.utils.RDFNamingConventionUtil;
import org.eclipse.rdf4j.model.vocabulary.XSD;
import org.eclipse.rdf4j.query.BindingSet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository("netflixViewingActivityRepo")
public class NetflixViewingActivityRepositoryImpl extends BaseRepository<NetflixViewingActivity> implements NetflixViewingActivityRepository {
    private static final Logger LOGGER = LoggerFactory.getLogger(NetflixViewingActivityRepositoryImpl.class);

    private final RDFConfiguration config;

    public NetflixViewingActivityRepositoryImpl(
        @Autowired final NetflixViewingActivityToStatements netflixViewingActivityToStatements,
        @Autowired final RDFConfiguration config
    ) {
        super(config);
        this.config = config;
    }

    @Override
    public NetflixViewingActivity find(String filter) throws Exception {
        throw new UnsupportedOperationException("Not implemented exception");
    }

    @Override
    public Boolean existsByCadenAliasAndIdentifierAndStartTime(String cadenAlias, Long identifier, Date startTime) {
      String queryString = ""
      + "PREFIX xsd: <" + XSD.NAMESPACE + "> "
      + "PREFIX cdn: <" + config.getCadenBaseNamespace() + "> "
      + "PREFIX cdnrd: <" + config.getCadenReferenceDataNamespace() + "> "
      + "PREFIX sch: <" + config.getSchemaNamespace() + "> "
      + "PREFIX spif: <http://spinrdf.org/spif#> "
      + " SELECT * {"
        + " GRAPH ?g {"
          + " ?s a cdn:WatchAction ;"
            + " sch:object ?program ;"
            + " sch:startTime ?time ."
        + " }"
        + " GRAPH cdnrd: {"
          + " ?program sch:identifier ?identifier ."
        + " }"
        + " FILTER("
          + " ?identifier = ?searchId"
          + " && spif:timeMillis(?time) = ?searchTime"
        + " ) ."
      + " }"
      + " LIMIT 1";

      Map<String, Object> params = new HashMap<>();
      params.put("g", config.getCadenBaseDataIRI(RDFNamingConventionUtil.generateUserGraphName(cadenAlias)));
      params.put("searchId", Long.toString(identifier));
      params.put("searchTime", startTime.getTime());

      try {
        List<BindingSet> bindingSets = this.executeQueryList(queryString, params);

        return !bindingSets.isEmpty();
      } catch (Exception e) {
        LOGGER.error("Error existsByCadenAliasAndIdentifierAndStartTime.", e);
        return false;
      }
    }
}
