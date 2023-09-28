package io.caden.transformers.amazon.entities;

import io.caden.transformers.shared.entities.RDFAbstractEntity;
import lombok.Data;

@Data
public class AmazonProductFeatureBullet extends RDFAbstractEntity {

  private String bullet;

}
