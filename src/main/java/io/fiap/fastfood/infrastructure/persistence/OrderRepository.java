package io.fiap.fastfood.infrastructure.persistence;

import java.util.Date;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface OrderRepository extends ReactiveCrudRepository<OrderEntity, String> {

    Flux<OrderEntity> findById(String id, Pageable pageable);

    Flux<OrderEntity> findByIdNotNull(Pageable pageable);

}
