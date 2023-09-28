package io.caden.transformers.amazon.repositories;

import io.caden.transformers.amazon.entities.AmazonGraphDbBatch;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AmazonGraphDbBatchRepository extends JpaRepository<AmazonGraphDbBatch, Integer> {
    AmazonGraphDbBatch findFirstByOrderById();
}
