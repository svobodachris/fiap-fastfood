package io.fiap.fastfood.domain.application.usecases;

import io.fiap.fastfood.domain.application.gateways.BillingGateway;
import io.fiap.fastfood.domain.entity.Billing;
import io.fiap.fastfood.infrastructure.controllers.billing.dto.BillingDTO;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class BillingInteractor implements BillingUseCase {

    private final BillingGateway billingGateway;

    public BillingInteractor(BillingGateway billingGateway) {
        this.billingGateway = billingGateway;
    }

    @Override
    public Mono<Billing> open() {
        return billingGateway.openBillingDay();
    }

    @Override
    public Mono<Billing> close() {
        return billingGateway.closeBillingDay();
    }
}
