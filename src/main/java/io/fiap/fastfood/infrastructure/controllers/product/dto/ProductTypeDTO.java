package io.fiap.fastfood.infrastructure.controllers.product.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.Optional;

public record ProductTypeDTO(Long id,
                             @NotNull @Size(min = 2, max = 50) String description) {
    Optional<Long> getId() {
        return Optional.ofNullable(id());
    }


    public static final class ProductTypeDTOBuilder {
        private Long id;
        private @NotNull @Size(min = 2, max = 50) String description;

        private ProductTypeDTOBuilder() {
        }

        public static ProductTypeDTOBuilder builder() {
            return new ProductTypeDTOBuilder();
        }

        public ProductTypeDTOBuilder withId(Long id) {
            this.id = id;
            return this;
        }

        public ProductTypeDTOBuilder withDescription(String description) {
            this.description = description;
            return this;
        }

        public ProductTypeDTO build() {
            return new ProductTypeDTO(id, description);
        }
    }
}
