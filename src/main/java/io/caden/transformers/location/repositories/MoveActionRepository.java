package io.caden.transformers.location.repositories;

import io.caden.transformers.location.entities.MoveAction;
import io.caden.transformers.shared.repositories.Repository;

import java.util.Date;
import java.util.Map;

public interface MoveActionRepository extends Repository<MoveAction> {
  Map<Date, String> findPreviousLocationByCadenAlias(String cadenAlias) throws Exception;
}
