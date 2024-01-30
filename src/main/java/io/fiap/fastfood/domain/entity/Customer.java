package io.fiap.fastfood.domain.entity;

public record Customer(
    String id,
    String name,
    Identity identity,
    String email,
    String number) {


    public static final class CustomerBuilder {
        private String id;
        private String name;
        private Identity identity;
        private String email;
        private String number;

        private CustomerBuilder() {
        }

        public static CustomerBuilder builder() {
            return new CustomerBuilder();
        }

        public CustomerBuilder withId(String id) {
            this.id = id;
            return this;
        }

        public CustomerBuilder withName(String name) {
            this.name = name;
            return this;
        }

        public CustomerBuilder withIdentity(Identity identity) {
            this.identity = identity;
            return this;
        }

        public CustomerBuilder withEmail(String email) {
            this.email = email;
            return this;
        }

        public CustomerBuilder withNumber(String number) {
            this.number = number;
            return this;
        }

        public Customer build() {
            return new Customer(id, name, identity, email, number);
        }
    }
}

