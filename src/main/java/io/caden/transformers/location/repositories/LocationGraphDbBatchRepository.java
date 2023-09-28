package io.caden.transformers.location.repositories;

import io.caden.transformers.location.entities.LocationGraphDbBatch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LocationGraphDbBatchRepository extends JpaRepository<LocationGraphDbBatch, Integer> {
    LocationGraphDbBatch findFirstByOrderById();
}
