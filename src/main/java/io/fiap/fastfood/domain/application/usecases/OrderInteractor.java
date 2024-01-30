package io.fiap.fastfood.domain.application.usecases;

import io.fiap.fastfood.domain.application.exception.BadRequestException;
import io.fiap.fastfood.domain.application.gateways.CounterGateway;
import io.fiap.fastfood.domain.application.gateways.OrderGateway;
import io.fiap.fastfood.domain.application.gateways.OrderTrackingGateway;
import io.fiap.fastfood.domain.application.gateways.PaymentGateway;
import io.fiap.fastfood.domain.entity.Order;
import io.fiap.fastfood.domain.entity.OrderTracking;
import io.fiap.fastfood.domain.entity.Payment;

import java.time.LocalDateTime;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class OrderInteractor implements OrderUseCase {

    private final OrderGateway orderPort;
    private final PaymentGateway paymentPort;
    private final CounterGateway counterPort;
    private final OrderTrackingGateway trackingPort;

    public OrderInteractor(OrderGateway orderPort, PaymentGateway paymentPort, CounterGateway counterPort, OrderTrackingGateway trackingPort) {
        this.orderPort = orderPort;
        this.paymentPort = paymentPort;
        this.counterPort = counterPort;
        this.trackingPort = trackingPort;
    }

    @Override
    public Mono<Order> create(Order order) {
        return Mono.just(order)
            .switchIfEmpty(Mono.defer(() -> Mono.error(new BadRequestException())))
            .zipWith(counterPort.nextSequence("order_number_seq"))
            .flatMap(t ->
                orderPort.createOrder(
                    Order.OrderBuilder
                        .from(order)
                        .withNumber(t.getT2())
                        .build())
            )
            .flatMap(o ->
                paymentPort.createPayment(Payment.PaymentBuilder.from(order.payment())
                        .withOrderId(o.id())
                        .withDateTime(LocalDateTime.now())
                        .build())
                    .flatMap(p -> this.createWaitingPaymentStatus(p, o.number()))
                    .map(payment -> o)
            );
    }

    private Mono<OrderTracking> createWaitingPaymentStatus(Payment payment, Long orderNumber) {
        return trackingPort.createOrderTracking(OrderTracking.OrderTrackingBuilder.builder()
            .withRole("EMPLOYEE")
            .withOrderId(payment.orderId())
            .withOrderNumber(String.valueOf(orderNumber))
            .withOrderDateTime(LocalDateTime.now())
            .withOrderStatus("WAITING_PAYMENT")
            .build());
    }


    @Override
    public Flux<Order> list(String id, Pageable pageable) {
        return orderPort.listOrder(id, pageable);
    }

    @Override
    public Mono<Void> delete(String id) {
        return Mono.just(id)
            .switchIfEmpty(Mono.defer(() -> Mono.error(new BadRequestException())))
            .flatMap(orderPort::deleteOrder);
    }


    @Override
    public Mono<Order> update(String id, String operations) {
        return orderPort.updateOrder(id, operations);
    }

}
