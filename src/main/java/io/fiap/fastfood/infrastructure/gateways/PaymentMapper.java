package io.fiap.fastfood.infrastructure.gateways;

import io.fiap.fastfood.domain.entity.Payment;
import io.fiap.fastfood.infrastructure.controllers.order.dto.PaymentDTO;
import io.fiap.fastfood.infrastructure.persistence.PaymentEntity;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PaymentMapper {
    Payment domainFromDto(PaymentDTO paymentDTO);

    PaymentDTO dtoFromDomain(Payment payment);

    PaymentEntity entityFromDomain(Payment payment);

    Payment domainFromEntity(PaymentEntity paymentEntity);
}