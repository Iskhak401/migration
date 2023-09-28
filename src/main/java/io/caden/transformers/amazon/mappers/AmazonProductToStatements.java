package io.caden.transformers.amazon.mappers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import io.caden.transformers.amazon.entities.AmazonProduct;
import io.caden.transformers.amazon.entities.AmazonProductCategory;
import io.caden.transformers.config.RDFConfiguration;
import io.caden.transformers.shared.mappers.Mapper;
import io.caden.transformers.shared.utils.CadenBaseConstants;
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

@Component
public class AmazonProductToStatements extends Mapper<AmazonProduct, Collection<Statement>> {

  private final RDFConfiguration config;
  private final SimpleValueFactory svf = SimpleValueFactory.getInstance();

  public AmazonProductToStatements(@Autowired final RDFConfiguration config) {
    this.config = config;
  }

  @Override
  public Collection<Statement> map(final AmazonProduct amazonProduct) {
    String amazonProductInstance = getInstance(amazonProduct);

    List<Statement> statements =  new ArrayList<>(Arrays.asList(
      svf.createStatement(config.getCadenReferenceDataIRI(amazonProductInstance), RDF.TYPE, config.getSchemaIRI(SchemaBaseConstants.Class.PRODUCT)),
      svf.createStatement(config.getCadenReferenceDataIRI(amazonProductInstance), RDF.TYPE, config.getOwlIRI(OwlConstants.NAMED_INDIVIDUAL)),
      svf.createStatement(config.getCadenReferenceDataIRI(amazonProductInstance), RDFS.LABEL, Values.literal(amazonProductInstance)),
      svf.createStatement(config.getCadenReferenceDataIRI(amazonProductInstance), config.getSchemaIRI(SchemaBaseConstants.Property.IDENTIFIER), Values.literal(amazonProduct.getAsin()))
    ));

    if (amazonProduct.getRating() != null) {
      statements.add(
              svf.createStatement(
                      config.getCadenReferenceDataIRI(amazonProductInstance),
                      config.getSchemaIRI(SchemaBaseConstants.Property.RATING),
                      Values.literal(amazonProduct.getRating())
              )
      );
    }
    if (amazonProduct.getRatingsTotal() != null) {
      statements.add(
        svf.createStatement(
                config.getCadenReferenceDataIRI(amazonProductInstance),
                config.getSchemaIRI(SchemaBaseConstants.Property.RATINGS_TOTAL),
                Values.literal(amazonProduct.getRatingsTotal())
        )
      );
    }

    if (amazonProduct.getBrand() != null) {
      statements.add(
        svf.createStatement(
                config.getCadenReferenceDataIRI(amazonProductInstance),
                config.getSchemaIRI(SchemaBaseConstants.Property.BRAND),
                Values.literal(amazonProduct.getBrand())
        )
      );
    }

    this.mapCategories(amazonProduct, statements, amazonProductInstance);

    if (amazonProduct.getColor() != null) {
      statements.add(
        svf.createStatement(
                config.getCadenReferenceDataIRI(amazonProductInstance),
                config.getSchemaIRI(SchemaBaseConstants.Property.COLOR),
                Values.literal(amazonProduct.getColor())
        )
      );
    }

    if (amazonProduct.getGtin() != null) {
      statements.add(
        svf.createStatement(
                config.getCadenReferenceDataIRI(amazonProductInstance),
                config.getSchemaIRI(SchemaBaseConstants.Property.GTIN),
                Values.literal(amazonProduct.getGtin())
        )
      );
    }

    if (amazonProduct.getManufacturer() != null) {
      statements.add(
        svf.createStatement(
                config.getCadenReferenceDataIRI(amazonProductInstance),
                config.getSchemaIRI(SchemaBaseConstants.Property.MANUFACTURER),
                Values.literal(amazonProduct.getManufacturer())
        )
      );
    }

    if (amazonProduct.getModelNumber() != null) {
      statements.add(
        svf.createStatement(
                config.getCadenReferenceDataIRI(amazonProductInstance),
                config.getSchemaIRI(SchemaBaseConstants.Property.MODEL),
                Values.literal(amazonProduct.getModelNumber())
        )
      );
    }

    if (amazonProduct.getWeight() != null) {
      statements.add(
        svf.createStatement(
                config.getCadenReferenceDataIRI(amazonProductInstance),
                config.getSchemaIRI(SchemaBaseConstants.Property.WEIGHT),
                Values.literal(amazonProduct.getWeight())
        )
      );
    }

    if (amazonProduct.getFirstAvailable() != null) {
      statements.add(
        svf.createStatement(
                config.getCadenReferenceDataIRI(amazonProductInstance),
                config.getSchemaIRI(SchemaBaseConstants.Property.FIRST_AVAILABLE),
                Values.literal(amazonProduct.getFirstAvailable())
        )
      );
    }

    if (amazonProduct.getDimensions() != null) {
      statements.add(
        svf.createStatement(
                config.getCadenReferenceDataIRI(amazonProductInstance),
                config.getSchemaIRI(SchemaBaseConstants.Property.SIZE),
                Values.literal(amazonProduct.getDimensions())
        )
      );
    }

    if (amazonProduct.getAmazonProductKeywords() != null) {
      amazonProduct.getAmazonProductKeywords().stream().forEach(x -> statements.add(
          svf.createStatement(
                  config.getCadenReferenceDataIRI(amazonProductInstance),
                  config.getSchemaIRI(SchemaBaseConstants.Property.KEYWORDS),
                  Values.literal(x.getKeyword())
          )
        )
      );
    }

    if (amazonProduct.getAmazonProductEnergyEfficiency() != null && amazonProduct.getAmazonProductEnergyEfficiency().getRating() != null) {
      statements.add(
        svf.createStatement(
                config.getCadenReferenceDataIRI(amazonProductInstance),
                config.getSchemaIRI(SchemaBaseConstants.Property.HAS_ENERGY_CONSUMPTION_DETAILS),
                Values.literal(amazonProduct.getAmazonProductEnergyEfficiency().getRating())
        )
      );
    }

    if (amazonProduct.getAmazonProductImages() != null) {
      amazonProduct.getAmazonProductImages().stream().forEach(x ->
        statements.add(
          svf.createStatement(
                  config.getCadenReferenceDataIRI(amazonProductInstance),
                  config.getSchemaIRI(SchemaBaseConstants.Property.IMAGE),
                  Values.literal(x.getLink())
          )
        )
      );
    }

    if (amazonProduct.getTitle() != null) {
      statements.add(
        svf.createStatement(
                config.getCadenReferenceDataIRI(amazonProductInstance),
                config.getSchemaIRI(SchemaBaseConstants.Property.NAME),
                Values.literal(amazonProduct.getTitle())
        )
      );
    }

    if (amazonProduct.getDescription() != null) {
      statements.add(
        svf.createStatement(
                config.getCadenReferenceDataIRI(amazonProductInstance),
                config.getSchemaIRI(SchemaBaseConstants.Property.DESCRIPTION),
                Values.literal(amazonProduct.getDescription())
        )
      );
    }

    if (amazonProduct.getLink() != null) {
      statements.add(
        svf.createStatement(
                config.getCadenReferenceDataIRI(amazonProductInstance),
                config.getSchemaIRI(SchemaBaseConstants.Property.SAME_AS),
                Values.iri(amazonProduct.getLink())
        )
      );
    }

    return statements;
  }

  // Checks if AmazonProduct has categories and maps the categories into statements.
  private void mapCategories(AmazonProduct amazonProduct, List<Statement> statements, String amazonProductInstance) {
    if (amazonProduct.getAmazonProductCategories() != null) {
      amazonProduct.getAmazonProductCategories().stream().filter(x -> x.getName() != null && x.getExternalId() != null).forEach(x -> {
        String amazonProductCategoryInstance = getCategoryInstance(x);

        statements.addAll(Arrays.asList(
                svf.createStatement(
                        config.getCadenReferenceDataIRI(amazonProductCategoryInstance),
                        RDF.TYPE,
                        config.getSchemaIRI(SchemaBaseConstants.Class.CATEGORY_CODE)
                ),
                svf.createStatement(
                        config.getCadenReferenceDataIRI(amazonProductCategoryInstance),
                        RDF.TYPE,
                        config.getOwlIRI(OwlConstants.NAMED_INDIVIDUAL)
                ),
                svf.createStatement(
                        config.getCadenReferenceDataIRI(amazonProductCategoryInstance),
                        RDFS.LABEL,
                        Values.literal(amazonProductCategoryInstance)
                ),
                svf.createStatement(
                        config.getCadenReferenceDataIRI(amazonProductCategoryInstance),
                        config.getSchemaIRI(SchemaBaseConstants.Property.NAME),
                        Values.literal(x.getName())
                ),
                svf.createStatement(
                        config.getCadenReferenceDataIRI(amazonProductCategoryInstance),
                        config.getSchemaIRI(SchemaBaseConstants.Property.CODE_VALUE),
                        Values.literal(x.getExternalId())
                ),
                svf.createStatement(
                        config.getCadenReferenceDataIRI(amazonProductInstance),
                        config.getSchemaIRI(SchemaBaseConstants.Relationship.CATEGORY),
                        config.getCadenReferenceDataIRI(amazonProductCategoryInstance)
                )
        ));

        if (x.getLink() != null) {
          statements.add(
                  svf.createStatement(
                          config.getCadenReferenceDataIRI(amazonProductCategoryInstance),
                          config.getSchemaIRI(SchemaBaseConstants.Property.SAME_AS),
                          Values.iri(x.getLink())
                  )
          );
        }
      });
    }
  }

  public static String getInstance(final AmazonProduct amazonProduct) {
    return RDFNamingConventionUtil.generateReferenceName(SchemaBaseConstants.Class.PRODUCT, amazonProduct.getAsin());
  }

  public static String getCategoryInstance(final AmazonProductCategory amazonProductCategory) {
    return RDFNamingConventionUtil.generateReferenceName(CadenBaseConstants.Intake.AMAZON, amazonProductCategory.getExternalId());
  }

  @Override
  public AmazonProduct reverseMap(final Collection<Statement> value) {
    throw new UnsupportedOperationException("Not implemented exception");
  }
}
