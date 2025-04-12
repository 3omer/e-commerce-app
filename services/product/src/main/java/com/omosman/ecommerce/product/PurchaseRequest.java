package com.omosman.ecommerce.product;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record PurchaseRequest(
        @NotNull(message = "Product id is required")
        Integer id,

        @NotNull(message = "Quantity is required")
        @Positive(message = "Quantity should be greater than 1")
        Integer quantity
) {
}
