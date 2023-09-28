package io.caden.transformers.mx.repositories;

import io.caden.transformers.mx.entities.MxGraphDbBatch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MxGraphDbBatchRepository extends JpaRepository<MxGraphDbBatch, Long> {
    MxGraphDbBatch findFirstByOrderById();
}
