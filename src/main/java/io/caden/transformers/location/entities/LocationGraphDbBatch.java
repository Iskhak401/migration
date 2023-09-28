package io.caden.transformers.location.entities;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "location_graphdb_batch")
public class LocationGraphDbBatch {
    @Id
    @GeneratedValue
    private Integer id;
    @Column(name = "batch_size")
    private int batchSize;
}
