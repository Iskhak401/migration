package io.caden.transformers.netflix.repositories;

import io.caden.transformers.netflix.entities.BatchNetflixViewingActivity;
import io.caden.transformers.netflix.entities.NetflixViewingActivity;
import io.caden.transformers.shared.repositories.BaseBatchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BatchViewingActivityRepository extends BaseBatchRepository<NetflixViewingActivity, BatchNetflixViewingActivity> {
}
