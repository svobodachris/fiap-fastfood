package io.fiap.fastfood.domain.application.usecases;

import org.springframework.data.domain.Pageable;

import io.fiap.fastfood.domain.entity.Order;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface OrderUseCase {
    Mono<Order> create(Order value);

    Flux<Order> list(String id, Pageable pageable);

    Mono<Void> delete(String id);

    Mono<Order> update(String id, String operations);
}
