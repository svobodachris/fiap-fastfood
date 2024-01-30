package io.fiap.fastfood.infrastructure.persistence;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface OrderItemRepository extends ReactiveCrudRepository<OrderItemEntity, String> {
}
