package io.fiap.fastfood.infrastructure.controllers.order.dto;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

public record PaymentDTO(Long id,
                         @NotNull String method,
                         @NotNull BigDecimal amount,
                         @NotNull LocalDateTime dateTime,
                         @Nullable Long orderId) {

    Optional<Long> getId() {
        return Optional.ofNullable(id());
    }

    Optional<Long> getOrderId() {
        return Optional.ofNullable(orderId());
    }

    public static final class PaymentDTOBuilder {
        private Long id;
        private @NotNull String method;
        private @NotNull BigDecimal amount;
        private @NotNull LocalDateTime dateTime;
        private @Nullable Long orderId;

        private PaymentDTOBuilder() {
        }

        public static PaymentDTOBuilder builder() {
            return new PaymentDTOBuilder();
        }

        public PaymentDTOBuilder withId(Long id) {
            this.id = id;
            return this;
        }

        public PaymentDTOBuilder withMethod(String method) {
            this.method = method;
            return this;
        }

        public PaymentDTOBuilder withAmount(BigDecimal amount) {
            this.amount = amount;
            return this;
        }

        public PaymentDTOBuilder withDateTime(LocalDateTime date) {
            this.dateTime = date;
            return this;
        }

        public PaymentDTOBuilder withProofId(Long orderId) {
            this.orderId = orderId;
            return this;
        }

        public PaymentDTO build() {
            return new PaymentDTO(id, method, amount, dateTime, orderId);
        }
    }
}