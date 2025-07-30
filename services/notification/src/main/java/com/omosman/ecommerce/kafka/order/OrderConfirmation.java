package com.omosman.ecommerce.kafka.order;

import java.math.BigDecimal;
import java.util.Set;

public record OrderConfirmation(
        String orderReference,
        BigDecimal totalAmount,

        PaymentMethod paymentMethod,
        Customer customer,

        Set<Product> products
) {



}

record Product(
        Integer productId,
        String name,
        String description,
        BigDecimal price,
        double quantity
) {
}
