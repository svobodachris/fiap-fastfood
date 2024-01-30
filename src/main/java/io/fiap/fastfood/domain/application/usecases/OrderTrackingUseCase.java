package io.fiap.fastfood.domain.application.usecases;

import org.springframework.data.domain.Pageable;

import io.fiap.fastfood.domain.entity.OrderTracking;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface OrderTrackingUseCase {
    Mono<OrderTracking> create(OrderTracking orderTracking);

    Mono<OrderTracking> findByOrderId(String orderId);

    Flux<OrderTracking> find(Pageable pageable);
}
