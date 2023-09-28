package io.caden.transformers.amazon.repositories;

import io.caden.transformers.amazon.entities.AmazonProduct;
import io.caden.transformers.shared.repositories.Repository;
import org.eclipse.rdf4j.model.Statement;

import java.util.Collection;

public interface AmazonProductRepository extends Repository<AmazonProduct> {

  Collection<Statement> toStatements(AmazonProduct amazonProduct);
  void delete(AmazonProduct amazonProduct) throws Exception;
  AmazonProduct findByIdentifier(String identifier) throws Exception;
}
