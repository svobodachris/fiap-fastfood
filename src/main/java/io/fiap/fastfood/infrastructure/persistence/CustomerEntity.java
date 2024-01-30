package io.fiap.fastfood.infrastructure.persistence;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document("customer")
public record CustomerEntity(
    @Id
    String id,
    @Field("nome")
    String name,
    @Field("documento")
    IdentityEntity identity,
    @Field("email")
    String email,
    @Field("telefone")
    String number) {

    public static final class CustomerEntityBuilder {
        private String id;
        private String name;
        private IdentityEntity identity;
        private String email;
        private String number;

        private CustomerEntityBuilder() {
        }

        public static CustomerEntityBuilder builder() {
            return new CustomerEntityBuilder();
        }

        public CustomerEntityBuilder withId(String id) {
            this.id = id;
            return this;
        }

        public CustomerEntityBuilder withName(String name) {
            this.name = name;
            return this;
        }

        public CustomerEntityBuilder withIdentity(IdentityEntity identity) {
            this.identity = identity;
            return this;
        }

        public CustomerEntityBuilder withEmail(String email) {
            this.email = email;
            return this;
        }

        public CustomerEntityBuilder withNumber(String number) {
            this.number = number;
            return this;
        }

        public CustomerEntity build() {
            return new CustomerEntity(id, name, identity, email, number);
        }
    }
}

