package io.fiap.fastfood.infrastructure.controllers.customer.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record IdentityDTO(
    @NotNull String type,
    @NotNull @Size(min = 2, max = 50) String number) {


    public static final class IdentityDTOBuilder {
        private @NotNull String type;
        private @NotNull @Size(min = 2, max = 50) String number;

        private IdentityDTOBuilder() {
        }

        public static IdentityDTOBuilder builder() {
            return new IdentityDTOBuilder();
        }

        public IdentityDTOBuilder withType(String type) {
            this.type = type;
            return this;
        }

        public IdentityDTOBuilder withNumber(String number) {
            this.number = number;
            return this;
        }

        public IdentityDTO build() {
            return new IdentityDTO(type, number);
        }
    }
}
