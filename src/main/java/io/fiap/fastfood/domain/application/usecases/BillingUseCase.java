package io.fiap.fastfood.domain.application.usecases;

import io.fiap.fastfood.domain.entity.Billing;
import reactor.core.publisher.Mono;

public interface BillingUseCase {
    Mono<Billing> open();
    Mono<Billing> close();
}
