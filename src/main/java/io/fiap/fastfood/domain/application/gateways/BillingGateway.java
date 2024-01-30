package io.fiap.fastfood.domain.application.gateways;

import io.fiap.fastfood.domain.entity.Billing;
import reactor.core.publisher.Mono;

public interface BillingGateway {
    Mono<Billing> openBillingDay();

    Mono<Billing> closeBillingDay();

}
