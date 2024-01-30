package io.fiap.fastfood.domain.application.usecases;

import org.springframework.data.domain.Pageable;

import io.fiap.fastfood.domain.entity.Product;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ProductUseCase {
    Mono<Product> create(Product value);

    Flux<Product> list(String typeId, Pageable pageable);

    Mono<Void> delete(String id);

    Mono<Product> update(String id, String operations);
}
