package io.fiap.fastfood.domain.entity;

import java.time.LocalDateTime;

public record OrderTracking(
        String id,
        String orderId,
        String orderNumber,
        String orderStatus,
        String role,
        LocalDateTime orderDateTime,

        Long orderTimeSpent

) {

    public static final class OrderTrackingBuilder {
        private String id;
        private String orderId;
        private String orderNumber;
        private String orderStatus;
        private String role;
        private LocalDateTime orderDateTime;
        private Long orderTimeSpent;

        private OrderTrackingBuilder() {
        }

        public static OrderTrackingBuilder builder() {
            return new OrderTrackingBuilder();
        }

        public OrderTrackingBuilder withId(String id) {
            this.id = id;
            return this;
        }

        public OrderTrackingBuilder withOrderId(String orderId) {
            this.orderId = orderId;
            return this;
        }

        public OrderTrackingBuilder withOrderNumber(String orderNumber) {
            this.orderNumber = orderNumber;
            return this;
        }

        public OrderTrackingBuilder withOrderStatus(String orderStatus) {
            this.orderStatus = orderStatus;
            return this;
        }

        public OrderTrackingBuilder withRole(String role) {
            this.role = role;
            return this;
        }

        public OrderTrackingBuilder withOrderDateTime(LocalDateTime orderDateTime) {
            this.orderDateTime = orderDateTime;
            return this;
        }

        public OrderTrackingBuilder withOrderTimeSpent(Long orderTimeSpent) {
            this.orderTimeSpent = orderTimeSpent;
            return this;
        }

        public OrderTracking build() {
            return new OrderTracking(id, orderId, orderNumber, orderStatus, role, orderDateTime, orderTimeSpent);
        }
    }
}
