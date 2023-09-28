package io.caden.transformers.amazon.repositories.impl;

import io.caden.transformers.amazon.entities.AmazonOrder;
import io.caden.transformers.amazon.mappers.AmazonOrderToStatements;
import io.caden.transformers.amazon.models.ExistingAmazonOrder;
import io.caden.transformers.amazon.repositories.AmazonOrderRepository;
import io.caden.transformers.amazon.utils.AmazonConstants;
import io.caden.transformers.config.RDFConfiguration;
import io.caden.transformers.shared.repositories.BaseRepository;
import io.caden.transformers.shared.utils.CadenBaseConstants;
import io.caden.transformers.shared.utils.RDFNamingConventionUtil;
import io.caden.transformers.shared.utils.RDFUtil;
import io.caden.transformers.shared.utils.SchemaBaseConstants;
import io.micrometer.core.instrument.util.StringUtils;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.rdf4j.model.IRI;
import org.eclipse.rdf4j.model.Statement;
import org.eclipse.rdf4j.query.BindingSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static net.logstash.logback.argument.StructuredArguments.kv;

@Repository("amazonOrderRepo")
@Slf4j
public class AmazonOrderRepositoryImpl extends BaseRepository<AmazonOrder> implements AmazonOrderRepository {

  private final RDFConfiguration config;
  private final AmazonOrderToStatements amazonOrderToStatements;
  private final Pattern pattern;

  public AmazonOrderRepositoryImpl(
    @Autowired final RDFConfiguration config,
    @Autowired final AmazonOrderToStatements amazonOrderToStatements
  ) {
    super(config);
    this.config = config;
    this.amazonOrderToStatements = amazonOrderToStatements;
    this.pattern = Pattern.compile("(\\w*$)");
  }

  @Override
  public AmazonOrder find(final String filter) throws Exception {
    throw new UnsupportedOperationException("Not implemented exception");
  }

    @Override
    public Collection<Statement> toStatements(AmazonOrder amazonOrder) {
        return this.amazonOrderToStatements.map(amazonOrder);
    }

    @Override
  public void delete(final AmazonOrder amazonOrder) throws Exception {
    List<String> predicateWithNamespaces = Arrays.asList(
      config.getSchemaBase(SchemaBaseConstants.Property.ORDER_NUMBER),
      config.getSchemaBase(SchemaBaseConstants.Property.ORDER_DATE),
      config.getSchemaBase(SchemaBaseConstants.Property.BILLING_ADDRESS),
      config.getSchemaBase(SchemaBaseConstants.Relationship.ORDER_STATUS),
      config.getCadenBase(CadenBaseConstants.PAYMENT_ID_LAST_FOUR),
      config.getCadenBase(AmazonConstants.PRICE),
      config.getCadenBase(AmazonConstants.PRICE_CURRENCY),
      config.getCadenBase(AmazonConstants.TAX),
      config.getCadenBase(AmazonConstants.DELIVERY_FEE)
    );

    String previousOrderId = amazonOrder.getPreviousOrderId();
    String instance = StringUtils.isEmpty(previousOrderId) ? AmazonOrderToStatements.getInstance(amazonOrder) : previousOrderId;

    IRI userInstance = config.getCadenBaseDataIRI(RDFNamingConventionUtil.generateUserGraphName(amazonOrder.getCadenAlias()));

    super.delete(config.getCadenBaseDataBase(instance), predicateWithNamespaces, userInstance);
  }

  @Override
  public Map<String, ExistingAmazonOrder> findExistingOrdersByCadenAliasAndOrderNumbers(String cadenAlias, List<String> orderIds) {
      String queryString = ""
              + "PREFIX cdn: <" + config.getCadenBaseNamespace() + "> "
              + "PREFIX sch: <" + config.getSchemaNamespace() + "> "
              + "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> "
              + "SELECT ?orderNumber ?orderStatus ?formattedOrderId{ "
              + "GRAPH ?g { "
                  + "?s a cdn:Order; "
                  + "rdfs:label ?formattedOrderId; "
                  + "sch:orderNumber ?orderNumber; "
                  + "optional {?s sch:orderStatus ?orderStatus} . "
                  + " FILTER(?orderNumber IN (?l))"
              + "} "
              + "} ";

      Map<String, Object> params = new HashMap<>();

      params.put("g", config.getCadenBaseDataIRI(RDFNamingConventionUtil.generateUserGraphName(cadenAlias)));
      params.put("l", new OrderIds(orderIds));

      try {
          long startTime = System.currentTimeMillis();
          List<BindingSet> bindingSets = this.executeQueryList(queryString, params);
          long elapsedTime = System.currentTimeMillis() - startTime;
          log.info("Amazon order lookup took {}ms", elapsedTime, kv("elapsed_time", elapsedTime));
          return toMapValue(bindingSets);
      } catch (Exception e) {
          log.error("Error findExistingOrdersByCadenAliasAndOrderNumbers.", e);
      }

      return new HashMap<>();
  }

    private Map<String, ExistingAmazonOrder> toMapValue(List<BindingSet> bindingSets) {
        Map<String, ExistingAmazonOrder> existingOrdersMap = new HashMap<>();
        for (BindingSet bindingSet :
                bindingSets) {
            String orderNumber = RDFUtil.getValue(bindingSet, "orderNumber");
            String orderId = RDFUtil.getValue(bindingSet, "formattedOrderId");
            String orderStatusUrl = RDFUtil.getValue(bindingSet, "orderStatus");

            String ordersStatusStr = "NONE";

            if (orderStatusUrl != null) {
                Matcher matcher = pattern.matcher(orderStatusUrl);
                ordersStatusStr = matcher.find() ? matcher.group() : "NONE";
            }

            existingOrdersMap.put(orderNumber, new ExistingAmazonOrder(orderId, orderNumber, ordersStatusStr));
        }
        return existingOrdersMap;
    }

    @Data
    @AllArgsConstructor
    public class OrderIds {
        private List<String> orderIds;

        @Override
        public String toString() {
            return orderIds.stream().map(s -> "\"" + s + "\"").collect(Collectors.joining(", "));
        }
    }
}
