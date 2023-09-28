package io.caden.transformers.amazon.entities;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "amazon_graphdb_batch")
public class AmazonGraphDbBatch {
    @Id
    @GeneratedValue
    private Integer id;
    @Column(name = "batch_size")
    private int batchSize;
}
