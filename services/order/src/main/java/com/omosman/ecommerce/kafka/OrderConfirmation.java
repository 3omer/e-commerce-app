package com.omosman.ecommerce.kafka;

import com.omosman.ecommerce.customer.CustomerResponse;
import com.omosman.ecommerce.order.PaymentMethod;
import com.omosman.ecommerce.product.PurchaseResponse;

import java.math.BigDecimal;
import java.util.Set;

public record OrderConfirmation(
        String orderReference,
        BigDecimal totalAmount,

        PaymentMethod paymentMethod,
        CustomerResponse customer,
        Set<PurchaseResponse> products
) {
}
