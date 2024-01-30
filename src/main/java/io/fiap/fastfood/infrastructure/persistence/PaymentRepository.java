package io.fiap.fastfood.infrastructure.persistence;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface PaymentRepository extends ReactiveCrudRepository<PaymentEntity, String> {

    Flux<PaymentEntity> findByOrderId(String orderId);
}
