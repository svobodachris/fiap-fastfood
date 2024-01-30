package io.fiap.fastfood.infrastructure.gateways;

import io.fiap.fastfood.domain.entity.OrderItem;
import io.fiap.fastfood.infrastructure.controllers.order.dto.OrderItemDTO;
import io.fiap.fastfood.infrastructure.persistence.OrderItemEntity;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrderItemMapper {
    OrderItem domainFromDto(OrderItemDTO orderItemDTO);

    OrderItemDTO dtoFromDomain(OrderItem orderItem);

    OrderItemEntity entityFromDomain(OrderItem orderItem);

    OrderItem domainFromEntity(OrderItemEntity orderItemEntity);
}
