package io.fiap.fastfood.domain.application.usecases;

import io.fiap.fastfood.domain.application.exception.BadRequestException;
import io.fiap.fastfood.domain.application.gateways.CustomerGateway;
import io.fiap.fastfood.domain.entity.Customer;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class CustomerInteractor implements CustomerUseCase {

    private final CustomerGateway productPort;


    public CustomerInteractor(CustomerGateway productPort) {
        this.productPort = productPort;
    }

    @Override
    public Mono<Customer> create(Customer product) {
        return Mono.just(product)
            .switchIfEmpty(Mono.defer(() -> Mono.error(new BadRequestException())))
            .flatMap(productPort::createCustomer);
    }

    @Override
    public Flux<Customer> list(Pageable pageable) {
        return productPort.listCustomer(pageable);
    }

    @Override
    public Mono<Void> delete(String id) {
        return Mono.just(id)
            .switchIfEmpty(Mono.defer(() -> Mono.error(new BadRequestException())))
            .flatMap(productPort::deleteCustomer);
    }


    @Override
    public Mono<Customer> update(String id, String operations) {
        return productPort.updateCustomer(id, operations);
    }

}
