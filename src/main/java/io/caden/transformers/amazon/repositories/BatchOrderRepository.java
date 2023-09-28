package io.caden.transformers.amazon.repositories;

import io.caden.transformers.amazon.entities.AmazonOrder;
import io.caden.transformers.amazon.entities.BatchAmazonOrder;
import io.caden.transformers.shared.repositories.BaseBatchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BatchOrderRepository extends BaseBatchRepository<AmazonOrder, BatchAmazonOrder> {
}
