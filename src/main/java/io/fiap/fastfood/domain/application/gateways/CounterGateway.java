package io.fiap.fastfood.domain.application.gateways;

import reactor.core.publisher.Mono;

public interface CounterGateway {
    Mono<Long> nextSequence(String name);

    Mono<Long> resetSequence(String name);

}
