package io.fiap.fastfood.infrastructure.controllers.customer.dto;

import com.mongodb.lang.Nullable;
import jakarta.validation.constraints.NotNull;
import java.util.Optional;

public record CustomerDTO(
    @Nullable String id,
    @NotNull String name,
    @NotNull IdentityDTO identity,
    @NotNull String email,
    @Nullable String number) {

    Optional<String> getId() {
        return Optional.ofNullable(id());
    }

    Optional<String> getNumber() {
        return Optional.ofNullable(number());
    }


    public static final class CustomerDTOBuilder {
        private String id;
        private @NotNull String name;
        private @NotNull IdentityDTO identity;
        private @NotNull String email;
        private String number;

        private CustomerDTOBuilder() {
        }

        public static CustomerDTOBuilder builder() {
            return new CustomerDTOBuilder();
        }

        public CustomerDTOBuilder withId(String id) {
            this.id = id;
            return this;
        }

        public CustomerDTOBuilder withName(String name) {
            this.name = name;
            return this;
        }

        public CustomerDTOBuilder withIdentity(IdentityDTO identity) {
            this.identity = identity;
            return this;
        }

        public CustomerDTOBuilder withEmail(String email) {
            this.email = email;
            return this;
        }

        public CustomerDTOBuilder withNumber(String number) {
            this.number = number;
            return this;
        }

        public CustomerDTO build() {
            return new CustomerDTO(id, name, identity, email, number);
        }
    }
}
