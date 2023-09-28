package io.caden.transformers.mx.repositories.impl;

import io.caden.transformers.config.RDFConfiguration;
import io.caden.transformers.mx.entities.MxTransaction;
import io.caden.transformers.mx.mappers.MxTransactionToStatements;
import io.caden.transformers.mx.repositories.MxTransactionRepository;
import io.caden.transformers.shared.repositories.BaseRepository;
import io.caden.transformers.shared.utils.ExecuteStatements;
import io.caden.transformers.shared.utils.RDFNamingConventionUtil;
import org.eclipse.rdf4j.model.Statement;
import org.eclipse.rdf4j.model.vocabulary.RDFS;
import org.eclipse.rdf4j.query.BindingSet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository("mxTransactionRepo")
public class MxTransactionRepositoryImpl extends BaseRepository<MxTransaction> implements MxTransactionRepository {

  private static final Logger LOGGER = LoggerFactory.getLogger(MxTransactionRepositoryImpl.class);

  private final RDFConfiguration config;
  private final MxTransactionToStatements mxTransactionToStatements;

  public MxTransactionRepositoryImpl(
    @Autowired final RDFConfiguration config,
    @Autowired final MxTransactionToStatements mxTransactionToStatements
  ) {
    super(config);
    this.config = config;
    this.mxTransactionToStatements = mxTransactionToStatements;
  }

  @Override
  public MxTransaction find(final String uuid) throws Exception {
    throw new UnsupportedOperationException("Not implemented exception");
  }

  private Collection<Statement> toTransactionStatements(MxTransaction mxTransaction) {
    return this.mxTransactionToStatements.mapToCadenTransaction(mxTransaction);
  }

  private Collection<Statement> toMerchantStatements(MxTransaction mxTransaction) {
    return this.mxTransactionToStatements.mapToMxMerchant(mxTransaction);
  }

  private Collection<Statement> toMerchantLocationStatements(MxTransaction mxTransaction) {
    return this.mxTransactionToStatements.mapToStore(mxTransaction);
  }

  public ExecuteStatements buildStatements(MxTransaction mxTransaction) {
    ExecuteStatements executeStatements = new ExecuteStatements();

    executeStatements.addAllToUserGraph(toTransactionStatements(mxTransaction));

    if (mxTransaction.getMxMerchantLocation() != null) {
      executeStatements.addAllToReferenceGraph(toMerchantLocationStatements(mxTransaction));
    } else if (mxTransaction.getMxMerchant() != null) {
      executeStatements.addAllToReferenceGraph(toMerchantStatements(mxTransaction));
    }

    return  executeStatements;
  }

  @Override
  public Boolean exists(String cadenAlias, String identifier) {
    String queryString = ""
    + "PREFIX rdfs: <" + RDFS.NAMESPACE + "> "
    + "PREFIX cdn: <" + config.getCadenBaseNamespace() + "> "
    + "PREFIX sch: <" + config.getSchemaNamespace() + "> "
    + " SELECT DISTINCT ?id {"
      + " GRAPH ?g {"
        + " VALUES ?transaction { cdn:BuyAction cdn:PayAction cdn:DonateAction } ."
        + " ?s a ?transaction ;"
          + " sch:identifier ?id ."
      + " }"
      + " FILTER(?id = ?searchId) ."
    + " }"
    + " LIMIT 1"
    ;

    Map<String, Object> params = new HashMap<>();
    params.put("g", config.getCadenBaseDataIRI(RDFNamingConventionUtil.generateUserGraphName(cadenAlias)));
    params.put("searchId", identifier);

    try {
      List<BindingSet> bindingSets = this.executeQueryList(queryString, params);

      return !bindingSets.isEmpty();
    } catch (Exception e) {
      LOGGER.error("Error exists.", e);
      return false;
    }
  }
}
