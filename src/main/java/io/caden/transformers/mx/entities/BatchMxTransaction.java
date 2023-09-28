package io.caden.transformers.mx.entities;

import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.Data;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Data
@Entity
@Table(name = "mx_batch_transaction")
@TypeDefs({
        @TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
})
public class BatchMxTransaction {
    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "caden_alias")
    private UUID cadenAlias;

    @Column(name = "cognito_id")
    private UUID cognitoId;

    @Column(name = "extraction_id")
    private UUID extractionId;

    @Column(columnDefinition = "jsonb")
    @Type(type = "jsonb")
    private MxTransaction transaction;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at", updatable = false, nullable = false)
    private Date createdAt;

    @PrePersist
    protected void prePersist() {
        this.createdAt = new Date();
    }

}
