package io.caden.transformers.amazon.mappers;

import io.caden.transformers.amazon.entities.AmazonOrder;
import io.caden.transformers.amazon.entities.AmazonOrderItem;
import io.caden.transformers.amazon.utils.AmazonConstants;
import io.caden.transformers.config.RDFConfiguration;
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

import java.util.*;

@Component
public class AmazonOrderItemToStatements{

  private final RDFConfiguration config;
  private final SimpleValueFactory svf = SimpleValueFactory.getInstance();

  public AmazonOrderItemToStatements(@Autowired final RDFConfiguration config) {
    this.config = config;
  }

  public Collection<Statement> map(final AmazonOrderItem amazonOrderItem, AmazonOrder amazonOrder) {
    String amazonOrderInstance = AmazonOrderToStatements.getInstance(amazonOrder);
    String amazonOrderItemInstance = getInstance(amazonOrder, amazonOrderItem);

    List<Statement> statements =  new ArrayList<>(Arrays.asList(
      svf.createStatement(config.getCadenBaseDataIRI(amazonOrderItemInstance), RDF.TYPE, config.getSchemaIRI(SchemaBaseConstants.Class.ORDER_ITEM)),
      svf.createStatement(config.getCadenBaseDataIRI(amazonOrderItemInstance), RDF.TYPE, config.getOwlIRI(OwlConstants.NAMED_INDIVIDUAL)),
      svf.createStatement(config.getCadenBaseDataIRI(amazonOrderItemInstance), RDFS.LABEL, Values.literal(amazonOrderItemInstance)),
      svf.createStatement(config.getCadenBaseDataIRI(amazonOrderInstance), config.getSchemaIRI(SchemaBaseConstants.Relationship.ORDERED_ITEM), config.getCadenBaseDataIRI(amazonOrderItemInstance))
    ));

    if (amazonOrderItem.getParcelDelivery() != null) {
      String amazonParcelDeliveryInstance = AmazonParcelDeliveryToStatements.getInstance(amazonOrder);
      statements.add(
        svf.createStatement(config.getCadenBaseDataIRI(amazonOrderItemInstance), config.getSchemaIRI(SchemaBaseConstants.Relationship.ORDER_DELIVERY), config.getCadenBaseDataIRI(amazonParcelDeliveryInstance))
      );
    }

    if (amazonOrderItem.getSeller() != null) {
      String amazonSellerInstance = AmazonSellerToStatements.getInstance(amazonOrderItem.getSeller());
      statements.add(
        svf.createStatement(config.getCadenBaseDataIRI(amazonOrderItemInstance), config.getCadenBaseIRI(SchemaBaseConstants.Relationship.SELLER), config.getCadenReferenceDataIRI(amazonSellerInstance))
      );
    }

    if (amazonOrderItem.getAmazonProduct() != null) {
      String amazonProductInstance = AmazonProductToStatements.getInstance(amazonOrderItem.getAmazonProduct());
      statements.add(
        svf.createStatement(config.getCadenBaseDataIRI(amazonOrderItemInstance), config.getSchemaIRI(SchemaBaseConstants.Relationship.ORDERED_ITEM), config.getCadenReferenceDataIRI(amazonProductInstance))
      );
    }

    if (amazonOrderItem.getQuantity() != null) {
      statements.add(
        svf.createStatement(config.getCadenBaseDataIRI(amazonOrderItemInstance), config.getSchemaIRI(SchemaBaseConstants.Property.ORDER_QUANTITY), Values.literal(amazonOrderItem.getQuantity()))
      );
    }

    if (amazonOrderItem.getPrice() != null) {
      statements.add(
        svf.createStatement(config.getCadenBaseDataIRI(amazonOrderItemInstance), config.getCadenBaseIRI(AmazonConstants.PRICE), Values.literal(amazonOrderItem.getPrice()))
      );
    }

    if (amazonOrderItem.getPriceCurrency() != null) {
      statements.add(
        svf.createStatement(config.getCadenBaseDataIRI(amazonOrderItemInstance), config.getCadenBaseIRI(AmazonConstants.PRICE_CURRENCY), Values.literal(amazonOrderItem.getPriceCurrency()))
      );
    }

    return statements;
  }

  public AmazonOrderItem reverseMap(final Collection<Statement> value) {
    throw new UnsupportedOperationException("Not implemented exception");
  }

  public static String getInstance(final AmazonOrder amazonOrder) {
    return RDFNamingConventionUtil.generateAuxiliaryName(
      SchemaBaseConstants.Class.ORDER_ITEM,
      AmazonConstants.ORDER,
      amazonOrder.getOrderDate()
    );
  }

  public static String getInstance(final AmazonOrder amazonOrder, AmazonOrderItem orderItem) {
    return RDFNamingConventionUtil.generateAuxiliaryName(
            SchemaBaseConstants.Class.ORDER_ITEM,
            AmazonConstants.ORDER,
            amazonOrder.getOrderDate(),
            orderItem.getCreatedAt(),
            UUID.fromString(orderItem.getUuid())
    );
  }
}
