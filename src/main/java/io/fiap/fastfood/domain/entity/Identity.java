package io.fiap.fastfood.domain.entity;

public record Identity(
    String type,
    String number) {


    public static final class IdentityBuilder {
        private String type;
        private String number;

        private IdentityBuilder() {
        }

        public static IdentityBuilder builder() {
            return new IdentityBuilder();
        }

        public IdentityBuilder withType(String type) {
            this.type = type;
            return this;
        }

        public IdentityBuilder withNumber(String number) {
            this.number = number;
            return this;
        }

        public Identity build() {
            return new Identity(type, number);
        }
    }
}

