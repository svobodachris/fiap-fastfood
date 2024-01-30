package io.fiap.fastfood.infrastructure.persistence;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface BillingRepository extends ReactiveCrudRepository<BillingEntity, String> {
    Flux<BillingEntity> findByClosedAtNull();

    Flux<BillingEntity> findByIdNotNull(Pageable pageable);
}
