package io.fiap.fastfood.domain.application.gateways;

import org.springframework.data.domain.Pageable;

import io.fiap.fastfood.domain.entity.Order;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface OrderGateway {

    Mono<Order> createOrder(Order order);

    Mono<Order> updateOrder(String id, String operations);

    Mono<Void> deleteOrder(String id);

    Flux<Order> listOrder(String id, Pageable pageable);
}
