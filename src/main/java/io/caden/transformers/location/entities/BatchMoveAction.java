package io.caden.transformers.location.entities;

import io.caden.transformers.shared.entities.BaseBatchObject;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "location_batch_move_action")
public class BatchMoveAction extends BaseBatchObject<MoveAction> {
}
