package io.caden.transformers.shared.entities;

import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.Data;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Data
@MappedSuperclass
@TypeDefs({
        @TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
})
public abstract class BaseBatchObject<T> {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long id;

    @Column(name = "cognito_id")
    private UUID cognitoId;

    @Column(name = "extraction_id")
    private UUID extractionId;

    @Column(name = "batch_object", columnDefinition = "jsonb")
    @Type(type = "jsonb")
    private T batchObject;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at", updatable = false, nullable = false)
    private Date createdAt;

    @PrePersist
    protected  void prePersist() {
        this.createdAt = new Date();
    }
}
