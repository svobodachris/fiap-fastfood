package io.fiap.fastfood.domain.application.gateways;

import org.springframework.data.domain.Pageable;

import io.fiap.fastfood.domain.entity.Product;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ProductGateway {

    Mono<Product> createProduct(Product product);

    Mono<Product> updateProduct(String id, String operations);

    Mono<Void> deleteProduct(String id);

    Flux<Product> listProduct(String typeId, Pageable pageable);
}
