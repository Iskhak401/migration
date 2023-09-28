package io.caden.transformers.mx.mappers;

import io.caden.transformers.config.RDFConfiguration;
import io.caden.transformers.mx.entities.MxMerchant;
import io.caden.transformers.mx.entities.MxMerchantLocation;
import io.caden.transformers.mx.entities.MxTransaction;
import io.caden.transformers.shared.mappers.Mapper;
import io.caden.transformers.shared.utils.CadenBaseConstants;
import io.caden.transformers.shared.utils.OwlConstants;
import io.caden.transformers.shared.utils.RDFNamingConventionUtil;
import io.caden.transformers.shared.utils.SchemaBaseConstants;
import lombok.extern.log4j.Log4j2;
import org.eclipse.rdf4j.model.IRI;
import org.eclipse.rdf4j.model.Statement;
import org.eclipse.rdf4j.model.impl.SimpleValueFactory;
import org.eclipse.rdf4j.model.util.Values;
import org.eclipse.rdf4j.model.vocabulary.RDF;
import org.eclipse.rdf4j.model.vocabulary.RDFS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@Log4j2
public class MxTransactionToStatements extends Mapper<MxTransaction, Collection<Statement>> {

  private final RDFConfiguration config;
  private final SimpleValueFactory svf = SimpleValueFactory.getInstance();

  public MxTransactionToStatements(@Autowired final RDFConfiguration config) {
    this.config = config;
  }

  @Override
  public Collection<Statement> map(final MxTransaction mxTransaction) {
    throw new UnsupportedOperationException("Not implemented exception");
  }

  @Override
  public MxTransaction reverseMap(final Collection<Statement> value) {
    throw new UnsupportedOperationException("Not implemented exception");
  }

  public String getTransactionInstance(MxTransaction transaction) {
    return RDFNamingConventionUtil.generateUserActionName(
      transaction.getCadenAlias(),
      this.getClassNameByCategory(transaction),
      transaction.getOrderDate(),
      transaction.getInstanceTimestamp(),
      UUID.fromString(transaction.getUuid())
    );
  }

  public String getGeoCoordinatesInstance(MxTransaction transaction) {
    return RDFNamingConventionUtil.generateAuxiliaryName(
      SchemaBaseConstants.Class.GEO_COORDINATES,
      this.getClassNameByCategory(transaction),
      transaction.getOrderDate(),
      UUID.fromString(transaction.getUuid())
    );
  }

  public String getMxMerchantInstance(MxTransaction transaction) {
    return RDFNamingConventionUtil.generateSlashReferenceName(CadenBaseConstants.Intake.MX, transaction.getMxMerchant().getIdentifier());
  }

  public String getStoreInstance(MxTransaction transaction) {
    return RDFNamingConventionUtil.generateSlashReferenceName(CadenBaseConstants.Intake.MX, transaction.getMxMerchantLocation().getIdentifier());
  }

  public String getPostalAddressInstance(MxTransaction transaction) {
    return RDFNamingConventionUtil.generateAuxiliaryName(SchemaBaseConstants.Class.POSTAL_ADDRESS, SchemaBaseConstants.Class.STORE,
            transaction.getOrderDate(), UUID.fromString(transaction.getUuid()));
  }

  public Collection<Statement> mapToCadenTransaction(final MxTransaction transaction) {
    String cadenAlias = transaction.getCadenAlias();
    String actionClassName = this.getClassNameByCategory(transaction);
    String actionInstance = this.getTransactionInstance(transaction);
    String userInstance = RDFNamingConventionUtil.generateUserGraphName(cadenAlias);

    List<Statement> statements = new ArrayList<>(Arrays.asList(
      svf.createStatement(config.getCadenBaseDataIRI(actionInstance), RDF.TYPE, config.getCadenBaseIRI(actionClassName)),
      svf.createStatement(config.getCadenBaseDataIRI(actionInstance), RDF.TYPE, config.getOwlIRI(OwlConstants.NAMED_INDIVIDUAL)),
      svf.createStatement(config.getCadenBaseDataIRI(actionInstance), RDFS.LABEL, Values.literal(actionInstance)),
      svf.createStatement(config.getCadenBaseDataIRI(actionInstance), config.getCadenBaseIRI(CadenBaseConstants.SOURCE_INTAKE), config.getCadenBaseIRI(CadenBaseConstants.Intake.MX)),
      svf.createStatement(config.getCadenBaseDataIRI(actionInstance), config.getSchemaIRI(SchemaBaseConstants.Relationship.AGENT), config.getCadenBaseDataIRI(userInstance)),
      svf.createStatement(config.getCadenBaseDataIRI(actionInstance), config.getSchemaIRI(SchemaBaseConstants.Property.PRICE), Values.literal(transaction.getPrice())),
      svf.createStatement(config.getCadenBaseDataIRI(actionInstance), config.getSchemaIRI(SchemaBaseConstants.Property.START_TIME), Values.literal(transaction.getOrderDate())),
      svf.createStatement(config.getCadenBaseDataIRI(actionInstance), config.getSchemaIRI(SchemaBaseConstants.Property.DESCRIPTION), Values.literal(transaction.getDescription())),
      svf.createStatement(config.getCadenBaseDataIRI(actionInstance), config.getSchemaIRI(SchemaBaseConstants.Property.IDENTIFIER), Values.literal(transaction.getMxGuid())),
      svf.createStatement(config.getCadenBaseDataIRI(actionInstance), config.getCadenBaseIRI(CadenBaseConstants.IS_CREDIT), Values.literal(transaction.getType().equalsIgnoreCase("CREDIT"))),
      svf.createStatement(config.getCadenBaseDataIRI(actionInstance), config.getCadenBaseIRI(CadenBaseConstants.TRANSACTION_CATEGORY), Values.literal(transaction.getCategory())),
      svf.createStatement(config.getCadenBaseDataIRI(actionInstance), config.getCadenBaseIRI(CadenBaseConstants.TRANSACTION_CATEGORY_ID), Values.literal(transaction.getCategoryGuid())),
      svf.createStatement(config.getCadenBaseDataIRI(actionInstance), config.getCadenBaseIRI(CadenBaseConstants.TOP_TRANSACTION_CATEGORY), Values.literal(transaction.getTopLevelCategory())),
      svf.createStatement(config.getCadenBaseDataIRI(actionInstance), config.getCadenBaseIRI(CadenBaseConstants.IS_BILL_PAY), Values.literal(transaction.getIsBillPay())),
      svf.createStatement(config.getCadenBaseDataIRI(actionInstance), config.getCadenBaseIRI(CadenBaseConstants.IS_DIRECT_DEPOSIT), Values.literal(transaction.getIsDirectDeposit())),
      svf.createStatement(config.getCadenBaseDataIRI(actionInstance), config.getCadenBaseIRI(CadenBaseConstants.IS_EXPENSE), Values.literal(transaction.getIsExpense())),
      svf.createStatement(config.getCadenBaseDataIRI(actionInstance), config.getCadenBaseIRI(CadenBaseConstants.IS_FEE), Values.literal(transaction.getIsFee())),
      svf.createStatement(config.getCadenBaseDataIRI(actionInstance), config.getCadenBaseIRI(CadenBaseConstants.IS_INCOME), Values.literal(transaction.getIsIncome())),
      svf.createStatement(config.getCadenBaseDataIRI(actionInstance), config.getCadenBaseIRI(CadenBaseConstants.IS_OVERDRAFT_FEE), Values.literal(transaction.getIsOverdraftFee())),
      svf.createStatement(config.getCadenBaseDataIRI(actionInstance), config.getCadenBaseIRI(CadenBaseConstants.IS_PAYROLL_ADVANCE), Values.literal(transaction.getIsPayrollAdvance())),
      svf.createStatement(config.getCadenBaseDataIRI(actionInstance), config.getCadenBaseIRI(CadenBaseConstants.IS_SUBSCRIPTION), Values.literal(transaction.getIsSubscription()))
    ));

    if (transaction.getPriceCurrency() != null) {
      statements.add(svf.createStatement(
        config.getCadenBaseDataIRI(actionInstance),
        config.getSchemaIRI(SchemaBaseConstants.Property.PRICE_CURRENCY),
        Values.literal(transaction.getPriceCurrency())
      ));
    }

    if (transaction.getMxAccount() != null && transaction.getMxAccount().getPaymentId() != null) {
      String paymentId = transaction.getMxAccount().getPaymentId();
      String paymentIdLastFour = paymentId.substring(Math.max(0, paymentId.length() - 4));

      statements.add(svf.createStatement(
        config.getCadenBaseDataIRI(actionInstance),
        config.getCadenBaseIRI(CadenBaseConstants.PAYMENT_ID_LAST_FOUR),
        Values.literal(paymentIdLastFour)
      ));
    }

    if (transaction.getIsRecurring() != null) {
      statements.add(svf.createStatement(
        config.getCadenBaseDataIRI(actionInstance),
        config.getCadenBaseIRI(CadenBaseConstants.IS_RECURRING),
        Values.literal(transaction.getIsRecurring())
      ));
    }

    if (transaction.getIsInternational() != null) {
      statements.add(svf.createStatement(
        config.getCadenBaseDataIRI(actionInstance),
        config.getCadenBaseIRI(CadenBaseConstants.IS_INTERNATIONAL),
        Values.literal(transaction.getIsInternational())
      ));
    }

    if (transaction.getTransactionId() != null) {
      statements.add(svf.createStatement(
        config.getCadenBaseDataIRI(actionInstance),
        config.getCadenBaseIRI(CadenBaseConstants.BANK_TRANSACTION_ID),
        Values.literal(transaction.getTransactionId())
      ));
    }

    if (transaction.getLatitude() != null && transaction.getLongitude() != null) {
      String coordinatesInstance = this.getGeoCoordinatesInstance(transaction);

      statements.addAll(Arrays.asList(
        svf.createStatement(config.getCadenBaseDataIRI(coordinatesInstance), RDF.TYPE, config.getSchemaIRI(SchemaBaseConstants.Class.GEO_COORDINATES)),
        svf.createStatement(config.getCadenBaseDataIRI(coordinatesInstance), RDF.TYPE, config.getOwlIRI(OwlConstants.NAMED_INDIVIDUAL)),
        svf.createStatement(config.getCadenBaseDataIRI(coordinatesInstance), RDFS.LABEL, Values.literal(coordinatesInstance)),
        svf.createStatement(config.getCadenBaseDataIRI(coordinatesInstance), config.getSchemaIRI(SchemaBaseConstants.Property.LATITUDE), Values.literal(transaction.getLatitude())),
        svf.createStatement(config.getCadenBaseDataIRI(coordinatesInstance), config.getSchemaIRI(SchemaBaseConstants.Property.LONGITUDE), Values.literal(transaction.getLongitude())),
        svf.createStatement(config.getCadenBaseDataIRI(actionInstance), config.getCadenBaseIRI(CadenBaseConstants.LOCATION), config.getCadenBaseDataIRI(coordinatesInstance))
      ));
    }

    if (transaction.getMxMerchantLocation() != null) {
      String storeInstance = this.getStoreInstance(transaction);

      statements.add(svf.createStatement(
        config.getCadenBaseDataIRI(actionInstance),
        this.getSellerOrRecipient(transaction),
        config.getCadenReferenceDataIRI(storeInstance)
      ));
    } else if (transaction.getMxMerchant() != null) {
      String mxMerchantInstance = this.getMxMerchantInstance(transaction);

      statements.add(svf.createStatement(
        config.getCadenBaseDataIRI(actionInstance),
        this.getSellerOrRecipient(transaction),
        config.getCadenReferenceDataIRI(mxMerchantInstance)
      ));
    } else {
      log.info("Merchant not found for transactionGuid={}", transaction.getMxGuid());
    }

    return statements;
  }

  public Collection<Statement> mapToMxMerchant(final MxTransaction transaction) {
    String merchantInstance = this.getMxMerchantInstance(transaction);
    MxMerchant merchant = transaction.getMxMerchant();

    List<Statement> statements = new ArrayList<>(Arrays.asList(
      svf.createStatement(config.getCadenReferenceDataIRI(merchantInstance), RDF.TYPE, config.getCadenBaseIRI(CadenBaseConstants.MX_MERCHANT)),
      svf.createStatement(config.getCadenReferenceDataIRI(merchantInstance), RDF.TYPE, config.getOwlIRI(OwlConstants.NAMED_INDIVIDUAL)),
      svf.createStatement(config.getCadenReferenceDataIRI(merchantInstance), RDFS.LABEL, Values.literal(merchantInstance)),
      svf.createStatement(config.getCadenReferenceDataIRI(merchantInstance), config.getSchemaIRI(SchemaBaseConstants.Property.NAME), Values.literal(merchant.getName())),
      svf.createStatement(config.getCadenReferenceDataIRI(merchantInstance), config.getSchemaIRI(SchemaBaseConstants.Property.IDENTIFIER), Values.literal(merchant.getIdentifier()))
    ));

    if (merchant.getUrl() != null) {
      statements.add(svf.createStatement(
        config.getCadenReferenceDataIRI(merchantInstance),
        config.getSchemaIRI(SchemaBaseConstants.Property.URL),
        Values.literal(merchant.getUrl())
      ));
    }

    if (merchant.getLogo() != null) {
      statements.add(svf.createStatement(
        config.getCadenReferenceDataIRI(merchantInstance),
        config.getSchemaIRI(SchemaBaseConstants.Property.LOGO),
        Values.literal(merchant.getLogo())
      ));
    }

    if (transaction.getMxMerchantLocation() != null) {
      String storeInstance = this.getStoreInstance(transaction);

      statements.add(svf.createStatement(
        config.getCadenReferenceDataIRI(merchantInstance),
        config.getSchemaIRI(SchemaBaseConstants.Relationship.SUB_ORGANIZATION),
        config.getCadenReferenceDataIRI(storeInstance)
      ));
    }

    return statements;
  }

  public Collection<Statement> mapToStore(final MxTransaction transaction) {
    String storeInstance = this.getStoreInstance(transaction);
    String addressInstance = this.getPostalAddressInstance(transaction);
    MxMerchantLocation location = transaction.getMxMerchantLocation();

    List<Statement> statements = new ArrayList<>(Arrays.asList(
      svf.createStatement(config.getCadenReferenceDataIRI(storeInstance), RDF.TYPE, config.getSchemaIRI(SchemaBaseConstants.Class.STORE)),
      svf.createStatement(config.getCadenReferenceDataIRI(storeInstance), RDF.TYPE, config.getOwlIRI(OwlConstants.NAMED_INDIVIDUAL)),
      svf.createStatement(config.getCadenReferenceDataIRI(storeInstance), RDFS.LABEL, Values.literal(storeInstance)),
      svf.createStatement(config.getCadenReferenceDataIRI(storeInstance), config.getSchemaIRI(SchemaBaseConstants.Property.IDENTIFIER), Values.literal(location.getIdentifier())),
      svf.createStatement(config.getCadenReferenceDataIRI(storeInstance), config.getSchemaIRI(SchemaBaseConstants.Relationship.ADDRESS), config.getSchemaIRI(addressInstance))
    ));

    if (location.getLatitude() != null) {
      statements.add(svf.createStatement(
        config.getCadenReferenceDataIRI(storeInstance),
        config.getSchemaIRI(SchemaBaseConstants.Property.LATITUDE),
        Values.literal(location.getLatitude())
      ));
    }

    if (location.getLongitude() != null) {
      statements.add(svf.createStatement(
        config.getCadenReferenceDataIRI(storeInstance),
        config.getSchemaIRI(SchemaBaseConstants.Property.LONGITUDE),
        Values.literal(location.getLongitude())
      ));
    }

    if (location.getTelephone() != null) {
      statements.add(svf.createStatement(
        config.getCadenReferenceDataIRI(storeInstance),
        config.getSchemaIRI(SchemaBaseConstants.Property.TELEPHONE),
        Values.literal(location.getTelephone())
      ));
    }

    if (location.getStreetAddress() != null) {
      statements.add(svf.createStatement(
        config.getCadenReferenceDataIRI(addressInstance),
        config.getSchemaIRI(SchemaBaseConstants.Property.STREET_ADDRESS),
        Values.literal(location.getStreetAddress())
      ));
    }

    if (location.getAddressLocality() != null) {
      statements.add(svf.createStatement(
        config.getCadenReferenceDataIRI(addressInstance),
        config.getSchemaIRI(SchemaBaseConstants.Property.ADDRESS_LOCALITY),
        Values.literal(location.getAddressLocality())
      ));
    }

    if (location.getAddressRegion() != null) {
      statements.add(svf.createStatement(
        config.getCadenReferenceDataIRI(addressInstance),
        config.getSchemaIRI(SchemaBaseConstants.Property.ADDRESS_REGION),
        Values.literal(location.getAddressRegion())
      ));
    }

    if (location.getAddressCountry() != null) {
      statements.add(svf.createStatement(
        config.getCadenReferenceDataIRI(addressInstance),
        config.getSchemaIRI(SchemaBaseConstants.Property.ADDRESS_COUNTRY),
        Values.literal(location.getAddressCountry())
      ));
    }

    if (location.getPostalCode() != null) {
      statements.add(svf.createStatement(
        config.getCadenReferenceDataIRI(addressInstance),
        config.getSchemaIRI(SchemaBaseConstants.Property.POSTAL_CODE),
        Values.literal(location.getPostalCode())
      ));
    }

    if (transaction.getMxMerchant() != null) {
      String merchantInstance = this.getMxMerchantInstance(transaction);

      statements.add(svf.createStatement(
        config.getCadenReferenceDataIRI(storeInstance),
        config.getSchemaIRI(SchemaBaseConstants.Relationship.PARENT_ORGANIZATION),
        config.getCadenReferenceDataIRI(merchantInstance)
      ));
    }

    return statements;
  }

  private String getClassNameByCategory(MxTransaction transaction) {
    if (transaction.getCategory() == "Mortgage & Rent") {
      return CadenBaseConstants.PAY_ACTION;
    }

    switch(transaction.getTopLevelCategory()) {
      case "Taxes":
      case "Fees & Charges":
      case "Transfer":
        return CadenBaseConstants.PAY_ACTION;
      case "Gifts & Donations":
        return CadenBaseConstants.DONATE_ACTION;
      default:
        return CadenBaseConstants.BUY_ACTION;
    }
  }

  private IRI getSellerOrRecipient(MxTransaction transaction) {
    String className = this.getClassNameByCategory(transaction);

    if (className.equals(CadenBaseConstants.PAY_ACTION) || className.equals(CadenBaseConstants.DONATE_ACTION)) {
      return config.getSchemaIRI(SchemaBaseConstants.Relationship.RECIPIENT);
    } else {
      return config.getSchemaIRI(SchemaBaseConstants.Relationship.SELLER);
    }
  }
}