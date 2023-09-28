package io.caden.transformers.amazon.mappers;

import io.caden.transformers.amazon.entities.AmazonOrder;
import io.caden.transformers.amazon.entities.AmazonParcelDelivery;
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
public class AmazonParcelDeliveryToStatements {
  private final RDFConfiguration config;
  private final SimpleValueFactory svf = SimpleValueFactory.getInstance();

  public AmazonParcelDeliveryToStatements(@Autowired final RDFConfiguration config) {
    this.config = config;
  }

  public Collection<Statement> map(final AmazonParcelDelivery parcelDelivery, final AmazonOrder amazonOrder) {
    String amazonParcelDeliveryInstance = getInstance(amazonOrder);

    List<Statement> statements =  new ArrayList<>(Arrays.asList(
      svf.createStatement(config.getCadenBaseDataIRI(amazonParcelDeliveryInstance), RDF.TYPE, config.getSchemaIRI(SchemaBaseConstants.Class.PARCEL_DELIVERY)),
      svf.createStatement(config.getCadenBaseDataIRI(amazonParcelDeliveryInstance), RDF.TYPE, config.getOwlIRI(OwlConstants.NAMED_INDIVIDUAL)),
      svf.createStatement(config.getCadenBaseDataIRI(amazonParcelDeliveryInstance), RDFS.LABEL, Values.literal(amazonParcelDeliveryInstance))
    ));

    if(parcelDelivery.getTrackingNumber() != null) {
      statements.add(
        svf.createStatement(config.getCadenBaseDataIRI(amazonParcelDeliveryInstance), config.getSchemaIRI(SchemaBaseConstants.Property.TRACKING_NUMBER), Values.literal(parcelDelivery.getTrackingNumber()))
      );
    }

    if (parcelDelivery.getDeliveryAddress() != null) {
      statements.add(
        svf.createStatement(config.getCadenBaseDataIRI(amazonParcelDeliveryInstance), config.getSchemaIRI(SchemaBaseConstants.Property.DELIVERY_ADDRESS), Values.literal(parcelDelivery.getDeliveryAddress()))
      );
    }

    if (parcelDelivery.getDeliveryMethod() != null) {
      statements.add(
        svf.createStatement(config.getCadenBaseDataIRI(amazonParcelDeliveryInstance), config.getSchemaIRI(SchemaBaseConstants.Relationship.HAS_DELIVERY_METHOD), config.getSchemaIRI(parcelDelivery.getDeliveryMethod().label))
      );
    }

    return statements;
  }

  public AmazonParcelDelivery reverseMap(final Collection<Statement> value) {
    throw new UnsupportedOperationException("Not implemented exception");
  }

  public static String getInstance(final AmazonOrder amazonOrder) {
    return RDFNamingConventionUtil.generateAuxiliaryName(
      SchemaBaseConstants.Class.PARCEL_DELIVERY,
      SchemaBaseConstants.Class.ORDER_ITEM,
      amazonOrder.getOrderDate(),
      UUID.fromString(amazonOrder.getUuid())
    );
  }
}
