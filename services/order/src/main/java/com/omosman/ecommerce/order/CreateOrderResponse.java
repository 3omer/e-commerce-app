package com.omosman.ecommerce.order;

import java.math.BigDecimal;

public record CreateOrderResponse(
        Integer id,
        String reference,
        BigDecimal amount,
        PaymentMethod paymentMethod,
        String customerId
) {
}
