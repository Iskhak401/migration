package io.caden.transformers.shared.repositories;

import io.caden.transformers.shared.entities.BaseBatchObject;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.data.jpa.repository.JpaRepository;

@ConditionalOnBean(BaseBatchRepository.class)
public interface BaseBatchRepository<A, T extends BaseBatchObject<A>> extends JpaRepository<T, Long> {
}
