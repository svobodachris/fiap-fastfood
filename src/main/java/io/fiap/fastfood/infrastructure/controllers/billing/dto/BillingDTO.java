package io.fiap.fastfood.infrastructure.controllers.billing.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;

public record BillingDTO(
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    String id,
    @Schema(example = "2023-10-21T09:00:44.495Z")
    LocalDateTime openAt,
    @Schema(example = "2023-10-21T09:00:44.495Z")
    LocalDateTime closedAt
) {
}
