package io.fiap.fastfood.domain.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;


public record Payment(
    String id,
    String method,
    BigDecimal amount,
    LocalDateTime dateTime,
    String orderId) {


    public static final class PaymentBuilder {
        private String id;
        private String method;
        private BigDecimal amount;
        private LocalDateTime dateTime;
        private String orderId;

        private PaymentBuilder() {
        }

        public static PaymentBuilder builder() {
            return new PaymentBuilder();
        }

        public static PaymentBuilder from(Payment payment) {
            return PaymentBuilder.builder()
                .withId(payment.id)
                .withOrderId(payment.orderId)
                .withDateTime(payment.dateTime)
                .withMethod(payment.method)
                .withAmount(payment.amount);
        }

        public PaymentBuilder withId(String id) {
            this.id = id;
            return this;
        }

        public PaymentBuilder withMethod(String method) {
            this.method = method;
            return this;
        }

        public PaymentBuilder withAmount(BigDecimal amount) {
            this.amount = amount;
            return this;
        }

        public PaymentBuilder withDateTime(LocalDateTime date) {
            this.dateTime = date;
            return this;
        }

        public PaymentBuilder withOrderId(String orderId) {
            this.orderId = orderId;
            return this;
        }

        public Payment build() {
            return new Payment(id, method, amount, dateTime, orderId);
        }
    }
}
