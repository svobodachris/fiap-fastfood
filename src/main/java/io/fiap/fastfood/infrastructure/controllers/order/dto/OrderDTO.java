package io.fiap.fastfood.infrastructure.controllers.order.dto;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public record OrderDTO(@Nullable String id,
                       @NotNull String customerId,
                       @NotNull List<OrderItemDTO> items,
                       @NotNull LocalDateTime createdAt,
                       @Nullable Long number,
                       @NotNull PaymentDTO payment) {

    Optional<String> getId() {
        return Optional.ofNullable(id());
    }

    Optional<Long> getNumber() {
        return Optional.ofNullable(number());
    }


    public static final class OrderDTOBuilder {
        private String id;
        private @NotNull String customerId;
        private @NotNull List<OrderItemDTO> items;
        private @NotNull LocalDateTime createdAt;
        private Long number;
        private @NotNull PaymentDTO payment;

        private OrderDTOBuilder() {
        }

        public static OrderDTOBuilder builder() {
            return new OrderDTOBuilder();
        }

        public OrderDTOBuilder withId(String id) {
            this.id = id;
            return this;
        }

        public OrderDTOBuilder withCustomerId(String customerId) {
            this.customerId = customerId;
            return this;
        }

        public OrderDTOBuilder withItems(List<OrderItemDTO> orderItemList) {
            this.items = orderItemList;
            return this;
        }

        public OrderDTOBuilder withCreatedAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public OrderDTOBuilder withNumber(Long number) {
            this.number = number;
            return this;
        }

        public OrderDTOBuilder withPayment(PaymentDTO payment) {
            this.payment = payment;
            return this;
        }

        public OrderDTO build() {
            return new OrderDTO(id, customerId, items, createdAt, number, payment);
        }
    }
}