package io.caden.transformers.netflix.repositories;

import io.caden.transformers.netflix.entities.NetflixGraphDbBatch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NetflixGraphDbBatchRepository extends JpaRepository<NetflixGraphDbBatch, Integer> {
    NetflixGraphDbBatch findFirstByOrderById();
}
