package io.caden.transformers.amazon.repositories;

import io.caden.transformers.amazon.entities.AmazonSeller;
import io.caden.transformers.shared.repositories.Repository;
import org.eclipse.rdf4j.model.Statement;

import java.util.Collection;

public interface AmazonSellerRepository extends Repository<AmazonSeller> {

  Collection<Statement> toStatements(AmazonSeller amazonSeller);
  void delete(AmazonSeller amazonSeller) throws Exception;
}
