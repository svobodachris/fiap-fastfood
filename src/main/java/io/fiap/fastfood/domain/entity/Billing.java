package io.fiap.fastfood.domain.entity;

import java.time.LocalDateTime;

public record Billing(
    String id,
    LocalDateTime openAt,
    LocalDateTime closedAt) {
}
