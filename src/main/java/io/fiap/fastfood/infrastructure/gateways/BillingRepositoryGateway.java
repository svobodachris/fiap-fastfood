package io.fiap.fastfood.infrastructure.gateways;

import io.fiap.fastfood.domain.application.gateways.BillingGateway;
import io.fiap.fastfood.domain.application.gateways.CounterGateway;
import io.fiap.fastfood.domain.entity.Billing;
import io.fiap.fastfood.infrastructure.persistence.BillingEntity;
import io.fiap.fastfood.infrastructure.persistence.BillingRepository;

import java.time.LocalDateTime;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

@Component
public class BillingRepositoryGateway implements BillingGateway {

    private final BillingRepository billingRepository;
    private final CounterGateway counterGateway;
    private final BillingMapper mapper;

    public BillingRepositoryGateway(BillingRepository billingRepository,
                          CounterGateway counterGateway,
                          BillingMapper mapper) {
        this.billingRepository = billingRepository;
        this.counterGateway = counterGateway;
        this.mapper = mapper;
    }

    @Transactional
    @Override
    public Mono<Billing> openBillingDay() {
        return billingRepository.findByClosedAtNull()
            .next()
            .switchIfEmpty(Mono.defer(() ->
                Mono.just(BillingEntity.BillingEntityBuilder.builder().withOpenAt(LocalDateTime.now()).build())
                    .flatMap(billingRepository::save)))
            .flatMap(billing -> counterGateway.resetSequence("order_number")
                .map(__ -> billing))
            .map(mapper::domainFromEntity);
    }

    @Override
    public Mono<Billing> closeBillingDay() {
        return billingRepository.findByClosedAtNull()
            .next()
            .map(billing -> BillingEntity.BillingEntityBuilder.from(billing)
                .withClosedAt(LocalDateTime.now())
                .build())
            .flatMap(billingRepository::save)
            .map(mapper::domainFromEntity);
    }
}
