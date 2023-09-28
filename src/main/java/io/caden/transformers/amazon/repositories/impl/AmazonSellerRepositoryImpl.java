package io.caden.transformers.amazon.repositories.impl;

import io.caden.transformers.amazon.entities.AmazonSeller;
import io.caden.transformers.amazon.mappers.AmazonSellerToStatements;
import io.caden.transformers.amazon.repositories.AmazonSellerRepository;
import io.caden.transformers.config.RDFConfiguration;
import io.caden.transformers.shared.repositories.BaseRepository;
import io.caden.transformers.shared.utils.SchemaBaseConstants;

import org.eclipse.rdf4j.model.Statement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@Repository("amazonSellerRepo")
public class AmazonSellerRepositoryImpl extends BaseRepository<AmazonSeller> implements AmazonSellerRepository {
  private final AmazonSellerToStatements amazonSellerToStatements;
  private final RDFConfiguration config;

  public AmazonSellerRepositoryImpl(
    @Autowired final AmazonSellerToStatements amazonSellerToStatements,
    @Autowired final RDFConfiguration config
  ) {
    super(config);
    this.amazonSellerToStatements = amazonSellerToStatements;
    this.config = config;
  }

  @Override
  public AmazonSeller find(String filter) throws Exception {
    throw new UnsupportedOperationException("Not implemented exception");
  }

  @Override
  public Collection<Statement> toStatements(AmazonSeller amazonSeller) {
    return this.amazonSellerToStatements.map(amazonSeller);
  }

  @Override
  public void delete(final AmazonSeller amazonSeller) throws Exception {
    List<String> predicateWithNamespaces = Arrays.asList(
      config.getSchemaBase(SchemaBaseConstants.Property.NAME),
      config.getSchemaBase(SchemaBaseConstants.Property.SAME_AS)
    );

    String instance = AmazonSellerToStatements.getInstance(amazonSeller);

    super.delete(config.getCadenReferenceDataBase(instance), predicateWithNamespaces, config.getCadenReferenceDataIRI(""));
  }
}
