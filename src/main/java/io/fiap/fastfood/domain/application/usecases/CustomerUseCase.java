package io.fiap.fastfood.domain.application.usecases;

import org.springframework.data.domain.Pageable;

import io.fiap.fastfood.domain.entity.Customer;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CustomerUseCase {
    Mono<Customer> create(Customer value);
    Mono<Void> delete(String id);
    Mono<Customer> update(String id, String operations);
    Flux<Customer> list(Pageable pageable);
}
