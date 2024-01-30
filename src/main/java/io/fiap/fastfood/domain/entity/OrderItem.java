package io.fiap.fastfood.domain.entity;

import java.util.Optional;

public record OrderItem(
    String productId,
    Long amount,
    String quote) {

    Optional<String> getQuote() {
        return Optional.ofNullable(quote());
    }
}
