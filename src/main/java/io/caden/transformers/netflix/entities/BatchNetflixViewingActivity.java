package io.caden.transformers.netflix.entities;

import io.caden.transformers.shared.entities.BaseBatchObject;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "netflix_batch_viewing_activity")
public class BatchNetflixViewingActivity extends BaseBatchObject<NetflixViewingActivity> {
}
