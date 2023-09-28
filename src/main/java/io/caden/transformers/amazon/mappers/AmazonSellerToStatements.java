package io.caden.transformers.amazon.mappers;

import io.caden.transformers.amazon.entities.AmazonSeller;
import io.caden.transformers.amazon.utils.AmazonConstants;
import io.caden.transformers.config.RDFConfiguration;
import io.caden.transformers.shared.mappers.Mapper;
import io.caden.transformers.shared.utils.OwlConstants;
import io.caden.transformers.shared.utils.RDFNamingConventionUtil;
import io.caden.transformers.shared.utils.SchemaBaseConstants;
import org.eclipse.rdf4j.model.Statement;
import org.eclipse.rdf4j.model.impl.SimpleValueFactory;
import org.eclipse.rdf4j.model.util.Values;
import org.eclipse.rdf4j.model.vocabulary.RDF;
import org.eclipse.rdf4j.model.vocabulary.RDFS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@Component
public class AmazonSellerToStatements extends Mapper<AmazonSeller, Collection<Statement>> {
  private final RDFConfiguration config;
  private final SimpleValueFactory svf = SimpleValueFactory.getInstance();

  public AmazonSellerToStatements(@Autowired final RDFConfiguration config) {
    this.config = config;
  }

  @Override
  public Collection<Statement> map(final AmazonSeller amazonSeller) {
    String amazonSellerInstance = getInstance(amazonSeller);

    List<Statement> statements =  new ArrayList<>(Arrays.asList(
      svf.createStatement(config.getCadenReferenceDataIRI(amazonSellerInstance), RDF.TYPE, config.getCadenBaseIRI(AmazonConstants.AMAZON_SELLER)),
      svf.createStatement(config.getCadenReferenceDataIRI(amazonSellerInstance), RDF.TYPE, config.getOwlIRI(OwlConstants.NAMED_INDIVIDUAL)),
      svf.createStatement(config.getCadenReferenceDataIRI(amazonSellerInstance), RDFS.LABEL, Values.literal(amazonSellerInstance)),
      svf.createStatement(config.getCadenReferenceDataIRI(amazonSellerInstance), config.getSchemaIRI(SchemaBaseConstants.Property.IDENTIFIER), Values.literal(amazonSeller.getIdentifier()))
    ));

    if (amazonSeller.getName() != null) {
      statements.add(
        svf.createStatement(config.getCadenReferenceDataIRI(amazonSellerInstance), config.getSchemaIRI(SchemaBaseConstants.Property.NAME), Values.literal(amazonSeller.getName()))
      );
    }

    if (amazonSeller.getSameAs() != null) {
      statements.add(
        svf.createStatement(config.getCadenReferenceDataIRI(amazonSellerInstance), config.getSchemaIRI(SchemaBaseConstants.Property.SAME_AS), Values.iri(amazonSeller.getSameAs()))
      );
    }

    return statements;
  }

  @Override
  public AmazonSeller reverseMap(final Collection<Statement> value) {
    throw new UnsupportedOperationException("Not implemented exception");
  }

  public static String getInstance(final AmazonSeller amazonSeller) {
    return RDFNamingConventionUtil.generateSlashReferenceName(AmazonConstants.AMAZON_SELLER, amazonSeller.getIdentifier());
  }
}
