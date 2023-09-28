package io.caden.transformers.amazon.mappers;

import io.caden.transformers.amazon.entities.AmazonOrder;
import io.caden.transformers.amazon.entities.AmazonOrderStatus;
import io.caden.transformers.amazon.utils.AmazonConstants;
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

import java.util.*;

@Component
public class AmazonOrderToStatements extends Mapper<AmazonOrder, Collection<Statement>> {
  private final RDFConfiguration config;
  private final SimpleValueFactory svf = SimpleValueFactory.getInstance();

  public AmazonOrderToStatements(@Autowired final RDFConfiguration config) {
    this.config = config;
  }

  @Override
  public Collection<Statement> map(final AmazonOrder amazonOrder) {
    String userInstance = RDFNamingConventionUtil.generateUserGraphName(amazonOrder.getCadenAlias());
    String amazonOrderInstance = getInstance(amazonOrder);

    List<Statement> statements =  new ArrayList<>(Arrays.asList(
      svf.createStatement(config.getCadenBaseDataIRI(amazonOrderInstance), RDF.TYPE, config.getCadenBaseIRI(AmazonConstants.ORDER)),
      svf.createStatement(config.getCadenBaseDataIRI(amazonOrderInstance), RDF.TYPE, config.getOwlIRI(OwlConstants.NAMED_INDIVIDUAL)),
      svf.createStatement(config.getCadenBaseDataIRI(amazonOrderInstance), RDFS.LABEL, Values.literal(amazonOrderInstance)),
      svf.createStatement(config.getCadenBaseDataIRI(amazonOrderInstance), config.getSchemaIRI(SchemaBaseConstants.Relationship.CUSTOMER), config.getCadenBaseDataIRI(userInstance)),
      svf.createStatement(config.getCadenBaseDataIRI(amazonOrderInstance), config.getCadenBaseIRI(CadenBaseConstants.SOURCE_INTAKE), config.getCadenBaseIRI(CadenBaseConstants.Intake.AMAZON)),
      svf.createStatement(config.getCadenBaseDataIRI(amazonOrderInstance), config.getSchemaIRI(SchemaBaseConstants.Relationship.BROKER), config.getCadenBaseIRI(CadenBaseConstants.Intake.AMAZON))
    ));

    if (amazonOrder.getOrderNumber() != null) {
      statements.add(
        svf.createStatement(config.getCadenBaseDataIRI(amazonOrderInstance), config.getSchemaIRI(SchemaBaseConstants.Property.ORDER_NUMBER), Values.literal(amazonOrder.getOrderNumber()))
      );
    }

    if (amazonOrder.getOrderDate() != null) {
      statements.add(
        svf.createStatement(config.getCadenBaseDataIRI(amazonOrderInstance), config.getSchemaIRI(SchemaBaseConstants.Property.ORDER_DATE), Values.literal(amazonOrder.getOrderDate()))
      );
    }

    if (amazonOrder.getBillingAddress() != null) {
      statements.add(
        svf.createStatement(config.getCadenBaseDataIRI(amazonOrderInstance), config.getSchemaIRI(SchemaBaseConstants.Property.BILLING_ADDRESS), Values.literal(amazonOrder.getBillingAddress()))
      );
    }

    if (amazonOrder.getOrderStatus() != null) {
      String orderStatusInstance = this.mapOrderStatus(amazonOrder.getOrderStatus());

      if (orderStatusInstance != null) {
        statements.add(svf.createStatement(
          config.getCadenBaseDataIRI(amazonOrderInstance),
          config.getSchemaIRI(SchemaBaseConstants.Relationship.ORDER_STATUS),
          config.getCadenReferenceIRI(orderStatusInstance)
        ));
      }
    }

    if (amazonOrder.getPaymentIdLastFour() != null) {
      statements.add(
        svf.createStatement(config.getCadenBaseDataIRI(amazonOrderInstance), config.getCadenBaseIRI(CadenBaseConstants.PAYMENT_ID_LAST_FOUR), Values.literal(amazonOrder.getPaymentIdLastFour()))
      );
    }

    if (amazonOrder.getPrice() != null) {
      statements.add(
        svf.createStatement(config.getCadenBaseDataIRI(amazonOrderInstance), config.getCadenBaseIRI(AmazonConstants.PRICE), Values.literal(amazonOrder.getPrice()))
      );
    }

    if (amazonOrder.getPriceCurrency() != null) {
      statements.add(
        svf.createStatement(config.getCadenBaseDataIRI(amazonOrderInstance), config.getCadenBaseIRI(AmazonConstants.PRICE_CURRENCY), Values.literal(amazonOrder.getPriceCurrency()))
      );
    }

    if (amazonOrder.getTax() != null) {
      statements.add(
        svf.createStatement(config.getCadenBaseDataIRI(amazonOrderInstance), config.getCadenBaseIRI(AmazonConstants.TAX), Values.literal(amazonOrder.getTax()))
      );
    }

    if (amazonOrder.getDeliveryFee() != null) {
      statements.add(
        svf.createStatement(config.getCadenBaseDataIRI(amazonOrderInstance), config.getCadenBaseIRI(AmazonConstants.DELIVERY_FEE), Values.literal(amazonOrder.getDeliveryFee()))
      );
    }

    return statements;
  }

  @Override
  public AmazonOrder reverseMap(final Collection<Statement> value) {
    throw new UnsupportedOperationException("Not implemented exception");
  }

  public static String getInstance(final AmazonOrder amazonOrder) {
    return RDFNamingConventionUtil.generateUserActionName(
      amazonOrder.getCadenAlias(),
      AmazonConstants.ORDER,
      amazonOrder.getOrderDate(),
      amazonOrder.getCreatedAt(),
      UUID.fromString(amazonOrder.getUuid())
    );
  }

  private String mapOrderStatus(final AmazonOrderStatus orderStatus) {
    switch(orderStatus) {
      case ORDER_CANCELLED:
        return SchemaBaseConstants.AmazonOrderStatus.ORDER_CANCELLED;
      case ORDER_DELIVERED:
        return SchemaBaseConstants.AmazonOrderStatus.ORDER_DELIVERED;
      case ORDER_IN_TRANSIT:
        return SchemaBaseConstants.AmazonOrderStatus.ORDER_IN_TRANSIT;
      case ORDER_PAYMENT_DUE:
        return SchemaBaseConstants.AmazonOrderStatus.ORDER_PAYMENT_DUE;
      case ORDER_PICKUP_AVAILABLE:
        return SchemaBaseConstants.AmazonOrderStatus.ORDER_PICKUP_AVAILABLE;
      case ORDER_PROBLEM:
        return SchemaBaseConstants.AmazonOrderStatus.ORDER_PROBLEM;
      case ORDER_PROCESSING:
        return SchemaBaseConstants.AmazonOrderStatus.ORDER_PROCESSING;
      case ORDER_RETURNED:
        return SchemaBaseConstants.AmazonOrderStatus.ORDER_RETURNED;
      case ORDER_REFUNDED:
        return SchemaBaseConstants.AmazonOrderStatus.ORDER_REFUNDED;
      case ORDER_EXCHANGED:
        return SchemaBaseConstants.AmazonOrderStatus.ORDER_EXCHANGED;

      default:
        return null;
    }

  }
}
