package io.fiap.fastfood.infrastructure.gateways;

import io.fiap.fastfood.domain.entity.Order;
import io.fiap.fastfood.infrastructure.controllers.order.dto.OrderDTO;
import io.fiap.fastfood.infrastructure.persistence.OrderEntity;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {CustomerMapper.class, OrderItemMapper.class, PaymentMapper.class})
public interface OrderMapper {
    Order domainFromDto(OrderDTO orderDTO);

    OrderDTO dtoFromDomain(Order order);

    OrderEntity entityFromDomain(Order order);

    Order domainFromEntity(OrderEntity orderEntity);
}
