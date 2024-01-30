package io.fiap.fastfood.domain.application.usecases;

import io.fiap.fastfood.domain.application.exception.BadRequestException;
import io.fiap.fastfood.domain.application.gateways.ProductGateway;
import io.fiap.fastfood.domain.entity.Product;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ProductInteractor implements ProductUseCase {

    private final ProductGateway productPort;


    public ProductInteractor(ProductGateway productPort) {
        this.productPort = productPort;
    }

    @Override
    public Mono<Product> create(Product product) {
        return Mono.just(product)
                .switchIfEmpty(Mono.defer(() -> Mono.error(new BadRequestException())))
                .flatMap(productPort::createProduct);
    }

    @Override
    public Flux<Product> list(String typeId, Pageable pageable) {
        return productPort.listProduct(typeId, pageable);
    }

    @Override
    public Mono<Void> delete(String id) {
        return Mono.just(id)
                .switchIfEmpty(Mono.defer(() -> Mono.error(new BadRequestException())))
                .flatMap(productPort::deleteProduct);
    }

    @Override
    public Mono<Product> update(String id, String operations) {
        return productPort.updateProduct(id, operations);
    }

}
