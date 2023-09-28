package io.caden.transformers.ahk.repositories;

import io.caden.transformers.ahk.entities.AHKWorkout;
import io.caden.transformers.shared.repositories.Repository;

public interface AHKWorkoutRepository extends Repository<AHKWorkout> {
  void update(AHKWorkout ahkWorkout) throws Exception;
}
