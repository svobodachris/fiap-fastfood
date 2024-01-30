package io.fiap.fastfood.domain.entity;

public record PaymentStatus(
    Long id,
    Long paymentId,
    String description) {
}
