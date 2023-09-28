package io.caden.transformers.netflix.entities;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "netflix_graphdb_batch")
public class NetflixGraphDbBatch {
    @Id
    @GeneratedValue
    private Integer id;
    @Column(name = "batch_size")
    private int batchSize;
}
