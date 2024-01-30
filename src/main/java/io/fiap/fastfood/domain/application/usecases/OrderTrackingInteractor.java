package io.fiap.fastfood.domain.application.usecases;

import io.fiap.fastfood.domain.application.gateways.OrderTrackingGateway;
import io.fiap.fastfood.domain.entity.OrderTracking;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class OrderTrackingInteractor implements OrderTrackingUseCase {

    private final OrderTrackingGateway orderTrackingPort;

    public OrderTrackingInteractor(OrderTrackingGateway orderTrackingPort) {
        this.orderTrackingPort = orderTrackingPort;
    }

    @Override
    public Mono<OrderTracking> create(OrderTracking orderTracking) {
        return orderTrackingPort.createOrderTracking(orderTracking);
    }

    @Override
    public Mono<OrderTracking> findByOrderId(String orderId) {
        return orderTrackingPort.findByOrderId(orderId);
    }

    @Override
    public Flux<OrderTracking> find(Pageable pageable) {
        return orderTrackingPort.find(pageable);
    }

}
