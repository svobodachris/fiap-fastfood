package io.fiap.fastfood.infrastructure.gateways;

import io.fiap.fastfood.domain.entity.Billing;
import io.fiap.fastfood.infrastructure.controllers.billing.dto.BillingDTO;
import io.fiap.fastfood.infrastructure.persistence.BillingEntity;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BillingMapper {
    Billing domainFromDto(BillingDTO billingDTO);

    BillingDTO dtoFromDomain(Billing billing);

    BillingEntity entityFromDomain(Billing billing);

    Billing domainFromEntity(BillingEntity billingEntity);
}
