package io.caden.transformers.amazon.repositories;

import io.caden.transformers.amazon.entities.AmazonOrder;
import io.caden.transformers.amazon.models.ExistingAmazonOrder;
import io.caden.transformers.shared.repositories.Repository;
import org.eclipse.rdf4j.model.Statement;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public interface AmazonOrderRepository extends Repository<AmazonOrder> {

  Collection<Statement> toStatements(AmazonOrder amazonOrder);
  void delete(AmazonOrder amazonOrder) throws Exception;

  Map<String, ExistingAmazonOrder> findExistingOrdersByCadenAliasAndOrderNumbers(String cadenAlias, List<String> orderNumbers);
}
