package io.fiap.fastfood.infrastructure.gateways;

import io.fiap.fastfood.domain.entity.Customer;
import io.fiap.fastfood.infrastructure.controllers.customer.dto.CustomerDTO;
import io.fiap.fastfood.infrastructure.persistence.CustomerEntity;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {IdentityMapper.class})
public interface CustomerMapper {
    Customer domainFromDto(CustomerDTO customerDTO);

    CustomerDTO dtoFromDomain(Customer customer);

    CustomerEntity entityFromDomain(Customer customer);

    Customer domainFromEntity(CustomerEntity customerEntity);
}
