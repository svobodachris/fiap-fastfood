package io.fiap.fastfood.infrastructure.gateways;

import io.fiap.fastfood.domain.application.exception.NotFoundException;
import io.fiap.fastfood.domain.application.gateways.OrderTrackingGateway;
import io.fiap.fastfood.domain.entity.OrderTracking;
import io.fiap.fastfood.infrastructure.persistence.OrderTrackingRepository;
import io.fiap.fastfood.infrastructure.persistence.PaginatedOrderTrackingEntity;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class OrderTrackingRepositoryGateway implements OrderTrackingGateway {

    private final OrderTrackingRepository orderTrackingRepository;
    private final OrderTrackingMapper orderTrackingMapper;


    public OrderTrackingRepositoryGateway(OrderTrackingRepository orderTrackingRepository,
                                OrderTrackingMapper orderTrackingMapper) {
        this.orderTrackingRepository = orderTrackingRepository;
        this.orderTrackingMapper = orderTrackingMapper;
    }

    @Override
    public Mono<OrderTracking> createOrderTracking(OrderTracking orderTracking) {
        return orderTrackingRepository.save(orderTrackingMapper.entityFromDomain(orderTracking))
            .map(orderTrackingMapper::domainFromEntity);
    }

    @Override
    public Mono<OrderTracking> findByOrderId(String orderId) {
        return orderTrackingRepository.findByOrderIdOrderByOrderDateTime(orderId)
            .last()
            .map(orderTrackingMapper::domainFromEntity)
            .switchIfEmpty(Mono.defer(() -> Mono.error(NotFoundException::new)));
    }

    @Override
    public Flux<OrderTracking> find(Pageable pageable) {
        return orderTrackingRepository.findTracking(
                pageable.getPageNumber(),
                pageable.getPageNumber() * pageable.getPageSize(),
                pageable.getPageSize()
            )
            .flatMapIterable(PaginatedOrderTrackingEntity::data)
            .map(orderTrackingMapper::domainFromEntity)
            .switchIfEmpty(Mono.defer(() -> Mono.error(NotFoundException::new)));
    }
}
