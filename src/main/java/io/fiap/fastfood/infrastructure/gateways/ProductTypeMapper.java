package io.fiap.fastfood.infrastructure.gateways;

import io.fiap.fastfood.domain.entity.ProductType;
import io.fiap.fastfood.infrastructure.controllers.product.dto.ProductTypeDTO;
import io.fiap.fastfood.infrastructure.persistence.ProductTypeEntity;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductTypeMapper {
    ProductType domainFromDto(ProductTypeDTO productTypeDTO);

    ProductTypeDTO dtoFromDomain(ProductType productType);

    ProductTypeEntity entityFromDomain(ProductType productType);

    ProductType domainFromEntity(ProductTypeEntity productTypeEntity);
}
