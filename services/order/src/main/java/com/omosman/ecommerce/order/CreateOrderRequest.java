package com.omosman.ecommerce.order;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.util.Set;

public record CreateOrderRequest(
        @NotBlank(message = "Reference is required")
        String reference,
        @NotBlank(message = "Customer is required")
        String customerId,
        @NotNull(message = "")
        PaymentMethod paymentMethod,

        @Positive(message = "Order amount should be positive")
        BigDecimal amount,

        @NotNull(message = "At least one product must be selected")
        @NotEmpty(message = "At least one product must be selected")
        Set<PurchaseRequest> products

) {
}
