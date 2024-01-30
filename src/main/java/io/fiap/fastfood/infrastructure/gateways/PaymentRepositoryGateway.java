package io.fiap.fastfood.infrastructure.gateways;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;

import io.fiap.fastfood.domain.application.exception.BadRequestException;
import io.fiap.fastfood.domain.application.exception.DuplicatedKeyException;
import io.fiap.fastfood.domain.application.gateways.OrderTrackingGateway;
import io.fiap.fastfood.domain.application.gateways.PaymentGateway;
import io.fiap.fastfood.domain.entity.Payment;
import io.fiap.fastfood.infrastructure.persistence.PaymentEntity;
import io.fiap.fastfood.infrastructure.persistence.PaymentRepository;
import io.vavr.CheckedFunction1;
import io.vavr.CheckedFunction2;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class PaymentRepositoryGateway implements PaymentGateway {
    private final PaymentRepository repository;
    private final PaymentMapper mapper;
    private final ObjectMapper objectMapper;

    public PaymentRepositoryGateway(PaymentRepository repository, PaymentMapper mapper, ObjectMapper objectMapper, OrderTrackingGateway trackingGateway) {
        this.repository = repository;
        this.mapper = mapper;
        this.objectMapper = objectMapper;
    }

    @Override
    public Mono<Payment> createPayment(Payment payment) {
        return repository.findByOrderId(payment.orderId())
            .next()
            .flatMap(c -> Mono.defer(() -> Mono.<PaymentEntity>error(DuplicatedKeyException::new)))
            .switchIfEmpty(Mono.defer(() -> repository.save(mapper.entityFromDomain(payment))))
            .map(mapper::domainFromEntity);
    }

    @Override
    public Mono<Payment> updatePayment(String id, String operations) {
        return repository.findById(id)
            .map(payment -> applyPatch().unchecked().apply(payment, operations))
            .flatMap(repository::save)
            .map(mapper::domainFromEntity)
            .onErrorMap(JsonPatchException.class::isInstance, BadRequestException::new);
    }

    private CheckedFunction2<PaymentEntity, String, PaymentEntity> applyPatch() {
        return (payment, operations) -> {
            var patch = readOperations()
                .unchecked()
                .apply(operations);

            var patched = patch.apply(objectMapper.convertValue(payment, JsonNode.class));

            return objectMapper.treeToValue(patched, PaymentEntity.class);
        };
    }

    private CheckedFunction1<String, JsonPatch> readOperations() {
        return operations -> {
            final InputStream in = new ByteArrayInputStream(operations.getBytes());
            return objectMapper.readValue(in, JsonPatch.class);
        };
    }
}
