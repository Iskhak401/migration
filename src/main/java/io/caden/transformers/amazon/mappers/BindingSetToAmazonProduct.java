package io.caden.transformers.amazon.mappers;

import org.eclipse.rdf4j.query.BindingSet;
import org.springframework.stereotype.Component;

import io.caden.transformers.amazon.entities.AmazonProduct;
import io.caden.transformers.amazon.utils.AmazonConstants;
import io.caden.transformers.shared.mappers.Mapper;
import io.caden.transformers.shared.utils.CadenBaseConstants;
import io.caden.transformers.shared.utils.RDFUtil;
import io.caden.transformers.shared.utils.SchemaBaseConstants;

@Component
public class BindingSetToAmazonProduct extends Mapper<BindingSet, AmazonProduct> {

  @Override
  public AmazonProduct map(final BindingSet bindingSet) {
    if (bindingSet == null) {
      return null;
    }

    AmazonProduct amazonProduct = new AmazonProduct();
    amazonProduct.setAsin(RDFUtil.getValue(bindingSet, SchemaBaseConstants.Property.IDENTIFIER));
    amazonProduct.setBrand(RDFUtil.getValue(bindingSet, SchemaBaseConstants.Property.BRAND));
    amazonProduct.setWeight(RDFUtil.getValue(bindingSet, SchemaBaseConstants.Property.WEIGHT));
    amazonProduct.setDimensions(RDFUtil.getValue(bindingSet, SchemaBaseConstants.Property.SIZE));
    amazonProduct.setFirstAvailable(RDFUtil.getDate(RDFUtil.getValue(bindingSet, SchemaBaseConstants.Property.FIRST_AVAILABLE)));

    String rating = RDFUtil.getValue(bindingSet, AmazonConstants.RATING);
    if (rating != null) {
      amazonProduct.setRating(Double.parseDouble(rating));
    }

    String ratingsTotal = RDFUtil.getValue(bindingSet, SchemaBaseConstants.Property.RATINGS_TOTAL);
    if (ratingsTotal != null) {
      amazonProduct.setRatingsTotal(Integer.parseInt(ratingsTotal));
    }

    String reviewsTotal = RDFUtil.getValue(bindingSet, AmazonConstants.REVIEWS_TOTAL);
    if (reviewsTotal != null) {
      amazonProduct.setReviewsTotal(Integer.parseInt(reviewsTotal));
    }

    amazonProduct.setCreatedAt(RDFUtil.getDate(RDFUtil.getValue(bindingSet, CadenBaseConstants.CREATED_AT)));
    amazonProduct.setUpdatedAt(RDFUtil.getDate(RDFUtil.getValue(bindingSet, CadenBaseConstants.UPDATED_AT)));

    return amazonProduct;
  }

  @Override
  public BindingSet reverseMap(final AmazonProduct amazonProduct) {
    throw new UnsupportedOperationException("Not implemented exception");
  }
}
