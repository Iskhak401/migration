package io.caden.transformers.ahk.repositories;

import io.caden.transformers.ahk.entities.AHKActivitySummary;
import io.caden.transformers.shared.repositories.Repository;

public interface AHKActivitySummaryRepository extends Repository<AHKActivitySummary> {
  void update(AHKActivitySummary ahkActivitySummary) throws Exception;
}
