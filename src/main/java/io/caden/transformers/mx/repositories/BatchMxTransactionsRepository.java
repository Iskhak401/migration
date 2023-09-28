package io.caden.transformers.mx.repositories;

import io.caden.transformers.mx.entities.BatchMxTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BatchMxTransactionsRepository extends JpaRepository<BatchMxTransaction, Long> {
}
