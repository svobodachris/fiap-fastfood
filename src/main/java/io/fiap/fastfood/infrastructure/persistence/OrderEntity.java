package io.fiap.fastfood.infrastructure.persistence;

import java.util.Date;
import java.util.List;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document("pedido")
public record OrderEntity(
    @Id
    String id,
    @Field("id_cliente")
    String customerId,
    @Field("items")
    List<OrderItemEntity> items,
    @Field("data_hora")
    Date createdAt,
    @Field("numero_pedido")
    Long number) {

        public static final class OrderEntityBuilder {
            private String id;
            private String customerId;
            private List<OrderItemEntity> items;
            private Date createdAt;
            private Long number;

            private OrderEntityBuilder() {
            }
    
            public static OrderEntityBuilder builder() {
                return new OrderEntityBuilder();
            }
    
            public OrderEntityBuilder withId(String id) {
                this.id = id;
                return this;
            }
    
            public OrderEntityBuilder withCustomerId(String customerId) {
                this.customerId = customerId;
                return this;
            }

            public OrderEntityBuilder withItems(List<OrderItemEntity> items) {
                this.items = items;
                return this;
            }

            public OrderEntityBuilder withCreatedAt(Date createdAt) {
                this.createdAt = createdAt;
                return this;
            }

            public OrderEntityBuilder withNumber(Long number) {
                this.number = number;
                return this;
            }
       
            public OrderEntity build() {
                return new OrderEntity(id, customerId, items, createdAt, number);
            }
        }

}
