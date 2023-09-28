package io.caden.transformers.netflix.repositories;

import io.caden.transformers.netflix.entities.NetflixViewingActivity;
import io.caden.transformers.shared.repositories.Repository;

import java.util.Date;

public interface NetflixViewingActivityRepository extends Repository<NetflixViewingActivity> {
  Boolean existsByCadenAliasAndIdentifierAndStartTime(String cadenAlias, Long identifier, Date startTime);
}
