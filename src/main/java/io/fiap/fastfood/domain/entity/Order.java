package io.fiap.fastfood.domain.entity;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.function.BooleanSupplier;

import org.mockito.ArgumentMatchers;

import io.fiap.fastfood.Item;

public record Order(
    String id,
    String customerId,
    List<OrderItem> items,
    Date createdAt,
    Long number,
    Payment payment) {

    public Optional<String> getId() {
        return Optional.ofNullable(id());
    }

    Optional<Long> getNumber() {
        return Optional.ofNullable(number());
    }

    Optional<Payment> getPayment() {
        return Optional.ofNullable(payment());
    }

    public void setId(int i) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setId'");
    }

    public void setCustomerName(String string) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setCustomerName'");
    }

    public void setTotalAmount(double d) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setTotalAmount'");
    }

    public Object getCustomerName() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getCustomerName'");
    }

    public Double getTotalAmount() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getTotalAmount'");
    }

    public void addItem(Item item) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'addItem'");
    }

    public ArgumentMatchers getItems() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getItems'");
    }

    public double calculateTotalPrice() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'calculateTotalPrice'");
    }

    public void removeItem(Item item1) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'removeItem'");
    }

    public void clear() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'clear'");
    }

    public void placeOrder() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'placeOrder'");
    }

    public BooleanSupplier isPlaced() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'isPlaced'");
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
