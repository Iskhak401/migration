package io.caden.transformers.mx.entities;


import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "mx_graphdb_batch")
public class MxGraphDbBatch {
    @Id
    @GeneratedValue
    private Integer id;
    @Column(name = "batch_size")
    private int batchSize;
}
