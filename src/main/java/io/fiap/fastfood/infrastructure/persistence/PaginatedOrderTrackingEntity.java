package io.fiap.fastfood.infrastructure.persistence;

import java.util.List;

public record PaginatedOrderTrackingEntity(
        List<OrderTrackingEntity> data
) {
}
