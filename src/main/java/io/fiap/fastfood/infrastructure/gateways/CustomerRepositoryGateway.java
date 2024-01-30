package io.fiap.fastfood.infrastructure.gateways;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;

import io.fiap.fastfood.domain.application.exception.BadRequestException;
import io.fiap.fastfood.domain.application.exception.DuplicatedKeyException;
import io.fiap.fastfood.domain.application.gateways.CustomerGateway;
import io.fiap.fastfood.domain.entity.Customer;
import io.fiap.fastfood.infrastructure.persistence.CustomerEntity;
import io.fiap.fastfood.infrastructure.persistence.CustomerRepository;
import io.vavr.CheckedFunction1;
import io.vavr.CheckedFunction2;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class CustomerRepositoryGateway implements CustomerGateway {
    private final CustomerRepository customerRepository;
    private final CustomerMapper mapper;
    private final ObjectMapper objectMapper;

    public CustomerRepositoryGateway(CustomerRepository customerRepository,
                           CustomerMapper mapper,
                           ObjectMapper objectMapper) {
        this.customerRepository = customerRepository;
        this.mapper = mapper;
        this.objectMapper = objectMapper;
    }

    @Override
    public Mono<Customer> createCustomer(Customer customer) {
        return customerRepository.findByIdentityNumberAndIdentityType(
                customer.identity().number(),
                customer.identity().type())
            .flatMap(c -> Mono.defer(() -> Mono.<CustomerEntity>error(DuplicatedKeyException::new)))
            .switchIfEmpty(Mono.defer(() -> customerRepository.save(mapper.entityFromDomain(customer))))
            .map(mapper::domainFromEntity);
    }

    @Override
    public Mono<Void> deleteCustomer(String id) {
        return customerRepository.deleteById(id);
    }

    @Override
    public Mono<Customer> updateCustomer(String id, String operations) {
        return customerRepository.findById(id)
            .map(customer -> applyPatch().unchecked().apply(customer, operations))
            .flatMap(customerRepository::save)
            .map(mapper::domainFromEntity)
            .onErrorMap(JsonPatchException.class::isInstance, BadRequestException::new);
    }

    private CheckedFunction2<CustomerEntity, String, CustomerEntity> applyPatch() {
        return (customer, operations) -> {
            var patch = readOperations()
                .unchecked()
                .apply(operations);

            var patched = patch.apply(objectMapper.convertValue(customer, JsonNode.class));

            return objectMapper.treeToValue(patched, CustomerEntity.class);
        };
    }

    private CheckedFunction1<String, JsonPatch> readOperations() {
        return operations -> {
            final InputStream in = new ByteArrayInputStream(operations.getBytes());
            return objectMapper.readValue(in, JsonPatch.class);
        };
    }

    @Override
    public Flux<Customer> listCustomer(Pageable pageable) {
        return customerRepository.findByIdNotNull(pageable)
            .map(mapper::domainFromEntity);
    }
}
