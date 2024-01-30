package io.fiap.fastfood.infrastructure.gateways;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;

import io.fiap.fastfood.domain.application.exception.BadRequestException;
import io.fiap.fastfood.domain.application.gateways.ProductGateway;
import io.fiap.fastfood.domain.entity.Product;
import io.fiap.fastfood.infrastructure.persistence.ProductEntity;
import io.fiap.fastfood.infrastructure.persistence.ProductRepository;
import io.vavr.CheckedFunction1;
import io.vavr.CheckedFunction2;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Optional;

@Component
public class ProductRepositoryGateway implements ProductGateway {
    private final ProductRepository productRepository;
    private final ProductMapper mapper;
    private final ObjectMapper objectMapper;

    public ProductRepositoryGateway(ProductRepository productRepository,
                          ProductMapper mapper,
                          ObjectMapper objectMapper) {
        this.productRepository = productRepository;
        this.mapper = mapper;
        this.objectMapper = objectMapper;
    }

    @Override
    public Mono<Product> createProduct(Product product) {
        return productRepository.save(mapper.entityFromDomain(product))
                .map(mapper::domainFromEntity);
    }

    @Override
    public Mono<Void> deleteProduct(String id) {
        return productRepository.deleteById(id);
    }

    @Override
    public Mono<Product> updateProduct(String id, String operations) {
        return productRepository.findById(id)
                .map(product -> applyPatch().unchecked().apply(product, operations))
                .flatMap(productRepository::save)
                .map(mapper::domainFromEntity)
                .onErrorMap(JsonPatchException.class::isInstance, BadRequestException::new);
    }

    private CheckedFunction2<ProductEntity, String, ProductEntity> applyPatch() {
        return (product, operations) -> {
            var patch = readOperations()
                    .unchecked()
                    .apply(operations);

            var patched = patch.apply(objectMapper.convertValue(product, JsonNode.class));

            return objectMapper.treeToValue(patched, ProductEntity.class);
        };
    }

    private CheckedFunction1<String, JsonPatch> readOperations() {
        return operations -> {
            final InputStream in = new ByteArrayInputStream(operations.getBytes());
            return objectMapper.readValue(in, JsonPatch.class);
        };
    }

    @Override
    public Flux<Product> listProduct(String typeId, Pageable pageable) {
        return Flux.just(Optional.ofNullable(typeId))
                .filter(Optional::isEmpty)
                .flatMap(__ -> productRepository.findByIdNotNull(pageable))
                .switchIfEmpty(Flux.defer(() -> productRepository.findByTypeId(typeId, pageable)))
                .map(mapper::domainFromEntity);
    }
}
