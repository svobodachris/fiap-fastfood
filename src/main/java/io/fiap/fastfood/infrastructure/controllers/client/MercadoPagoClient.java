package io.fiap.fastfood.infrastructure.controllers.client;

import io.fiap.fastfood.infrastructure.controllers.client.dto.MercadoPagoResponse;
import io.fiap.fastfood.infrastructure.persistence.CustomerEntity;
import io.fiap.fastfood.infrastructure.persistence.OrderEntity;

import java.util.UUID;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class MercadoPagoClient {

    public Mono<MercadoPagoResponse> createPayment(CustomerEntity customer, OrderEntity order) {
        return Mono.just(new MercadoPagoResponse(UUID.randomUUID().toString(), "ok"));
    }

    public Mono<MercadoPagoResponse> checkPayment(String paymentId) {
        return Mono.just(new MercadoPagoResponse(paymentId, "ok"));
    }
}
