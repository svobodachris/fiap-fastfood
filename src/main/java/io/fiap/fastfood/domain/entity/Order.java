package io.fiap.fastfood.domain.entity;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public record Order(
    String id,
    String customerId,
    List<OrderItem> items,
    Date createdAt,
    Long number,
    Payment payment) {

    Optional<String> getId() {
        return Optional.ofNullable(id());
    }

    Optional<Long> getNumber() {
        return Optional.ofNullable(number());
    }

    Optional<Payment> getPayment() {
        return Optional.ofNullable(payment());
    }

    public static final class OrderBuilder {
        private String id;
        private String customerId;
        private List<OrderItem> items;
        private Date createdAt;
        private Long number;
        private Payment payment;

        private OrderBuilder() {
        }

        public static OrderBuilder builder() {
            return new OrderBuilder();
        }

        public static OrderBuilder from(Order order) {
            return OrderBuilder.builder()
                .withId(order.id)
                .withNumber(order.number)
                .withCreatedAt(order.createdAt)
                .withItems(order.items)
                .withCustomerId(order.customerId);
        }

        public OrderBuilder withId(String id) {
            this.id = id;
            return this;
        }

        public OrderBuilder withCustomerId(String customerId) {
            this.customerId = customerId;
            return this;
        }

        public OrderBuilder withItems(List<OrderItem> items) {
            this.items = items;
            return this;
        }

        public OrderBuilder withCreatedAt(Date createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public OrderBuilder withNumber(Long number) {
            this.number = number;
            return this;
        }

        public OrderBuilder withPayment(Payment payment) {
            this.payment = payment;
            return this;
        }

        public Order build() {
            return new Order(id, customerId, items, createdAt, number, payment);
        }
    }
}
