package io.caden.transformers.amazon.entities;

import io.caden.transformers.shared.entities.BaseBatchObject;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "amazon_batch_order")
public class BatchAmazonOrder extends BaseBatchObject<AmazonOrder> {
}
