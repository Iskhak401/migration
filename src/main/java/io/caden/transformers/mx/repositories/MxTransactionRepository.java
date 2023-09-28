package io.caden.transformers.mx.repositories;

import io.caden.transformers.mx.entities.MxTransaction;
import io.caden.transformers.shared.repositories.Repository;
import io.caden.transformers.shared.utils.ExecuteStatements;

public interface MxTransactionRepository extends Repository<MxTransaction> {

  ExecuteStatements buildStatements(MxTransaction mxTransaction);

  Boolean exists(String cadenAlias, String identifier);
}