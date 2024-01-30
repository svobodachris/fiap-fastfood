package io.fiap.fastfood.infrastructure.gateways;

import io.fiap.fastfood.domain.entity.Product;
import io.fiap.fastfood.infrastructure.controllers.product.dto.ProductDTO;
import io.fiap.fastfood.infrastructure.persistence.ProductEntity;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {ProductTypeMapper.class})
public interface ProductMapper {
    Product domainFromDto(ProductDTO productDTO);

    ProductDTO dtoFromDomain(Product product);

    ProductEntity entityFromDomain(Product product);

    Product domainFromEntity(ProductEntity productEntity);
}
