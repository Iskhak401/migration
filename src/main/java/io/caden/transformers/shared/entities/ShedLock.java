package io.caden.transformers.shared.entities;

import lombok.RequiredArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Timestamp;

@Entity
@Table(name = "shedlock")
public class ShedLock {

    @Id
    private String name;
    @Column(name = "lock_until")
    private Timestamp lockUntil;
    @Column(name = "locked_at")
    private Timestamp lockedAt;
    @Column(name = "locked_by")
    private String lockedBy;
}
