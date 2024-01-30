package io.fiap.fastfood.domain.application.gateways;

import org.springframework.data.domain.Pageable;

import io.fiap.fastfood.domain.entity.Customer;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CustomerGateway {
    Mono<Customer> createCustomer(Customer product);
    Mono<Customer> updateCustomer(String id, String operations);
    Mono<Void> deleteCustomer(String id);
    Flux<Customer> listCustomer(Pageable pageable);
}
