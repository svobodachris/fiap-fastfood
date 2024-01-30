package io.fiap.fastfood.infrastructure.persistence;

import org.springframework.data.mongodb.core.mapping.Field;

public record IdentityEntity(
    @Field("tipo")
    String type,
    @Field("numero")
    String number) {


    public static final class IdentityEntityBuilder {
        private String type;
        private String number;

        private IdentityEntityBuilder() {
        }

        public static IdentityEntityBuilder builder() {
            return new IdentityEntityBuilder();
        }

        public IdentityEntityBuilder withType(String type) {
            this.type = type;
            return this;
        }

        public IdentityEntityBuilder withNumber(String number) {
            this.number = number;
            return this;
        }

        public IdentityEntity build() {
            return new IdentityEntity(type, number);
        }
    }
}

