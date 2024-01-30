package io.fiap.fastfood.infrastructure.gateways;

import io.fiap.fastfood.domain.entity.Identity;
import io.fiap.fastfood.infrastructure.controllers.customer.dto.IdentityDTO;
import io.fiap.fastfood.infrastructure.persistence.IdentityEntity;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface IdentityMapper {
    Identity domainFromDto(IdentityDTO productTypeDTO);

    IdentityDTO dtoFromDomain(Identity productType);

    IdentityEntity entityFromDomain(Identity productType);

    Identity domainFromEntity(IdentityEntity productTypeEntity);
}
