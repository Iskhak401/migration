package io.caden.transformers.location.repositories;

import io.caden.transformers.location.entities.BatchMoveAction;
import io.caden.transformers.location.entities.MoveAction;
import io.caden.transformers.shared.repositories.BaseBatchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BatchMoveActionRepository extends BaseBatchRepository<MoveAction, BatchMoveAction> {
}
