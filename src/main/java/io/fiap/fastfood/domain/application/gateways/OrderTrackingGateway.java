package io.fiap.fastfood.domain.application.gateways;

import org.springframework.data.domain.Pageable;

import io.fiap.fastfood.domain.entity.OrderTracking;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface OrderTrackingGateway {
    Mono<OrderTracking> createOrderTracking(OrderTracking orderTracking);

    Mono<OrderTracking> findByOrderId(String orderId);

    Flux<OrderTracking> find(Pageable pageable);
}
