package io.fiap.fastfood.domain.application.gateways;

import io.fiap.fastfood.domain.entity.Payment;
import reactor.core.publisher.Mono;

public interface PaymentGateway {

    Mono<Payment> createPayment(Payment order);

    Mono<Payment> updatePayment(String id, String operations);

}
