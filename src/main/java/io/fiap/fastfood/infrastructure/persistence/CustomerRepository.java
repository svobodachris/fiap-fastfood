package io.fiap.fastfood.infrastructure.persistence;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CustomerRepository extends ReactiveCrudRepository<CustomerEntity, String> {

    Flux<CustomerEntity> findByIdNotNull(Pageable pageable);

    Mono<CustomerEntity> findByIdentityNumberAndIdentityType(String number, String type);
}
