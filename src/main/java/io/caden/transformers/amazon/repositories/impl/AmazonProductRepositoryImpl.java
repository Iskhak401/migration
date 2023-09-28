package io.caden.transformers.amazon.repositories.impl;

import io.caden.transformers.amazon.entities.AmazonProduct;
import io.caden.transformers.amazon.entities.AmazonProductCategory;
import io.caden.transformers.amazon.mappers.AmazonProductToStatements;
import io.caden.transformers.amazon.mappers.BindingSetToAmazonProduct;
import io.caden.transformers.amazon.repositories.AmazonProductRepository;
import io.caden.transformers.config.RDFConfiguration;
import io.caden.transformers.shared.repositories.BaseRepository;
import io.caden.transformers.shared.utils.SchemaBaseConstants;
import org.eclipse.rdf4j.model.Statement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository("amazonProductRepo")
public class AmazonProductRepositoryImpl extends BaseRepository<AmazonProduct> implements AmazonProductRepository {

  private final RDFConfiguration config;
  private final AmazonProductToStatements amazonProductToStatements;
  private final BindingSetToAmazonProduct bindingSetToAmazonProduct;

  public AmazonProductRepositoryImpl(
    @Autowired final RDFConfiguration config,
    @Autowired final AmazonProductToStatements amazonProductToStatements,
    @Autowired final BindingSetToAmazonProduct bindingSetToAmazonProduct
  ) {
    super(config);
    this.config = config;
    this.amazonProductToStatements = amazonProductToStatements;
    this.bindingSetToAmazonProduct = bindingSetToAmazonProduct;
  }

  @Override
  public AmazonProduct find(final String label) throws Exception {
    throw new UnsupportedOperationException("Not implemented exception");
  }

  @Override
  public Collection<Statement> toStatements(AmazonProduct amazonProduct) {
    return this.amazonProductToStatements.map(amazonProduct);
  }

  @Override
  public void delete(final AmazonProduct amazonProduct) throws Exception {
    List<String> predicateWithNamespaces = Arrays.asList(
      config.getSchemaBase(SchemaBaseConstants.Property.NAME),
      config.getSchemaBase(SchemaBaseConstants.Property.RATINGS_TOTAL),
      config.getSchemaBase(SchemaBaseConstants.Property.BRAND),
      config.getSchemaBase(SchemaBaseConstants.Property.COLOR),
      config.getSchemaBase(SchemaBaseConstants.Property.GTIN),
      config.getSchemaBase(SchemaBaseConstants.Property.MANUFACTURER),
      config.getSchemaBase(SchemaBaseConstants.Property.MODEL),
      config.getSchemaBase(SchemaBaseConstants.Property.WEIGHT),
      config.getSchemaBase(SchemaBaseConstants.Property.FIRST_AVAILABLE),
      config.getSchemaBase(SchemaBaseConstants.Property.SIZE),
      config.getSchemaBase(SchemaBaseConstants.Property.IS_RELATED_TO),
      config.getSchemaBase(SchemaBaseConstants.Property.KEYWORDS),
      config.getSchemaBase(SchemaBaseConstants.Property.HAS_ENERGY_CONSUMPTION_DETAILS),
      config.getSchemaBase(SchemaBaseConstants.Property.IMAGE),
      config.getSchemaBase(SchemaBaseConstants.Property.DESCRIPTION),
      config.getSchemaBase(SchemaBaseConstants.Property.SAME_AS),
      config.getSchemaBase(SchemaBaseConstants.Property.IDENTIFIER),
      config.getSchemaBase(SchemaBaseConstants.Relationship.CATEGORY)
    );

    String instance = AmazonProductToStatements.getInstance(amazonProduct);

    if (amazonProduct.getAmazonProductCategories() != null) {
      for (AmazonProductCategory amazonProductCategory : amazonProduct.getAmazonProductCategories()) {
        String categoryInstance = AmazonProductToStatements.getCategoryInstance(amazonProductCategory);
        super.delete(
          config.getCadenReferenceDataBase(categoryInstance),
          Arrays.asList(
            config.getSchemaBase(SchemaBaseConstants.Property.NAME),
            config.getSchemaBase(SchemaBaseConstants.Property.SAME_AS),
            config.getSchemaBase(SchemaBaseConstants.Property.CODE_VALUE)
          ),
          config.getCadenReferenceDataIRI("")
        );
      }
    }

    super.delete(config.getCadenReferenceDataBase(instance), predicateWithNamespaces, config.getCadenReferenceDataIRI(""));
  }

  @Override
  public AmazonProduct findByIdentifier(String identifier) throws Exception {
      String queryString = ""
        + "PREFIX cdnrd: <" + config.getCadenReferenceDataNamespace() + "> "
        + "PREFIX sch: <" + config.getSchemaNamespace() + "> "
        + "SELECT * WHERE {"
        + " GRAPH cdnrd: {"
          + " ?s a sch:Product ;"
          + " sch:identifier ?search ;"
          + " sch:name ?name ;"
          + " sch:identifier ?identifier ;"
          + " sch:brand ?brand ;"
        + " }"
      + " } "
      + " LIMIT 1";

      Map<String, Object> params = new HashMap<>();
      params.put("search", identifier);

      return this.bindingSetToAmazonProduct.map(this.executeQuery(queryString, params));
  }
}
